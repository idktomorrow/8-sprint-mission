package com.sprint.mission.discodeit.storage;

import com.sprint.mission.discodeit.dto.data.BinaryContentDto;
import java.io.InputStream;
import java.util.UUID;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;

public interface BinaryContentStorage {

  UUID save(UUID id, InputStream inputStream);

  InputStream openStream(UUID id);

  // BinaryContentDto 정보를 활용해 HTTP 다운로드 응답을 생성
  Resource loadAsResource(UUID id);

  void delete(UUID id);
}