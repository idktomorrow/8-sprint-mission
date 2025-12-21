package com.sprint.mission.discodeit.dto.binary;

public record BinaryContentCreateRequest(
        String contentType,
        long size,
        String storagePath
) {}