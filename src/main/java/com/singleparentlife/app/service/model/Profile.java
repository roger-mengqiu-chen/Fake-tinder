package com.singleparentlife.app.service.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Profile {

    private Long userId;

    private Long avatarId;

    private short profileImgAmt;

    private String firstname;

    private String lastname;

    private LocalDate birthday;

    private String gender;

    private String showme;

    private String description;

    private String company;

    private String jobTitle;

    private String school;

    private Long locationId;

    private double distanceToMe;

    private int numberOfMatchedPreferencesWithMe;

    public void reduceProfileImgAmt() {
        if (this.profileImgAmt <= 0) {
            throw new UnsupportedOperationException();
        }
        this.profileImgAmt --;
    }

    public void increaseProfileImgAmt() {
        if (this.profileImgAmt >= 9) {
            throw new UnsupportedOperationException();
        }
        this.profileImgAmt ++;
    }

}
