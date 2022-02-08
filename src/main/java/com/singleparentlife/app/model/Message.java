package com.singleparentlife.app.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Message {

    private long messageId;

    private long senderId;

    private long receiverId;

    private long attachmentId;

    private LocalDateTime time;

    private String content;
}
