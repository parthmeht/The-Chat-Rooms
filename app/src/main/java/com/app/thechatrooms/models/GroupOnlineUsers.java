package com.app.thechatrooms.models;

public class GroupOnlineUsers {
    private String userId;
    private boolean userOnlineStatus; //1 - online, 0 - offline

    public GroupOnlineUsers(String userId, boolean userOnlineStatus) {
        this.userId = userId;
        this.userOnlineStatus = userOnlineStatus;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public boolean isUserOnlineStatus() {
        return userOnlineStatus;
    }

    public void setUserOnlineStatus(boolean userOnlineStatus) {
        this.userOnlineStatus = userOnlineStatus;
    }

}
