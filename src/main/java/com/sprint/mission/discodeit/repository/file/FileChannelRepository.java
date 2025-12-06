package com.sprint.mission.discodeit.repository.file;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.repository.ChannelRepository;

import java.io.*;
import java.util.*;

public class FileChannelRepository implements ChannelRepository {
    private final String folderPath;

    public FileChannelRepository(String folderPath) {
        this.folderPath = folderPath;
        File folder = new File(folderPath);
        if (!folder.exists()) folder.mkdirs();
    }

    private File getFile(UUID id) {
        return new File(folderPath, id + ".ser");
    }

    @Override
    public Channel save(Channel channel) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(getFile(channel.getId())))) {
            oos.writeObject(channel);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return channel;
    }

    @Override
    public Channel findChannel(UUID id) {
        File file = getFile(id);
        if (!file.exists()) return null;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (Channel) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Channel> findAllChannels() {
        List<Channel> channels = new ArrayList<>();
        File folder = new File(folderPath);
        for (File file : Objects.requireNonNull(folder.listFiles())) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                channels.add((Channel) ois.readObject());
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return channels;
    }

    @Override
    public Channel updateChannel(UUID id, String newChannelName) {
        Channel channel = findChannel(id);
        if (channel != null) {
            channel.update(newChannelName);
            save(channel);
        }
        return channel;
    }

    @Override
    public boolean deleteChannel(UUID id) {
        File file = getFile(id);
        return file.exists() && file.delete();
    }
}
