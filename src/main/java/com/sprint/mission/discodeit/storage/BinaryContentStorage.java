package com.sprint.mission.discodeit.storage;

import com.sprint.mission.discodeit.dto.data.BinaryContentDto;
import java.io.InputStream;
import java.util.UUID;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;

public interface BinaryContentStorage {

  // UUID(BinaryContent ID)를 키로 byte[] 데이터를 저장합니다.
  UUID put(UUID id, byte[] bytes);

  // 키 정보를 바탕으로 데이터를 읽어 InputStream으로 반환합니다.
  InputStream get(UUID id);

  // BinaryContentDto 정보를 활용해 HTTP 다운로드 응답을 생성합니다.
  ResponseEntity<Resource> download(BinaryContentDto dto);
}