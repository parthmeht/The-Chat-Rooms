package com.app.thechatrooms.models;

public class OnlineUser {

    private String userId;
    private int userOnlineStatus; //1 - online, 0 - offline

    public OnlineUser() {

    }

    public OnlineUser(String userId, int userOnlineStatus) {
        this.userOnlineStatus = userOnlineStatus;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getUserOnlineStatus() {
        return userOnlineStatus;
    }

    public void setUserOnlineStatus(int userOnlineStatus) {
        this.userOnlineStatus = userOnlineStatus;
    }
}
