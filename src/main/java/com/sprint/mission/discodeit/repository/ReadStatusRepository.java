package com.sprint.mission.discodeit.repository;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.ReadStatus;
import com.sprint.mission.discodeit.entity.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface ReadStatusRepository extends JpaRepository<ReadStatus, UUID> {

  // 특정 유저가 특정 채널을 읽었는지 확인하는 쿼리 등 나중에
  List<ReadStatus> findAllByUserId(UUID userId);

  List<ReadStatus> findAllByChannel(Channel channel);

  Optional<ReadStatus> findByUserAndChannel(User user, Channel channel);
}