package com.singleparentlife.app.payload.response;

import lombok.Data;

@Data
public class SanitizedUser {

    private Long userId;

    private String fireId;

    private String role;

    private String email;

    private String phone;

}
