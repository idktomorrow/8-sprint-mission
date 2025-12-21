package com.sprint.mission.discodeit.entity;


import lombok.Getter;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

@Getter
public class BinaryContent implements Serializable {
    private static final long serialVersionUID = 1L;

    private UUID id;
    private String contentType;
    private long size;
    private String storagePath;
    private Instant createdAt;

    public BinaryContent(String contentType, long size, String storagePath) {
        this.id = UUID.randomUUID();
        this.contentType = contentType;
        this.size = size;
        this.storagePath = storagePath;
        this.createdAt = Instant.now();

    }
}