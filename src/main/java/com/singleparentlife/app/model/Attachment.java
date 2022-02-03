package com.singleparentlife.app.model;

import lombok.Data;

import java.sql.Blob;

@Data
public class Attachment {

    private long attachementId;

    private String attachmentType;

    private Blob attachmentContent;
}
