package com.sprint.mission.discodeit.service.file;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.service.ChannelService;

import java.util.List;
import java.util.UUID;

//저장 로직
public class FileChannelService implements ChannelService {

    private ChannelRepository channelRepository;

    public FileChannelService(ChannelRepository channelRepository) {
        this.channelRepository = channelRepository;
    }

    @Override
    public Channel createChannel(Channel channel) {
        return channelRepository.save(channel);
    }

    @Override
    public Channel findChannel(UUID id) {
        return channelRepository.findChannel(id);
    }

    @Override
    public List<Channel> findAllChannels() {
        return channelRepository.findAllChannels();
    }

    @Override
    public Channel updateChannel(UUID id, String newChannelName) {
        return channelRepository.updateChannel(id, newChannelName);
    }

    @Override
    public boolean deleteChannel(UUID id) {
        return channelRepository.deleteChannel(id);
    }
}
