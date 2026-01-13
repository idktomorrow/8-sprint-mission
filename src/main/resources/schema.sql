DROP TABLE IF EXISTS "binary_contents" CASCADE;

CREATE TABLE "binary_contents"
(
    "id"           uuid         NOT NULL,
    "created_at"   timestamptz  NOT NULL,
    "file_name"    varchar(255) NOT NULL,
    "size"         bigint       NOT NULL,
    "content_type" varchar(100) NOT NULL,
    "bytes"        bytea        NOT NULL
);

ALTER TABLE "binary_contents"
    ADD CONSTRAINT "PK_BINARY_CONTENTS" PRIMARY KEY ("id");