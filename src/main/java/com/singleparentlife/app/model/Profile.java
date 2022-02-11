package com.singleparentlife.app.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Profile {

    private Long userId;

    private Long avatarId;

    private Short profileImgNumber;

    private String firstname;

    private String lastname;

    private LocalDate birthday;

    private String gender;

    private String showMe;

    private String description;

    private String company;

    private String jobTitle;

    private String school;

    private Long locationId;

}
