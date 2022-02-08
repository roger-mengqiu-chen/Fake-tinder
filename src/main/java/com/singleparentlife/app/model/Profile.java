package com.singleparentlife.app.model;

import lombok.Data;

@Data
public class Profile {

    private long userId;

    private long avatarId;

    private String firstname;

    private String lastname;

    private short age;

    private char gender;

    private String description;

    private String company;

    private String jobTitle;

    private String school;

    private long locationId;

}
