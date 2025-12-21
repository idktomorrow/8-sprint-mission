package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.channel.ChannelDto;
import com.sprint.mission.discodeit.dto.channel.ChannelUpdateRequest;
import com.sprint.mission.discodeit.dto.channel.PrivateChannelCreateRequest;
import com.sprint.mission.discodeit.dto.channel.PublicChannelCreateRequest;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.ChannelType;
import com.sprint.mission.discodeit.entity.ReadStatus;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.ReadStatusRepository;
import com.sprint.mission.discodeit.service.ChannelService;
import com.sprint.mission.discodeit.service.ReadStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class BasicChannelService implements ChannelService {
    private final ChannelRepository channelRepository;
    private final ReadStatusService readStatusService;
    private final ReadStatusRepository readStatusRepository;


    //public 생성
    @Override
    public ChannelDto createPublic(PublicChannelCreateRequest request) {
        Channel channel = new Channel(
                ChannelType.PUBLIC,
                request.name(),
                request.description()
        );

        Channel saved = channelRepository.save(channel);
        return toDto(saved);
    }


    //private
    @Override
    public ChannelDto createPrivate(PrivateChannelCreateRequest request) {
        Channel channel = new Channel(
                ChannelType.PRIVATE,
                null,
                null
        );

        Channel saved = channelRepository.save(channel);

        for (UUID userId : request.participantUserIds()) {
            ReadStatus readStatus = new ReadStatus(userId, saved.getId());
            readStatusRepository.save(readStatus);
        }
        return toDto(saved);
    }

    //조회
    @Override
    public ChannelDto find(UUID channelId) {
        Channel channel = channelRepository.findById(channelId)
                .orElseThrow(() -> new NoSuchElementException("채널을 찾을 수 없습니다."));

        return toDto(channel);
    }

    /**
     * 특정 유저가 볼 수 있는 채널 목록 조회
     * (지금은 전체 조회, 다음 단계에서 PRIVATE 필터링)
     */
    @Override
    public List<ChannelDto> findAllByUserId(UUID userId) {
        return channelRepository.findAll().stream()
                .map(this::toDto)
                .toList();
    }


    //수정
    @Override
    public ChannelDto update(ChannelUpdateRequest request) {
        Channel channel = channelRepository.findById(request.channelId())
                .orElseThrow(() -> new NoSuchElementException("채널을 찾을 수 없습니다."));

        if (channel.getType() == ChannelType.PRIVATE) {
            throw new IllegalStateException("비공개 채널은 수정할 수 없습니다.");
        }

        channel.update(request.name(), request.description());
        Channel saved = channelRepository.save(channel);

        return toDto(saved);
    }


    //삭제
    @Override
    public void delete(UUID channelId) {
        if (!channelRepository.existsById(channelId)) {
            throw new NoSuchElementException("채널을 찾을 수 없습니다.");
        }
        channelRepository.deleteById(channelId);
    }

    //dto
    private ChannelDto toDto(Channel channel) {
        return new ChannelDto(
                channel.getId(),
                channel.getType(),
                channel.getName(),
                channel.getDescription(),
                null,   // 최근 메시지 시간 (다음 단계)
                List.of() // 참여 유저 목록 (PRIVATE 채널용, 다음 단계)
        );
    }
}
