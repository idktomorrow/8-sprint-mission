package com.sprint.mission.discodeit;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.service.jcf.JCFChannelService;
import com.sprint.mission.discodeit.service.jcf.JCFMessageService;
import com.sprint.mission.discodeit.service.jcf.JCFUserService;

public class JavaApplication {
    //수정 - null값 체크 안해서 오류 발생
    static void userCrudTest(JCFUserService jcfUserService) {
        //Create
        User user1 = jcfUserService.createUser(new User("웅제1", "woongjae1@naver.com"));
        System.out.println("사용자 생성 : " + user1.getUserId());
        User user2 = jcfUserService.createUser(new User("영제1", "youngjae1@naver.com"));
        System.out.println("사용자 생성 : " + user2.getUserId());
        System.out.println("------------------------------");

        //Read(단건)
        User findUser = jcfUserService.findId(user1.getId());
        System.out.println("사용자 조회 : "  + findUser.getUserId());
        //Read(다건)
        System.out.println("전체 사용자 조회");
        for (User user : jcfUserService.findAllUsers()) {
            System.out.println("사용자 ID : " + user.getUserId());
        }
        System.out.println("------------------------------");

        //Update
        jcfUserService.updateUser(user1.getId(), "웅제2","woongjae2@naer.com");
        System.out.println("사용자 수정 조회 : " + jcfUserService.findId(user1.getId()).getUserId());

        //Delete
        boolean deleteUser = jcfUserService.deleteUser(user1.getId());
        System.out.println("사용자 삭제 확인 : " + deleteUser);

        User deleteUser1 = jcfUserService.findId(user1.getId());
        if (deleteUser1 != null) {
            System.out.println("사용자 삭제 확인 조회 : " + deleteUser1.getUserId());
        } else {
            System.out.println("사용자 삭제 확인 조회 : 사용자가 존재하지 않습니다.");
        }
        System.out.println("------------------------------");
    }

    static void channelCrudTest(JCFChannelService jcfChannelService) {
        //Create
        Channel channel1 = jcfChannelService.createChannel(new Channel("일반 채널1"));
        System.out.println("채널 생성 : " + channel1.getChannelName());
        Channel channel2 = jcfChannelService.createChannel(new Channel("특별 채널1"));

        //Read
        Channel findChannel = jcfChannelService.findChannel(channel1.getId());
        System.out.println("채널 조회 : " + findChannel.getChannelName());
        System.out.println("전체 채널 조회");
        for (Channel channel : jcfChannelService.findAllChannels()) {
            System.out.println("채널 ID : " + channel.getChannelName());
        }
        System.out.println("------------------------------");

        //Update
        jcfChannelService.updateChannel(channel1.getId(),"일반 채널2");
        System.out.println("채널 수정 조회 : " +  jcfChannelService.findChannel(channel1.getId()).getChannelName());

        //Delete
        boolean deleteChannel = jcfChannelService.deleteChannel(channel1.getId());
        System.out.println("채널 삭제 확인 : " + deleteChannel);

        Channel deleteChannel1 = jcfChannelService.findChannel(channel1.getId());
        if (deleteChannel1 != null) {
            System.out.println("채널 삭제 확인 조회 : " + deleteChannel1.getChannelName());
        } else {
            System.out.println("채널 삭제 확인 조회 : 채널이 존재하지 않습니다.");
        }
        System.out.println("------------------------------");
    }

    static void messageCrudTest(JCFMessageService jcfMessageService, JCFUserService jcfUserService, JCFChannelService jcfChannelService) {

        //Create
        User user00 = jcfUserService.createUser(new User("임시 웅제", "tempWoongjae@naver.com"));
        Channel channel00 = jcfChannelService.createChannel(new Channel("임시 채널1"));
        Message message1 = jcfMessageService.createMessage(new Message(channel00,user00,"자바는 재미있어요."));
        System.out.println("메세지 등록 완료 : " + message1.getMessage());
        User user01 = jcfUserService.createUser(new User("임시 영제", "tempYoungjae@naver.com"));
        Channel channel01 = jcfChannelService.createChannel(new Channel("임시 채널2"));
        Message message00 = jcfMessageService.createMessage(new Message(channel01,user01,"내 목숨을 아이어에!"));
        System.out.println("메세지 등록 완료 : " + message00.getMessage());
        System.out.println("------------------------------");

        //Read
        Message findMessage = jcfMessageService.findMessage(message1.getId());
        System.out.println("\"임시 웅제\" 메세지 조회 : " + findMessage.getMessage());
        System.out.println("전체 메세지 조회");
        for (Message message : jcfMessageService.findAllMessages()) {
            System.out.println("메세지 : " + message.getMessage());
        }

        //Update
        jcfMessageService.updateMessage(message1.getId(),"자바는.. 재미..있어요..");
        System.out.println("메세지 수정 조회 : " + jcfMessageService.findMessage(message1.getId()).getMessage());

        //Delete
        boolean deleteMessage = jcfMessageService.deleteMessage(message1.getId());
        System.out.println("메세지 삭제 확인 : " + deleteMessage);

        Message deleteMessage1 = jcfMessageService.findMessage(message1.getId());
        if (deleteMessage1 != null) {
            System.out.println("메세지 삭제 확인 조회 : " + deleteMessage1.getMessage());
        } else {
            System.out.println("메세지 삭제 확인 조회 : 메세지가 존재하지 않습니다.");
        }
        System.out.println("------------------------------");
    }

    public static void main(String[] args) {
        //서비스 초기화
        JCFUserService jcfUserService = new JCFUserService();
        JCFChannelService jcfChannelService = new JCFChannelService();
        JCFMessageService jcfMessageService = new JCFMessageService();

        //도메인 CRUD 테스트
        userCrudTest(jcfUserService);
        channelCrudTest(jcfChannelService);
        messageCrudTest(jcfMessageService, jcfUserService, jcfChannelService);

    }

}
