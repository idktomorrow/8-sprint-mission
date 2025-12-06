package com.sprint.mission.discodeit.repository.jcf;

import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.repository.MessageRepository;

import java.util.*;

public class JCFMessageRepository implements MessageRepository {

    private Map<UUID, Message> messages = new HashMap<>();

    @Override
    public Message save(Message message) {
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
        Message message = messages.get(id);
        if (message != null) {
            message.updateMessage(newMessage);
        }
        return message;
    }

    @Override
    public boolean deleteMessage(UUID id) {
        return messages.remove(id) != null;
    }
}
