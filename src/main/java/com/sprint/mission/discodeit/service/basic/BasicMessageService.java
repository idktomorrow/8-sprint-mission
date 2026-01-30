package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.data.MessageDto;
import com.sprint.mission.discodeit.dto.request.BinaryContentCreateRequest;
import com.sprint.mission.discodeit.dto.request.MessageCreateRequest;
import com.sprint.mission.discodeit.dto.request.MessageUpdateRequest;
import com.sprint.mission.discodeit.dto.response.PageResponse;
import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.mapper.MessageMapper;
import com.sprint.mission.discodeit.mapper.PageResponseMapper;
import com.sprint.mission.discodeit.repository.BinaryContentRepository;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.service.MessageService;
import com.sprint.mission.discodeit.storage.BinaryContentStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Transactional
public class BasicMessageService implements MessageService {

  private final MessageRepository messageRepository;
  //
  private final ChannelRepository channelRepository;
  private final UserRepository userRepository;
  private final BinaryContentRepository binaryContentRepository;
  private final MessageMapper messageMapper;
  private final BinaryContentStorage binaryContentStorage;
  private final PageResponseMapper pageResponseMapper;

  @Override
  public MessageDto create(MessageCreateRequest messageCreateRequest,
      List<BinaryContentCreateRequest> binaryContentCreateRequests) {

    Channel channel = findChannel(messageCreateRequest.channelId());
    User author = findUser(messageCreateRequest.authorId());

    List<BinaryContent> attachments = saveAttachments(binaryContentCreateRequests);

    Message message = new Message(
        messageCreateRequest.content(),
        channel,
        author,
        attachments
    );
    Message savedMessage = messageRepository.save(message);

    return messageMapper.toDto(savedMessage);
  }

  private Channel findChannel(UUID channelId) {
    return channelRepository.findById(channelId)
        .orElseThrow(() -> new NoSuchElementException("Channel not found: " + channelId));
  }

  private User findUser(UUID authorId) {
    return userRepository.findById(authorId)
        .orElseThrow(() -> new NoSuchElementException("User not found: " + authorId));
  }

  private List<BinaryContent> saveAttachments(List<BinaryContentCreateRequest> requests) {
    if (requests == null || requests.isEmpty()) {
      return List.of();
    }

    return requests.stream()
        .map(req -> {
          BinaryContent content = new BinaryContent(
              req.fileName(),
              req.size(),
              req.contentType()
          );
          BinaryContent savedContent = binaryContentRepository.save(content);

          binaryContentStorage.save(savedContent.getId(), req.inputStream());

          return savedContent;
        })
        .toList();
  }

  @Override
  public MessageDto find(UUID messageId) {
    Message message = messageRepository.findById(messageId)
        .orElseThrow(
            () -> new NoSuchElementException("Message with id " + messageId + " not found"));

    return messageMapper.toDto(message);
  }

  @Override
  public PageResponse<MessageDto> findAllByChannelId(UUID channelId, int page) {
    // A. 50개씩, 최근 순서(createdAt 내림차순)로 가져오겠다는 설정
    Pageable pageable = PageRequest.of(page, 50, Sort.by("createdAt").descending());

    // B. 리포지토리에서 Slice(전체 개수 안 세는 방식)로 가져오기
    Slice<Message> messageSlice = messageRepository.findAllByChannelId(channelId, pageable);

    // C. 가져온 Entity들을 DTO로 하나씩 변환하기
    Slice<MessageDto> messageDtoSlice = messageSlice.map(messageMapper::toDto);

    // D. 웅제님이 만든 PageResponse 상자에 담아서 리턴!
    return pageResponseMapper.from(messageDtoSlice);
  }

  @Override
  public MessageDto update(UUID messageId, MessageUpdateRequest request) {
    Message message = messageRepository.findById(messageId)
        .orElseThrow(
            () -> new NoSuchElementException("Message with id " + messageId + " not found"));

    message.update(request.newContent());
    return messageMapper.toDto(message);
  }

  @Override
  public void delete(UUID messageId) {
    Message message = messageRepository.findById(messageId)
        .orElseThrow(
            () -> new NoSuchElementException("Message with id " + messageId + " not found"));

    // message.getAttachmentIds()
    //         .forEach(binaryContentRepository::deleteById);

    messageRepository.delete(message);
  }
}