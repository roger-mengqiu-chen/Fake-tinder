package com.singleparentlife.app.payload.request;

import lombok.Data;

@Data
public class NotificationRequest {

    private String topic;

    private String title;

    private String body;
}
