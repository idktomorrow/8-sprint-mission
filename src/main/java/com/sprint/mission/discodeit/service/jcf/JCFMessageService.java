package com.sprint.mission.discodeit.service.jcf;

import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.service.MessageService;

import java.util.*;

public class JCFMessageService implements MessageService {

    private Map<UUID, Message> messages;

    public JCFMessageService() {
        this.messages = new HashMap<>();
    }

    @Override
    public Message createMessage(Message message) {
        messages.put(message.getId(), message);
        return message;
    }

    @Override
    public Message findMessage(UUID id) {
        return messages.get(id);
    }

    @Override
    public List<Message> findAllMessages() {
        return new ArrayList<>(messages.values());
    }

    @Override
    public Message updateMessage(UUID id, String newMessage) {
        Message changeMessage = messages.get(id);
        if (changeMessage != null) {
            changeMessage.updateMessage(newMessage);
        }
        return changeMessage;
    }

    @Override
    public boolean deleteMessage(UUID id) {
        return messages.remove(id) != null;
    }
}
