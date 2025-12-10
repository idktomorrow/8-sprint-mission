package com.sprint.mission.discodeit.repository.jcf;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.repository.ChannelRepository;

import java.util.*;

public class JCFChannelRepository implements ChannelRepository {

    private Map<UUID, Channel> channels = new HashMap<>();

    @Override
    public Channel save(Channel channel) {
        channels.put(channel.getId(), channel);
        return channel;
    }

    @Override
    public Channel findChannel(UUID id) {
        return channels.get(id);
    }

    @Override
    public List<Channel> findAllChannels() {
        return new ArrayList<>(channels.values());
    }

    @Override
    public Channel updateChannel(UUID id, String newChannelName) {
        Channel channel = channels.get(id);
        if (channel != null) {
            channel.update(newChannelName);
        }
        return channel;
    }

    @Override
    public boolean deleteChannel(UUID id) {
        return channels.remove(id) != null;
    }
}
