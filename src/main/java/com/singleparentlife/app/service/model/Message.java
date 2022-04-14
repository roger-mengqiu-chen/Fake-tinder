package com.singleparentlife.app.service.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Message {

    private Long messageId;

    private Long senderId;

    private Long receiverId;

    private Long attachmentId;

    private LocalDateTime time;

    private String content;
}
