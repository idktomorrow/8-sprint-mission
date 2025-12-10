package com.sprint.mission.discodeit.entity;

import java.io.Serializable;
import java.util.UUID;

public class Message  implements Serializable {
    private static final long serialVersionUID = 1L;
     /*
        필수 요소
        1. id
        2. 생성 시간
        3. 업데이트 시간
        4. 메세지
        5. 보낸사람
        6. 보낸채널
    */

    //필드 선언
    private UUID id;
    private long createdAt;
    private long updatedAt;
    private String message;
    private User sender;
    private Channel channel;

    //생성자
    public Message(Channel channel, User sender, String message) {
        this.id = UUID.randomUUID();
        this.createdAt = System.currentTimeMillis();
        this.updatedAt = System.currentTimeMillis();
        this.channel = channel;
        this.sender = sender;
        this.message = message;
    }

    //getter
    public UUID getId() {
        return id;
    }
    public long getCreatedAt() {
        return createdAt;
    }
    public long getUpdatedAt() {
        return updatedAt;
    }
    public Channel getChannel() {
        return channel;
    }
    public User getSender() {
        return sender;
    }
    public String getMessage() {
        return message;
    }

    //update
    public void updateMessage(String newMessage) {
        this.message = newMessage;
        this.updatedAt = System.currentTimeMillis();
    }
}
