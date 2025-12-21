package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.dto.binary.BinaryContentCreateRequest;
import com.sprint.mission.discodeit.entity.BinaryContent;

import java.util.List;
import java.util.UUID;

public interface BinaryContentService {

    //바이너리컨텐츠생성 저장
    BinaryContent create(BinaryContentCreateRequest request);

    //단일 조회
    BinaryContent find(UUID id);

    //다건 조회
    List<BinaryContent> findAllByIds(List<UUID> ids);

    //바이너리컨텐츠삭제
    void delete(UUID id);
}
