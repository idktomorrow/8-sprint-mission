package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.entity.User;

import java.util.List;
import java.util.UUID;

public interface UserService {

    /*  필수요수
        1. Create
        2. Read(find, findAllUsers)
        3. Update
        4. Delete
    */

    User createUser(User user);
    User findId(UUID id);
    List<User> findAllUsers();
    User updateUser(UUID id, String newUserId, String newEmail);
    boolean deleteUser(UUID id);



}
