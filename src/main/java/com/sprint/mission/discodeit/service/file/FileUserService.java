package com.sprint.mission.discodeit.service.file;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.service.UserService;

import java.util.List;
import java.util.UUID;

//저장 로직
public class FileUserService implements UserService {

    private UserRepository userRepository;

    public FileUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User findId(UUID id) {
        return userRepository.findId(id);
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAllUsers();
    }

    @Override
    public User updateUser(UUID id, String newUserId, String newEmail) {
        return userRepository.update(id, newUserId, newEmail);
    }

    @Override
    public boolean deleteUser(UUID id) {
        return userRepository.delete(id);
    }
}
