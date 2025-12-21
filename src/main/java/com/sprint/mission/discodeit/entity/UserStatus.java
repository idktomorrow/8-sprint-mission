package com.sprint.mission.discodeit.entity;


import lombok.Getter;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

@Getter
public class UserStatus implements Serializable {
    private static final long serialVersionUID = 1L;

    private UUID id;
    private UUID userId;
    private Instant createdAt;
    private Instant updatedAt;

    //접속시간
    private Instant lastActive;

    public UserStatus(UUID userId) {
        this.id = UUID.randomUUID();
        this.userId = userId;
        this.createdAt = Instant.now();
        this.lastActive = this.createdAt;
    }

    public void updateLastActive() {
        this.lastActive = Instant.now();
        this.updatedAt = Instant.now();
    }

    public boolean isActive() {
        return Instant.now().minusSeconds(300).isBefore(lastActive);
    }


}
