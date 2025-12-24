package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.entity.UserStatus;

import java.util.UUID;

public interface UserStatusService {

    //로그인, 활동 시 호출
    void touch(UUID userId);

    //현재 온라인 여부 확인
    boolean isOnline(UUID userId);

    //상태 조회
    UserStatus find(UUID userId);
}
