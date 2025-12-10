package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.entity.Channel;

import java.util.List;
import java.util.UUID;

public interface ChannelService {


    /*  필수요수
        1. Create
        2. Read(find, findAllChannels)
        3. Update
        4. Delete
    */
    Channel createChannel(Channel channel);
    Channel findChannel(UUID id);
    List<Channel> findAllChannels();
    Channel updateChannel(UUID id, String newChannelName);
    boolean deleteChannel(UUID id);

}
