package com.sprint.mission.discodeit.controller;


import com.sprint.mission.discodeit.dto.data.UserDto;
import com.sprint.mission.discodeit.dto.request.LoginRequest;
import com.sprint.mission.discodeit.dto.request.UserCreateRequest;
import com.sprint.mission.discodeit.dto.request.UserStatusUpdateRequest;
import com.sprint.mission.discodeit.dto.request.UserUpdateRequest;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.service.AuthService;
import com.sprint.mission.discodeit.service.UserService;
import com.sprint.mission.discodeit.service.UserStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final AuthService authService;
    private final UserStatusService userStatusService;

    // 생성
    @GetMapping("/create")
    public User create(@RequestParam String username,
                       @RequestParam String email,
                       @RequestParam String password) {
        UserCreateRequest request = new UserCreateRequest(username, email, password);
        return userService.create(request, Optional.empty());
    }

    // 조회
    @GetMapping("/find")
    public UserDto find(@RequestParam UUID userId) {
        return userService.find(userId);
    }

    // 전체 조회
    @GetMapping("/findAll")
    public java.util.List<UserDto> findAll() {
        return userService.findAll();
    }

    // 수정
    @GetMapping("/update")
    public User update(@RequestParam UUID userId,
                       @RequestParam String newUsername,
                       @RequestParam String newEmail,
                       @RequestParam String newPassword) {
        UserUpdateRequest request = new UserUpdateRequest(newUsername, newEmail, newPassword);
        return userService.update(userId, request, Optional.empty());
    }

    // 삭제
    @GetMapping("/delete")
    public String delete(@RequestParam UUID userId) {
        userService.delete(userId);
        return "User deleted";
    }

    // 로그인
    @GetMapping("/login")
    public User login(@RequestParam String username,
                      @RequestParam String password) {
        return authService.login(new LoginRequest(username, password));
    }

    @GetMapping("/update-status")
    public String updateStatus(@RequestParam UUID userId) {
        userStatusService.updateByUserId(userId,
                new UserStatusUpdateRequest(Instant.now()));
        UserDto user = userService.find(userId); // 이름 가져오기
        return "User " + user.username() + " status updated to now";
    }

}
