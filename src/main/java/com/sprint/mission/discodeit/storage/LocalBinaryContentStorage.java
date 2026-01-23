package com.sprint.mission.discodeit.storage;

import com.sprint.mission.discodeit.dto.data.BinaryContentDto;
import jakarta.annotation.PostConstruct;
import java.io.*;
import java.nio.file.*;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.stereotype.Component;

@Component
// [요구사항] discodeit.storage.type 값이 local 인 경우에만 Bean으로 등록
@ConditionalOnProperty(name = "discodeit.storage.type", havingValue = "local")
public class LocalBinaryContentStorage implements BinaryContentStorage {

  private final Path root;

  // [요구사항] discodeit.storage.local.root-path 설정값을 통해 주입
  public LocalBinaryContentStorage(@Value("${discodeit.storage.local.root-path}") String rootPath) {
    this.root = Paths.get(rootPath);
  }

  // [요구사항] Bean이 생성되면 자동으로 루트 디렉토리를 초기화
  @PostConstruct
  public void init() {
    try {
      Files.createDirectories(root);
    } catch (IOException e) {
      throw new RuntimeException("Could not initialize storage", e);
    }
  }

  @Override
  public UUID put(UUID id, byte[] bytes) {
    Path targetPath = resolvePath(id); // [요구사항] resolvePath 활용
    try {
      Files.write(targetPath, bytes);
      return id;
    } catch (IOException e) {
      throw new RuntimeException("Failed to store file", e);
    }
  }

  @Override
  public InputStream get(UUID id) {
    Path targetPath = resolvePath(id);
    try {
      return Files.newInputStream(targetPath);
    } catch (IOException e) {
      throw new RuntimeException("Failed to read file", e);
    }
  }

  @Override
  public ResponseEntity<Resource> download(BinaryContentDto dto) {
    InputStream inputStream = get(dto.getId()); // [요구사항] get 메소드를 통해 데이터 조회
    Resource resource = new InputStreamResource(inputStream);

    // [요구사항] BinaryContentDto 정보를 활용해 응답 생성
    return ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_DISPOSITION,
            "attachment; filename=\"" + dto.getFileName() + "\"")
        .contentType(MediaType.parseMediaType(dto.getContentType()))
        .contentLength(dto.getSize())
        .body(resource);
  }

  // [요구사항] 파일 저장 위치 규칙 정의: {root}/{UUID}
  private Path resolvePath(UUID id) {
    return root.resolve(id.toString());
  }
}