package com.sprint.mission.discodeit.service.jcf;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.service.ChannelService;

import java.util.*;

public class JCFChannelService implements ChannelService {

    private Map<UUID, Channel> channels;
    public JCFChannelService() {
        this.channels = new HashMap<>();
    }

    @Override
    public Channel createChannel(Channel channel) {
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
        Channel changeChannel = channels.get(id);
        if (changeChannel != null) {
            changeChannel.update(newChannelName);
        }
        return changeChannel;
    }

    @Override
    public boolean deleteChannel(UUID id) {
        return channels.remove(id) != null;
    }


}
