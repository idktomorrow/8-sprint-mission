package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.dto.data.ChannelDto;
import com.sprint.mission.discodeit.dto.request.PrivateChannelCreateRequest;
import com.sprint.mission.discodeit.dto.request.PublicChannelCreateRequest;
import com.sprint.mission.discodeit.dto.request.PublicChannelUpdateRequest;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.service.ChannelService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/channels")
public class ChannelController {
    private final ChannelService channelService;

    // 공개 채널 생성
    @GetMapping("/create/public")
    public Channel createPublic(@RequestParam String name,
                                @RequestParam String description) {
        return channelService.create(new PublicChannelCreateRequest(name, description));
    }

    // 비공개 채널 생성
    @GetMapping("/create/private")
    public Channel createPrivate(@RequestParam List<UUID> participantIds) {
        return channelService.create(new PrivateChannelCreateRequest(participantIds));
    }

    // 조회
    @GetMapping("/find")
    public ChannelDto find(@RequestParam UUID channelId) {
        return channelService.find(channelId);
    }

    // 전체 조회 (사용자 기준)
    @GetMapping("/findAll")
    public List<ChannelDto> findAllByUserId(@RequestParam UUID userId) {
        return channelService.findAllByUserId(userId);
    }

    // 수정 (공개 채널만)
    @GetMapping("/update")
    public Channel update(@RequestParam UUID channelId,
                          @RequestParam String newName,
                          @RequestParam String newDescription) {
        return channelService.update(channelId, new PublicChannelUpdateRequest(newName, newDescription));
    }

    // 삭제
    @GetMapping("/delete")
    public String delete(@RequestParam UUID channelId) {
        channelService.delete(channelId);
        return "Channel deleted";
    }
}
