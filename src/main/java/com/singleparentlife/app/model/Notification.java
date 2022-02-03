package com.singleparentlife.app.model;

import lombok.Data;

@Data
public class Notification {

    private long notificationId;

    private long userId;

    private String content;

    private boolean isRead;
}
