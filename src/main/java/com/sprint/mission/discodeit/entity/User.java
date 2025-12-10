package com.sprint.mission.discodeit.entity;

import java.io.Serializable;
import java.util.UUID;

public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    /*
        필수 요소
        1. id
        2. 생성 시간
        3. 업데이트 시간
        4. 유저ID
        5. 유저Email
    */

    //필드 선언
    private UUID id;
    private long createdAt;
    private long updatedAt;
    private String userId;
    private String userEmail;

    //생성자
    public User(String userId,String userEmail) {
        this.id = UUID.randomUUID(); //UUID를 사용해 고유id
        this.createdAt = System.currentTimeMillis(); //system.currentTimeMillis는 java 제공 메서드
        this.updatedAt = System.currentTimeMillis();

        this.userId = userId;
        this.userEmail = userEmail;
    }

    //getter 함수 정의
    public UUID getId() {
        return id;
    }
    public String getUserId() {
        return userId;
    }
    public String getUserEmail() {
        return userEmail;
    }
    public long getCreatedAt() {
        return createdAt;
    }
    public long getUpdatedAt() {
        return updatedAt;
    }

    //update 함수 정의
    public void update(String newUserId, String newUserEmail) {
        this.userId = newUserId;
        this.userEmail = newUserEmail;
        this.updatedAt = System.currentTimeMillis();
    }
}
