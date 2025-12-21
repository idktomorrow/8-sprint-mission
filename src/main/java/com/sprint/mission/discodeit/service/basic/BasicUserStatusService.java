package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.repository.UserStatusRepository;
import com.sprint.mission.discodeit.service.UserStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.UUID;


@RequiredArgsConstructor
@Service
public class BasicUserStatusService implements UserStatusService {
    private final UserStatusRepository userStatusRepository;


    //사용자가 로그인하거나 활동했을 때 호출
    //기존 UserStatus가 있으면 LastActive 갱신 없으면 새로 생성
    @Override
    public void touch(UUID userId) {
        UserStatus status = userStatusRepository
                .findByUserId(userId)
                .orElseGet(() -> new UserStatus(userId));

        status.updateLastActive();
        userStatusRepository.save(status);
    }

    //현재 온라인 여부 확인
    //UserStatus가 없으면 오프라인 있으면 isActve() 결과 반환
    @Override
    public boolean isOnline(UUID userId) {
        return userStatusRepository
                .findByUserId(userId)
                .map(UserStatus::isActive)
                .orElse(false);
    }

    //특정 사용자의 UserStatus 조회
    @Override
    public UserStatus find(UUID userId) {
        return userStatusRepository
                .findByUserId(userId)
                .orElseThrow(() ->
                        new NoSuchElementException("UserStatus not found")
                );
    }
}
