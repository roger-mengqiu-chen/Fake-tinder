package com.singleparentlife.app.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReportedUser {

    private long userId;

    private long reporterId;

    private String reason;

    private LocalDateTime reportTime;
}
