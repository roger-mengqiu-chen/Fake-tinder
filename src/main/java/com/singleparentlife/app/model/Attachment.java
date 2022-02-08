package com.singleparentlife.app.model;

import lombok.Data;

@Data
public class Attachment {

    private long attachmentId;

    private long messageId;

    private long userId;

    private String attachmentType;

    private byte[] attachmentContent;
}
