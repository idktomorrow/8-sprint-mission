package com.sprint.mission.discodeit.controller;


import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.service.BinaryContentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/binary")
public class BinaryContentController {
    private final BinaryContentService binaryContentService;

    //단건 조회
    @RequestMapping("/find")
    public BinaryContent find(@RequestParam UUID id) {
        return binaryContentService.find(id);
    }

    //전체 조회
    @RequestMapping("/findAll")
    public List<BinaryContent> findAll(@RequestParam List<UUID> ids) {
        return binaryContentService.findAllByIdIn(ids);
    }



}
