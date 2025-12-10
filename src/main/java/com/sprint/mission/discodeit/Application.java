package com.sprint.mission.discodeit;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.repository.file.FileChannelRepository;
import com.sprint.mission.discodeit.repository.file.FileMessageRepository;
import com.sprint.mission.discodeit.repository.file.FileUserRepository;
import com.sprint.mission.discodeit.service.file.FileChannelService;
import com.sprint.mission.discodeit.service.file.FileMessageService;
import com.sprint.mission.discodeit.service.file.FileUserService;

public class Application {

    static void userCrudTest(FileUserService fileUserService) {
        User user1 = fileUserService.createUser(new User("웅제1", "woongjae1@naver.com"));
        User user2 = fileUserService.createUser(new User("영제1", "youngjae1@naver.com"));

        System.out.println("생성된 사용자 수 : " + fileUserService.findAllUsers().size());

        fileUserService.updateUser(user1.getId(), "웅제2", "woongjae2@naver.com");
        System.out.println("수정 후 사용자 조회 : " + fileUserService.findId(user1.getId()).getUserId());

        fileUserService.deleteUser(user1.getId());
        System.out.println("삭제 후 사용자 조회 : " + fileUserService.findId(user1.getId()));
    }

    static void channelCrudTest(FileChannelService fileChannelService) {
        Channel channel1 = fileChannelService.createChannel(new Channel("일반 채널1"));
        Channel channel2 = fileChannelService.createChannel(new Channel("특별 채널1"));

        System.out.println("전체 채널 수 : " + fileChannelService.findAllChannels().size());

        fileChannelService.updateChannel(channel1.getId(), "일반 채널2");
        System.out.println("수정 후 채널 조회 : " + fileChannelService.findChannel(channel1.getId()).getChannelName());

        fileChannelService.deleteChannel(channel1.getId());
        System.out.println("삭제 후 채널 조회 : " + fileChannelService.findChannel(channel1.getId()));
    }

    static void messageCrudTest(FileUserService fileUserService,
                                FileChannelService fileChannelService,
                                FileMessageService fileMessageService) {
        User user = fileUserService.createUser(new User("테스트 유저", "test@naver.com"));
        Channel channel = fileChannelService.createChannel(new Channel("테스트 채널"));

        Message message = fileMessageService.createMessage(new Message(channel, user, "테스트 메세지"));
        System.out.println("메세지 조회 : " + fileMessageService.findMessage(message.getId()).getMessage());

        fileMessageService.updateMessage(message.getId(), "수정된 메세지");
        System.out.println("수정 후 메세지 : " + fileMessageService.findMessage(message.getId()).getMessage());

        fileMessageService.deleteMessage(message.getId());
        System.out.println("삭제 후 메세지 조회 : " + fileMessageService.findMessage(message.getId()));
    }

    public static void main(String[] args) {

        // Repository 생성
        UserRepository userRepository = new FileUserRepository("data/users");        // 사용자 파일 폴더
        ChannelRepository channelRepository = new FileChannelRepository("data/channels"); // 채널 파일 폴더
        MessageRepository messageRepository = new FileMessageRepository("data/messages"); // 메시지 파일 폴더

        // File 기반 서비스 생성
        FileUserService fileUserService = new FileUserService(userRepository);
        FileChannelService fileChannelService = new FileChannelService(channelRepository);
        FileMessageService fileMessageService = new FileMessageService(messageRepository);

        // CRUD 테스트
        userCrudTest(fileUserService);
        channelCrudTest(fileChannelService);
        messageCrudTest(fileUserService, fileChannelService, fileMessageService);
    }
}



