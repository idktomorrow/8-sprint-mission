package com.sprint.mission.discodeit.service.file;

import com.sprint.mission.discodeit.dto.channel.ChannelDto;
import com.sprint.mission.discodeit.dto.channel.ChannelUpdateRequest;
import com.sprint.mission.discodeit.dto.channel.PrivateChannelCreateRequest;
import com.sprint.mission.discodeit.dto.channel.PublicChannelCreateRequest;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.ChannelType;
import com.sprint.mission.discodeit.service.ChannelService;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;
/*
public class FileChannelService implements ChannelService {
    private static final String EXTENSION = ".ser";
    private final Path DIRECTORY;

    public FileChannelService() {
        this.DIRECTORY = Paths.get(
                System.getProperty("user.dir"),
                "file-data-map",
                Channel.class.getSimpleName()
        );
        try {
            Files.createDirectories(DIRECTORY);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Path resolve(UUID id) {
        return DIRECTORY.resolve(id + EXTENSION);
    }

     ================= create =================

    @Override
    public ChannelDto createPublic(PublicChannelCreateRequest request) {
        Channel channel = new Channel(
                ChannelType.PUBLIC,
                request.name(),
                request.description()
        );
        save(channel);
        return toDto(channel);
    }

    @Override
    public ChannelDto createPrivate(PrivateChannelCreateRequest request) {
        Channel channel = new Channel(
                ChannelType.PRIVATE,
                request.name(),
                request.description()
        );
        save(channel);
        return toDto(channel);
    }

     ================= find =================

    @Override
    public ChannelDto find(UUID channelId) {
        return toDto(load(channelId));
    }

    @Override
    public List<ChannelDto> findAllByUserId(UUID userId) {
        try (Stream<Path> paths = Files.list(DIRECTORY)) {
            return paths
                    .filter(p -> p.toString().endsWith(EXTENSION))
                    .map(this::read)
                    .map(this::toDto)
                    .toList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

     ================= update =================

    @Override
    public ChannelDto update(ChannelUpdateRequest request) {
        Channel channel = load(request.channelId());
        channel.update(request.name(), request.description());
        save(channel);
        return toDto(channel);
    }

     ================= delete =================

    @Override
    public void delete(UUID channelId) {
        Path path = resolve(channelId);
        if (Files.notExists(path)) {
            throw new NoSuchElementException("Channel not found: " + channelId);
        }
        try {
            Files.delete(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

     ================= file helpers =================

    private void save(Channel channel) {
        try (
                FileOutputStream fos = new FileOutputStream(resolve(channel.getId()).toFile());
                ObjectOutputStream oos = new ObjectOutputStream(fos)
        ) {
            oos.writeObject(channel);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Channel load(UUID id) {
        Path path = resolve(id);
        if (Files.notExists(path)) {
            throw new NoSuchElementException("Channel not found: " + id);
        }
        return read(path);
    }

    private Channel read(Path path) {
        try (
                FileInputStream fis = new FileInputStream(path.toFile());
                ObjectInputStream ois = new ObjectInputStream(fis)
        ) {
            return (Channel) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

     ================= mapper =================

    private ChannelDto toDto(Channel channel) {
        return new ChannelDto(
                channel.getId(),
                channel.getName(),
                channel.getDescription(),
                channel.getType(),
                channel.getCreatedAt(),
                channel.getUpdatedAt()
        );
    }
}





*/