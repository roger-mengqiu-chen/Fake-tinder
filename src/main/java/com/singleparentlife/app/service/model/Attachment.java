package com.singleparentlife.app.service.model;

import lombok.Data;

@Data
public class Attachment {

    private Long attachmentId;

    private Long messageId;

    private Long userId;

    private String attachmentType;

    private byte[] attachmentContent;
}
