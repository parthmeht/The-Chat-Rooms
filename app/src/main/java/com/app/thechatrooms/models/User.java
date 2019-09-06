package com.app.thechatrooms.models;

import android.net.Uri;

import java.io.Serializable;

public class User implements Serializable {
    private String id;
    private Uri userProfileImage;
    private String firstName;
    private String lastName;
    private String emailId;
    private String city;
    private String gender;

    public User(String firstName, String lastName, String emailId, String city, String gender) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailId = emailId;
        this.city = city;
        this.gender = gender;
    }

    public User(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Uri getUserProfileImage() {
        return userProfileImage;
    }

    public void setUserProfileImage(Uri userProfileImage) {
        this.userProfileImage = userProfileImage;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", emailId='" + emailId + '\'' +
                ", city='" + city + '\'' +
                ", gender='" + gender + '\'' +
                '}';
    }
}
