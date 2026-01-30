--초기화
DROP TABLE IF EXISTS "message_attachments" CASCADE;
DROP TABLE IF EXISTS "read_statuses" CASCADE;
DROP TABLE IF EXISTS "messages" CASCADE;
DROP TABLE IF EXISTS "user_statuses" CASCADE;
DROP TABLE IF EXISTS "channels" CASCADE;
DROP TABLE IF EXISTS "users" CASCADE;
DROP TABLE IF EXISTS "binary_contents" CASCADE;

--테이블 생성
CREATE TABLE "binary_contents"
(
    "id"           uuid         NOT NULL,
    "created_at"   timestamptz  NOT NULL, -- NN
    "file_name"    varchar(255) NOT NULL, -- NN
    "size"         bigint       NOT NULL, -- NN
    "content_type" varchar(100) NOT NULL  -- NN
);

CREATE TABLE "users"
(
    "id"         uuid         NOT NULL,
    "created_at" timestamptz  NOT NULL, -- NN
    "updated_at" timestamptz,
    "username"   varchar(50)  NOT NULL, -- NN
    "email"      varchar(100) NOT NULL, -- NN
    "password"   varchar(60)  NOT NULL, -- NN
    "profile_id" uuid
);

CREATE TABLE "channels"
(
    "id"          uuid        NOT NULL,
    "created_at"  timestamptz NOT NULL, -- NN
    "updated_at"  timestamptz,
    "name"        varchar(100),
    "description" varchar(500),
    "type"        varchar(10) NOT NULL  -- NN (ENUM)
);

CREATE TABLE "user_statuses"
(
    "id"             uuid        NOT NULL,
    "created_at"     timestamptz NOT NULL, -- NN
    "updated_at"     timestamptz,
    "user_id"        uuid        NOT NULL, -- FK, UK, NN
    "last_active_at" timestamptz NOT NULL  -- NN
);

CREATE TABLE "messages"
(
    "id"         uuid        NOT NULL,
    "created_at" timestamptz NOT NULL, -- NN
    "updated_at" timestamptz,
    "content"    text,
    "channel_id" uuid        NOT NULL, -- FK, NN
    "author_id"  uuid
);

CREATE TABLE "read_statuses"
(
    "id"           uuid        NOT NULL,
    "created_at"   timestamptz NOT NULL, -- NN
    "updated_at"   timestamptz,
    "user_id"      uuid        NOT NULL, -- FK, NN
    "channel_id"   uuid        NOT NULL, -- FK, NN
    "last_read_at" timestamptz NOT NULL  -- NN
);

CREATE TABLE "message_attachments"
(
    "message_id"    uuid NOT NULL, -- FK, NN
    "attachment_id" uuid NOT NULL  -- FK, NN
);

--Primary Keys (PK) 생성
ALTER TABLE "binary_contents"
    ADD CONSTRAINT "PK_BINARY_CONTENTS" PRIMARY KEY ("id");
ALTER TABLE "users"
    ADD CONSTRAINT "PK_USERS" PRIMARY KEY ("id");
ALTER TABLE "channels"
    ADD CONSTRAINT "PK_CHANNELS" PRIMARY KEY ("id");
ALTER TABLE "user_statuses"
    ADD CONSTRAINT "PK_USER_STATUSES" PRIMARY KEY ("id");
ALTER TABLE "messages"
    ADD CONSTRAINT "PK_MESSAGES" PRIMARY KEY ("id");
ALTER TABLE "read_statuses"
    ADD CONSTRAINT "PK_READ_STATUSES" PRIMARY KEY ("id");
ALTER TABLE "message_attachments"
    ADD CONSTRAINT "PK_MESSAGE_ATTACHMENTS" PRIMARY KEY ("message_id", "attachment_id");


--Unique Keys (UK) 생성
ALTER TABLE "users"
    ADD CONSTRAINT "UK_USERS_USERNAME" UNIQUE ("username");
ALTER TABLE "users"
    ADD CONSTRAINT "UK_USERS_EMAIL" UNIQUE ("email");
ALTER TABLE "users"
    ADD CONSTRAINT "UK_USERS_PROFILE_ID" UNIQUE ("profile_id");
ALTER TABLE "user_statuses"
    ADD CONSTRAINT "UK_USER_STATUSES_USER_ID" UNIQUE ("user_id");
ALTER TABLE "read_statuses"
    ADD CONSTRAINT "UK_READ_STATUSES_USER_CHANNEL" UNIQUE ("user_id", "channel_id");
ALTER TABLE "message_attachments"
    ADD CONSTRAINT "UK_MESSAGE_ATTACHMENTS_ATTACHMENT_ID" UNIQUE ("attachment_id");

--Check Constraints
ALTER TABLE "channels"
    ADD CONSTRAINT "CHK_CHANNELS_TYPE" CHECK ("type" IN ('PUBLIC', 'PRIVATE'));


--Foreign Keys (FK) 생성
ALTER TABLE "users"
    ADD CONSTRAINT "FK_USERS_PROFILE"
        FOREIGN KEY ("profile_id") REFERENCES "binary_contents" ("id") ON DELETE SET NULL;

ALTER TABLE "user_statuses"
    ADD CONSTRAINT "FK_USER_STATUSES_USER"
        FOREIGN KEY ("user_id") REFERENCES "users" ("id") ON DELETE CASCADE;

ALTER TABLE "messages"
    ADD CONSTRAINT "FK_MESSAGES_CHANNEL"
        FOREIGN KEY ("channel_id") REFERENCES "channels" ("id") ON DELETE CASCADE;

ALTER TABLE "messages"
    ADD CONSTRAINT "FK_MESSAGES_AUTHOR"
        FOREIGN KEY ("author_id") REFERENCES "users" ("id") ON DELETE SET NULL;

ALTER TABLE "read_statuses"
    ADD CONSTRAINT "FK_READ_STATUSES_USER"
        FOREIGN KEY ("user_id") REFERENCES "users" ("id") ON DELETE CASCADE;

ALTER TABLE "read_statuses"
    ADD CONSTRAINT "FK_READ_STATUSES_CHANNEL"
        FOREIGN KEY ("channel_id") REFERENCES "channels" ("id") ON DELETE CASCADE;

ALTER TABLE "message_attachments"
    ADD CONSTRAINT "FK_MESSAGE_ATTACHMENTS_MESSAGE"
        FOREIGN KEY ("message_id") REFERENCES "messages" ("id") ON DELETE CASCADE;

ALTER TABLE "message_attachments"
    ADD CONSTRAINT "FK_MESSAGE_ATTACHMENTS_ATTACHMENT"
        FOREIGN KEY ("attachment_id") REFERENCES "binary_contents" ("id") ON DELETE CASCADE;

-- [Messages 테이블 인덱스]
-- 채널별 메시지 조회 및 정렬 성능 최적화
CREATE INDEX idx_messages_channel_id ON messages (channel_id);
CREATE INDEX idx_messages_author_id ON messages (author_id);
CREATE INDEX idx_messages_created_at ON messages (created_at DESC);
-- 복합 인덱스: 특정 채널 내에서 최신순 조회를 위해 필수
CREATE INDEX idx_messages_channel_created ON messages (channel_id, created_at DESC);

-- [ReadStatus 테이블 인덱스]
-- 유저/채널별 조회 및 복합 조건 검색 성능 최적화
CREATE INDEX idx_read_statuses_user_id ON read_statuses (user_id);
CREATE INDEX idx_read_statuses_channel_id ON read_statuses (channel_id);
CREATE INDEX idx_read_statuses_user_channel ON read_statuses (user_id, channel_id);

-- [MessageAttachments 테이블 인덱스]
-- 메시지와 첨부파일 간 조인 및 검색 성능 최적화
CREATE INDEX idx_message_attachments_message_id ON message_attachments (message_id);
CREATE INDEX idx_message_attachments_attachment_id ON message_attachments (attachment_id);