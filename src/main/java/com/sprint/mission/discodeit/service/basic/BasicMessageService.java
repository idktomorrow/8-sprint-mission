package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.message.MessageCreateRequest;
import com.sprint.mission.discodeit.dto.message.MessageDto;
import com.sprint.mission.discodeit.dto.message.MessageUpdateRequest;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.repository.ReadStatusRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.service.MessageService;
import com.sprint.mission.discodeit.service.ReadStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class BasicMessageService implements MessageService {
    private final MessageRepository messageRepository;
    //
    private final ReadStatusService readStatusService;

    //생성
    @Override
    public MessageDto create(MessageCreateRequest request) {

        Message message = new Message(
                request.content(),
                request.channelId(),
                request.authorId()
        );

        Message saved = messageRepository.save(message);

        //메세지를 보낸 유저는 해당 채널을 읽은 상태가 됨
        readStatusService.updateLastRead(request.authorId(), request.channelId());

        return toDto(saved);
    }

    //조회
    @Override
    public MessageDto find(UUID messageId){
        Message message = messageRepository.findById(messageId)
                .orElseThrow(() -> new NoSuchElementException("Message not found"));

            return toDto(message);
        }

    //조회(전체)
    @Override
    public List<MessageDto> findAll() {
        return messageRepository.findAll().stream()
                .map(this::toDto)
                .toList();
    }

    //수정
    @Override
    public MessageDto update(UUID messageId, MessageUpdateRequest request) {
        Message message = messageRepository.findById(messageId)
                .orElseThrow(() -> new NoSuchElementException("Message not found"));

        message.update(request.content());

        Message saved = messageRepository.save(message);
        return toDto(saved);
    }


    //삭제
    @Override
    public void delete(UUID messageId) {
        if (!messageRepository.existsById(messageId)) {
            throw new NoSuchElementException("Message with id " + messageId + " not found");
        }
        messageRepository.deleteById(messageId);
    }

    //dto
    private MessageDto toDto(Message message) {
        return new MessageDto(
                message.getId(),
                message.getChannelId(),
                message.getAuthorId(),
                message.getContent(),
                message.getCreatedAt(),
                message.getUpdatedAt()
        );

    }
}
