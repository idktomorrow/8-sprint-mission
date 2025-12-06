package com.sprint.mission.discodeit.entity;

import java.io.Serializable;
import java.util.UUID;

public class Channel implements Serializable {
    private static final long serialVersionUID = 1L;

    /*
        필수 요소
        1. id
        2. 생성 시간
        3. 업데이트 시간
        4. 채널이름
    */

    //필드 선언
    private UUID id;
    private long createdAt;
    private long updatedAt;
    private String channelName;

    //생성자
    public Channel(String channelName) {
        this.id =  UUID.randomUUID();
        this.createdAt = System.currentTimeMillis();
        this.updatedAt = System.currentTimeMillis();

        this.channelName = channelName;
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
    public String getChannelName() {
        return channelName;
    }

    //update
    public void update(String newChannelName) {
        this.channelName = newChannelName;
        this.updatedAt = System.currentTimeMillis();
    }
}
