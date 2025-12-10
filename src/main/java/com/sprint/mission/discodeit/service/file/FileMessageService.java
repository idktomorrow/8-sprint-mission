package com.sprint.mission.discodeit.service.file;

import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.service.MessageService;

import java.util.List;
import java.util.UUID;

//저장 조릭
public class FileMessageService implements MessageService {

    private MessageRepository messageRepository;

    public FileMessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Override
    public Message createMessage(Message message) {
        return messageRepository.save(message);
    }

    @Override
    public Message findMessage(UUID id) {
        return messageRepository.findMessage(id);
    }

    @Override
    public List<Message> findAllMessages() {
        return messageRepository.findAllMessages();
    }

    @Override
    public Message updateMessage(UUID id, String newMessage) {
        return messageRepository.updateMessage(id, newMessage);
    }

    @Override
    public boolean deleteMessage(UUID id) {
        return messageRepository.deleteMessage(id);
    }
}
