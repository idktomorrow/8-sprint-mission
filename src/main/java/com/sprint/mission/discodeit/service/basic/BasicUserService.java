package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.user.UserCreateRequest;
import com.sprint.mission.discodeit.dto.user.UserDto;
import com.sprint.mission.discodeit.dto.user.UserUpdateRequest;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.repository.UserStatusRepository;
import com.sprint.mission.discodeit.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class BasicUserService implements UserService {
    private final UserRepository userRepository;
    private final UserStatusRepository userStatusRepository;


    //생성
    @Override
    public UserDto create(UserCreateRequest request) {
        User user = new User(
                request.username(),
                request.email(),
                request.password(),
                request.profileImageId()
        );
        User savedUser = userRepository.save(user);
        UserStatus status = new UserStatus(savedUser.getId());
        userStatusRepository.save(status);

        return toDto(savedUser, status);
    }


    //조회
    @Override
    public UserDto find(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User not found"));

        UserStatus status = userStatusRepository.findByUserId(userId).orElse(null);


        return toDto(user, status);
    }

    //조회(전체)
    @Override
    public List<UserDto> findAll() {
        return userRepository.findAll().stream()
                .map(user -> {
                    UserStatus status =
                            userStatusRepository.findByUserId(user.getId()).orElse(null);
                    return toDto(user, status);
                }
                )
                .toList();
    }


    //수정
    @Override
    public UserDto update(UserUpdateRequest request) {
        User user = userRepository.findById(request.id())
                .orElseThrow(() -> new NoSuchElementException("User not found"));

        user.update(
                request.username(),
                request.email(),
                request.password(),
                request.newProfileImageID()
        );

        User saved = userRepository.save(user);

        UserStatus status = userStatusRepository.findByUserId(user.getId()).orElse(null);

        return toDto(saved, status);
    }


    //삭제
    @Override
    public void delete(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User not found"));

        // 1. UserStatus 삭제
        userStatusRepository.findByUserId(userId)
                .ifPresent(status -> userStatusRepository.deleteById(status.getId()));

        // 2. 프로필 이미지 삭제 (있다면)
        if (user.getProfileImageId() != null) {
            //binaryContentRepository.deleteById(user.getProfileImageId());
        }

        userRepository.deleteById(userId);
    }


    //dto
    private UserDto toDto(User user, UserStatus status) {
        boolean isOnline = status != null && status.isActive();

        return new UserDto(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getProfileImageId(),
                isOnline
        );
    }
}
