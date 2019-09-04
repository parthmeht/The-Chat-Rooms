package com.app.thechatrooms.models;

import java.util.ArrayList;

public class Messages {
    private String messageId;
    private String message;
    private ArrayList<String> likesUserId = new ArrayList<>();
    private String createdBy;
    private String createdOn;

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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

    public ArrayList<String> getLikesUserId() {
        return likesUserId;
    }

    public void setLikesUserId(ArrayList<String> likesUserId) {
        this.likesUserId = likesUserId;
    }
}
