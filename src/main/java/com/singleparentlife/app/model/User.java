package com.singleparentlife.app.model;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class User implements Serializable {

    private Long userId;

    private String fireId;

    private String email;

    private String phone;

    private LocalDate startDate;

    private LocalDateTime loginTime;

    private Integer roleId;

    private boolean isActive;

    private boolean isSuspended;
}
