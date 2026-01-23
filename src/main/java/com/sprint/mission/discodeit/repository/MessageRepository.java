package com.sprint.mission.discodeit.repository;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface MessageRepository extends JpaRepository<Message, UUID> {

  // 특정 채널의 메시지를 생성일자 내림차순(최신순)으로 가져오기
  Slice<Message> findAllByChannelId(UUID channelId, Pageable pageable);

  // 나중에 특정 채널의 메시지 목록을 가져오는 메서드 등 추가
  List<Message> findAllByChannel(Channel channel);
}