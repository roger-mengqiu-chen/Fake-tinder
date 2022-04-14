package com.singleparentlife.app.service.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AppNotification {

    private Long notificationId;

    private Long userId;

    private String topic;

    private String title;

    private String content;

    private LocalDateTime time;

    private Boolean isRead;
}
