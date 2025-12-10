package com.sprint.mission.discodeit.repository.jcf;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.UserRepository;

import java.util.*;

public class JCFUserRepository implements UserRepository {

    private Map<UUID, User> users = new HashMap<>();

    @Override
    public User save(User user) {
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
    public User update(UUID id, String newUserName, String newUserEmail) {
        User user = users.get(id);
        if (user != null) {
            user.update(newUserName, newUserEmail);
        }
        return user;
    }

    @Override
    public boolean delete(UUID id) {
        return users.remove(id) != null;
    }
}
