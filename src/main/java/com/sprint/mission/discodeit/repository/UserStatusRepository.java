package com.sprint.mission.discodeit.repository;

import com.sprint.mission.discodeit.entity.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface UserStatusRepository extends JpaRepository<UserStatus, UUID> {

  // 나중에 특정 유저의 상태를 찾는 메서드 등 추가
  java.util.Optional<UserStatus> findByUser(com.sprint.mission.discodeit.entity.User user);
}