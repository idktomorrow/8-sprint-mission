package com.sprint.mission.discodeit;

import com.sprint.mission.discodeit.dto.channel.ChannelDto;
import com.sprint.mission.discodeit.dto.channel.PublicChannelCreateRequest;
import com.sprint.mission.discodeit.dto.message.MessageCreateRequest;
import com.sprint.mission.discodeit.dto.message.MessageDto;
import com.sprint.mission.discodeit.dto.user.UserCreateRequest;
import com.sprint.mission.discodeit.dto.user.UserDto;
import com.sprint.mission.discodeit.service.ChannelService;
import com.sprint.mission.discodeit.service.MessageService;
import com.sprint.mission.discodeit.service.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;


@SpringBootApplication
public class DiscodeitApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(DiscodeitApplication.class, args);

		UserService userService = context.getBean(UserService.class);
		ChannelService channelService = context.getBean(ChannelService.class);
		MessageService messageService = context.getBean(MessageService.class);

		UserDto user = setupUser(userService);
		ChannelDto channel = setupChannel(channelService);
		messageCreateTest(messageService, channel, user);
	}

	static UserDto setupUser(UserService userService) {
		return userService.create(
				new UserCreateRequest(
						"woody",
						"woody@codeit.com",
						"woody1234",
						null
				)
		);
	}

	static ChannelDto setupChannel(ChannelService channelService) {
		return channelService.createPublic(
				new PublicChannelCreateRequest(
						"공지",
						"공지 채널입니다."
				)
		);
	}

	static void messageCreateTest(
			MessageService messageService,
			ChannelDto channel,
			UserDto user
	) {
		MessageDto message = messageService.create(
				new MessageCreateRequest(
						"안녕하세요.",
						channel.id(),
						user.id()
				)
		);

		System.out.println("메시지 생성: " + message.id());

	}


}