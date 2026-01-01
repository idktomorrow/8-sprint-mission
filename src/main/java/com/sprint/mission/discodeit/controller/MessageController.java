package com.sprint.mission.discodeit.controller;


import com.sprint.mission.discodeit.dto.request.MessageCreateRequest;
import com.sprint.mission.discodeit.dto.request.MessageUpdateRequest;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/messages")
public class MessageController {

    private final MessageService messageService;

    // 생성
    @RequestMapping("/create")
    public Message create(@RequestParam UUID channelId,
                          @RequestParam UUID authorId,
                          @RequestParam String content) {
        return messageService.create(
                new MessageCreateRequest(content, channelId, authorId),
                List.of()
        );
    }

    // 조회
    @RequestMapping("/find")
    public Message find(@RequestParam UUID messageId) {
        return messageService.find(messageId);
    }

    // 전체 조회 (채널 기준)
    @RequestMapping("/findAll")
    public List<Message> findAll(@RequestParam UUID channelId) {
        return messageService.findAllByChannelId(channelId);
    }

    // 수정
    @RequestMapping("/update")
    public Message update(@RequestParam UUID messageId,
                          @RequestParam String newContent) {
        return messageService.update(messageId, new MessageUpdateRequest(newContent));
    }

    // 삭제
    @RequestMapping("/delete")
    public String delete(@RequestParam UUID messageId) {
        messageService.delete(messageId);
        return "Message deleted";
    }
}
