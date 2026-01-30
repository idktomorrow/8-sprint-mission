package com.sprint.mission.discodeit.storage;

import com.sprint.mission.discodeit.dto.data.BinaryContentDto;
import jakarta.annotation.PostConstruct;
import java.io.*;
import java.net.MalformedURLException;
import java.nio.file.*;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
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
      throw new UncheckedIOException("Could not initialize storage", e);
    }
  }

  @Override
  public UUID save(UUID id, InputStream inputStream) {
    Path targetPath = resolvePath(id); // [요구사항] resolvePath 활용
    try {
      Files.copy(inputStream, targetPath, StandardCopyOption.REPLACE_EXISTING);
      return id;
    } catch (IOException e) {
      throw new UncheckedIOException("Failed to store file", e);
    }
  }

  @Override
  public InputStream openStream(UUID id) {
    Path targetPath = resolvePath(id);
    try {
      return Files.newInputStream(targetPath);
    } catch (IOException e) {
      throw new UncheckedIOException("Failed to read file", e);
    }
  }

  @Override
  public Resource loadAsResource(UUID id) {
    Path targetPath = resolvePath(id);
    try {
      return new UrlResource(targetPath.toUri());
    } catch (MalformedURLException e) {
      throw new UncheckedIOException("Failed to load file as resource", e);
    }
  }

  @Override
  public void delete(UUID id) {
    Path filePath = resolvePath(id);
    try {
      // 파일이 존재할 때만 삭제 (없으면 무시)
      Files.deleteIfExists(filePath);
    } catch (IOException e) {
      throw new UncheckedIOException("파일 삭제 중 오류가 발생했습니다: " + id, e);
    }
  }

  // [요구사항] 파일 저장 위치 규칙 정의: {root}/{UUID}
  private Path resolvePath(UUID id) {
    return root.resolve(id.toString());
  }
}