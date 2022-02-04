package com.singleparentlife.app.payload.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ProfileRequest {
    @JsonProperty
    private Long userId;
    @JsonProperty
    private String firstname;
    @JsonProperty
    private String lastname;
    @JsonProperty
    private Short age;
    @JsonProperty
    private String male;
    @JsonProperty
    private String description;
    @JsonProperty
    private LocationRequest location;
}
