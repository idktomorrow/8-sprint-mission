package com.sprint.mission.discodeit.repository;

import com.sprint.mission.discodeit.entity.Channel;

import java.util.List;
import java.util.UUID;

public interface ChannelRepository {

    Channel save(Channel channel);
    Channel findChannel(UUID id);
    List<Channel> findAllChannels();
    Channel updateChannel(UUID id, String newChannelName);
    boolean deleteChannel(UUID id);
}
