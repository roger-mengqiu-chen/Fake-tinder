package com.singleparentlife.app.model;

import lombok.Data;

@Data
public class Notification {

    private Long notificationId;

    private Long userId;

    private String content;

    private Boolean isRead;
}
