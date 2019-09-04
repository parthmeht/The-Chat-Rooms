package com.app.thechatrooms.models;

import java.util.ArrayList;

public class GroupChatRoom {
    private String groupId;
    private String groupName;
    private String groupIcon;
    private ArrayList<String> membersList = new ArrayList<>();
    private ArrayList<Messages> messageList = new ArrayList<>();
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

    public String getGroupIcon() {
        return groupIcon;
    }

    public void setGroupIcon(String groupIcon) {
        this.groupIcon = groupIcon;
    }

    public ArrayList<String> getMembersList() {
        return membersList;
    }

    public void setMembersList(ArrayList<String> membersList) {
        this.membersList = membersList;
    }

    public ArrayList<Messages> getMessageList() {
        return messageList;
    }

    public void setMessageList(ArrayList<Messages> messageList) {
        this.messageList = messageList;
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
