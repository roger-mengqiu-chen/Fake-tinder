package com.singleparentlife.app.payload.response;

import lombok.Data;

@Data
public class AttachmentResponse {

    private Long attachmentId;

    private Long userId;

    private Long messageId;

    private String attachmentType;
}
