package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.binary.BinaryContentCreateRequest;
import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.repository.BinaryContentRepository;
import com.sprint.mission.discodeit.service.BinaryContentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class BasicBinaryContentService implements BinaryContentService {
    private final BinaryContentRepository binaryContentRepository;

    //바이너리 컨텐츠 생성
    @Override
    public BinaryContent create(BinaryContentCreateRequest request) {
        BinaryContent binaryContent = new BinaryContent(
                request.contentType(),
                request.size(),
                request.storagePath()
        );

        return binaryContentRepository.save(binaryContent);
    }

    //단일 조회
    @Override
    public BinaryContent find(UUID id) {
        return binaryContentRepository.findById(id)
                .orElseThrow(() ->
                        new NoSuchElementException("BinaryContent not found")
                );
    }

    //다건 조회
    @Override
    public List<BinaryContent> findAllByIds(List<UUID> ids) {
        return binaryContentRepository.findAllByIdIn(ids);
    }

    //삭제
    @Override
    public void delete(UUID id) {
        binaryContentRepository.deleteById(id);
    }
}
