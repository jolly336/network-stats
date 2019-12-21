package com.nelson.stats;

/**
 * Created by Nelson on 2019-12-13.
 */
public class User {

    private final String userId;
    private final String userName;

    public User(String userId, String userName) {
        this.userId = userId;
        this.userName = userName;
    }

    public String getUserId() {
        return this.userId;
    }

    public String getUserName() {
        return this.userName;
    }
}
