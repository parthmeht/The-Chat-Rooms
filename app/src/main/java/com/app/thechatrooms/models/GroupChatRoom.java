package com.app.thechatrooms.models;

import java.util.ArrayList;

public class GroupChatRoom {
    private String groupId;
    private String groupName;
    private ArrayList<OnlineUser> membersListWithOnlineStatus = new ArrayList<>();
    private String createdBy, createdOn;

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public ArrayList<OnlineUser> getMembersList() {
        return membersListWithOnlineStatus;
    }

    public void setMembersList(ArrayList<OnlineUser> membersList) {
        this.membersListWithOnlineStatus = membersList;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

}
