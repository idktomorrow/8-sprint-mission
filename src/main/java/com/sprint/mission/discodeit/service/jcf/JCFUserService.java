package com.sprint.mission.discodeit.service.jcf;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.service.UserService;

import java.util.*;
import java.util.stream.Collectors;

public class JCFUserService implements UserService {

    /*
        필수요소
        1. 데이터를 저장할 필드 선언
        2. create
        3. read(update)
        4. update
        5. delete
    */

    private Map<UUID, User> users;
    public JCFUserService() {
        this.users = new HashMap<>();
    }

    @Override
    public User createUser(User user) {
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public User findId(UUID id) {
        return users.get(id);
    }

    @Override
    public List<User> findAllUsers() {
        return new ArrayList<>(users.values());
    }

    @Override
    public User updateUser(UUID id, String newUserId , String newUserEmail) {
        User changeUser = users.get(id);
        if (changeUser != null) {
            changeUser.update(newUserId, newUserEmail);
        }
        return changeUser;
    }

    @Override
    public boolean deleteUser(UUID id) {
        return users.remove(id) != null;
    }
//

}
