package com.singleparentlife.app.model;

import lombok.Data;

@Data
public class Profile {

    private Long userId;

    private Long avatarId;

    private String firstname;

    private String lastname;

    private Short age;

    private Character gender;

    private String description;

    private String company;

    private String jobTitle;

    private String school;

    private Long locationId;

}
