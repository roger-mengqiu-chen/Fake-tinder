package com.singleparentlife.app.payload.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ProfileRequest {

    @JsonProperty
    private String firstname;
    @JsonProperty
    private String lastname;
    @JsonProperty
    private String email;
    @JsonProperty
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate birthday;
    @JsonProperty
    private String gender;
    @JsonProperty
    private String showme;
    @JsonProperty
    private String description;
    @JsonProperty
    private String company;
    @JsonProperty
    private String school;
    @JsonProperty
    private String jobTitle;
    @JsonProperty
    private LocationRequest location;
    @JsonProperty
    private PreferenceRequest preferences;
}
