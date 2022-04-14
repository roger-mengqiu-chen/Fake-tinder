package com.singleparentlife.app.service.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReportedUser {

    private Long userId;

    private Long reporterId;

    private String reason;

    private LocalDateTime reportTime;
}
