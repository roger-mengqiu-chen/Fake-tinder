package com.singleparentlife.app.payload.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UserRequest {
    @JsonProperty
    private Long userId;
    @JsonProperty
    private String email;
    @JsonProperty
    private String phone;

}
