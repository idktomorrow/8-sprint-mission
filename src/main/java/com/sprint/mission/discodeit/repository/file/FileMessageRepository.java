package com.sprint.mission.discodeit.repository.file;

import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.repository.MessageRepository;

import java.io.*;
import java.util.*;

public class FileMessageRepository implements MessageRepository {
    private final String folderPath;

    public FileMessageRepository(String folderPath) {
        this.folderPath = folderPath;
        File folder = new File(folderPath);
        if (!folder.exists()) folder.mkdirs();
    }

    private File getFile(UUID id) {
        return new File(folderPath, id + ".ser");
    }

    @Override
    public Message save(Message message) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(getFile(message.getId())))) {
            oos.writeObject(message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return message;
    }

    @Override
    public Message findMessage(UUID id) {
        File file = getFile(id);
        if (!file.exists()) return null;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (Message) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Message> findAllMessages() {
        List<Message> messages = new ArrayList<>();
        File folder = new File(folderPath);
        for (File file : Objects.requireNonNull(folder.listFiles())) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                messages.add((Message) ois.readObject());
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return messages;
    }

    @Override
    public Message updateMessage(UUID id, String newMessage) {
        Message message = findMessage(id);
        if (message != null) {
            message.updateMessage(newMessage);
            save(message);
        }
        return message;
    }

    @Override
    public boolean deleteMessage(UUID id) {
        File file = getFile(id);
        return file.exists() && file.delete();
    }
}
