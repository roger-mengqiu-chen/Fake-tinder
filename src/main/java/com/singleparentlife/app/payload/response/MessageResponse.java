package com.singleparentlife.app.payload.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MessageResponse {

    private Long messageId;

    private Long senderId;

    private String senderName;

    private Long receiverId;

    private String receiverName;

    private Long attachmentId;

    private LocalDateTime time;

    private String content;
}
