package com.singleparentlife.app.payload.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDate;

@Data
public class SignupRequest {

    @JsonProperty
    private String email;
    @JsonProperty
    private String phone;
    @JsonProperty
    private String firstname;
    @JsonProperty
    private String lastname;
    @JsonProperty
    private LocalDate birthday;
    @JsonProperty
    private String gender;
    @JsonProperty
    private String showMe;
    @JsonProperty
    private String description;
    @JsonProperty
    private String company;
    @JsonProperty
    private String jobTitle;
    @JsonProperty
    private String school;
}
