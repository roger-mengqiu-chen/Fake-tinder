package com.singleparentlife.app.payload.request;

import lombok.Data;

@Data
public class UserRequest {

    private Long userId;

    private String email;

    private String phone;

    private String password;

}
