package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.entity.Message;

import java.util.List;
import java.util.UUID;

public interface MessageService {

/*  필수요수
        1. Create
        2. Read(find, findAllMessages)
        3. Update
        4. Delete
*/
    Message createMessage(Message message);
    Message findMessage(UUID id);
    List<Message> findAllMessages();
    Message updateMessage(UUID id, String newMessage);
    boolean deleteMessage(UUID id);
}
