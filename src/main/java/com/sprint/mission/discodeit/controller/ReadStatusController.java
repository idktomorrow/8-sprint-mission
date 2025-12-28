package com.sprint.mission.discodeit.controller;


import com.sprint.mission.discodeit.dto.request.ReadStatusCreateRequest;
import com.sprint.mission.discodeit.dto.request.ReadStatusUpdateRequest;
import com.sprint.mission.discodeit.entity.ReadStatus;
import com.sprint.mission.discodeit.service.ReadStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/read-status")
public class ReadStatusController {

    private final ReadStatusService readStatusService;

    //생성
    @GetMapping("/create")
    public ReadStatus create(@RequestParam UUID userId,
                             @RequestParam UUID channelId) {
        return readStatusService.create(new ReadStatusCreateRequest(userId, channelId, Instant.now()));
    }

    //전체 조회
    @GetMapping("/findAll")
    public List<ReadStatus> findAllByUserId(@RequestParam UUID userId) {
        return readStatusService.findAllByUserId(userId);
    }

    @GetMapping("/update")
    public ReadStatus update(@RequestParam UUID readStatusId) {
        return readStatusService.update(readStatusId, new ReadStatusUpdateRequest(Instant.now()));
    }
}
