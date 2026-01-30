package com.sprint.mission.discodeit.dto.request;

import java.io.InputStream;

public record BinaryContentCreateRequest(
    String fileName,
    String contentType,
    InputStream inputStream,
    long size
) {

}
