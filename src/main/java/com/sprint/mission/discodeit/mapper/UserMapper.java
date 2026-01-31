package com.sprint.mission.discodeit.mapper;

import com.sprint.mission.discodeit.dto.data.UserDto;
import com.sprint.mission.discodeit.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {

  private final BinaryContentMapper binaryContentMapper;

  public UserDto toDto(User user) {
    return new UserDto(
        user.getId(),
        user.getUsername(),
        user.getEmail(),
        // [수정] 프로필 엔티티를 BinaryContentDto로 변환해서 넣음
        user.getProfile() != null ? binaryContentMapper.toDto(user.getProfile()) : null,
        // [수정] 유저 상태에서 온라인 여부를 가져옴
        user.getStatus() != null && user.getStatus().isOnline()
    );
  }
}