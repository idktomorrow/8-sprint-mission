package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    // 로그인 (GET으로 테스트용)
    @GetMapping("/login")
    public User login(@RequestParam String username, @RequestParam String password) {
        return authService.login(new com.sprint.mission.discodeit.dto.request.LoginRequest(username, password));
    }
}