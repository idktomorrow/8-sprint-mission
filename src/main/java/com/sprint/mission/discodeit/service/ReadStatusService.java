package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.entity.ReadStatus;

import java.util.UUID;

public interface ReadStatusService {


    //채널에 사용자가 처음 참여할 때 생성
    ReadStatus create(UUID userId, UUID channelId);

    //마지막 읽은 시간 갱ㅅ긴
    void updateLastRead(UUID userId, UUID channelId);

    //특정 사용자의 특정 채널 조회
    ReadStatus find(UUID userId, UUID channelId);
}
