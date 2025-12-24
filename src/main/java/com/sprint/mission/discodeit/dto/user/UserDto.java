package com.sprint.mission.discodeit.dto.user;

import java.util.UUID;

public record UserDto(
        UUID id,
        String username,
        String email,
        UUID profileImageId,
        boolean isOnline
) {
}
