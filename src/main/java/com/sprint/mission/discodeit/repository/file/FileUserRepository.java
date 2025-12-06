package com.sprint.mission.discodeit.repository.file;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.UserRepository;

import java.io.*;
import java.util.*;

public class FileUserRepository implements UserRepository {
    private final String folderPath;

    // ★ 폴더 경로를 인자로 받는 생성자
    public FileUserRepository(String folderPath) {
        this.folderPath = folderPath;
        File folder = new File(folderPath);
        if (!folder.exists()) folder.mkdirs();
    }

    private File getFile(UUID id) {
        return new File(folderPath, id + ".ser");
    }

    @Override
    public User save(User user) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(getFile(user.getId())))) {
            oos.writeObject(user);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return user;
    }

    @Override
    public User findId(UUID id) {
        File file = getFile(id);
        if (!file.exists()) return null;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (User) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<User> findAllUsers() {
        List<User> users = new ArrayList<>();
        File folder = new File(folderPath);
        for (File file : Objects.requireNonNull(folder.listFiles())) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                users.add((User) ois.readObject());
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return users;
    }

    @Override
    public User update(UUID id, String newUserId, String newUserEmail) {
        User user = findId(id);
        if (user != null) {
            user.update(newUserId, newUserEmail);
            save(user);
        }
        return user;
    }

    @Override
    public boolean delete(UUID id) {
        File file = getFile(id);
        return file.exists() && file.delete();
    }
}
