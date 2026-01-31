package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.dto.data.BinaryContentDto;
import com.sprint.mission.discodeit.dto.request.BinaryContentCreateRequest;
import java.util.List;
import java.util.UUID;

public interface BinaryContentService {

  // 반환 타입을 BinaryContent -> BinaryContentDto로 변경
  BinaryContentDto create(BinaryContentCreateRequest request);

  // 반환 타입을 BinaryContent -> BinaryContentDto로 변경
  BinaryContentDto find(UUID binaryContentId);

  // 반환 타입을 List<BinaryContent> -> List<BinaryContentDto>로 변경
  List<BinaryContentDto> findAllByIdIn(List<UUID> binaryContentIds);

  void delete(UUID binaryContentId);
}
