package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.entity.ReadStatus;
import com.sprint.mission.discodeit.repository.ReadStatusRepository;
import com.sprint.mission.discodeit.service.ReadStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.NoSuchElementException;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class BasicReadStatusService implements ReadStatusService {

    private final ReadStatusRepository readStatusRepository;


    //채널에 사용자가 처음 참여할 때 호출
    @Override
    public ReadStatus create(UUID  userId, UUID channelId) {
        ReadStatus readStatus = new ReadStatus(userId, channelId);
        return readStatusRepository.save(readStatus);
    }


    //사용자가 메세지를 읽었을 때 호출
    @Override
    public void updateLastRead(UUID userId, UUID channelId) {
        ReadStatus readStatus = readStatusRepository
                .findByUserIdAndChannelId(userId, channelId)
                .orElseGet(() -> {
                    ReadStatus newStatus = new ReadStatus(userId, channelId);
                    return readStatusRepository.save(newStatus);
                });

        readStatus.updateLastReadAt(Instant.now());
        readStatusRepository.save(readStatus);
    }

    //특정 사용자 + 특정 채널의 ReadStstus 조회
    @Override
    public ReadStatus find(UUID userId, UUID channelId) {
        return readStatusRepository
                .findByUserIdAndChannelId(userId, channelId)
                .orElseThrow(() ->
                        new NoSuchElementException("ReadStatus not found")
                );
    }
}
