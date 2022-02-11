package com.singleparentlife.app.service;

import com.singleparentlife.app.mapper.AttachmentMapper;
import com.singleparentlife.app.model.Attachment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DownloadService {

    @Autowired
    private AttachmentMapper attachmentMapper;

    public Attachment getAttachmentByMessageId(long messageId) {
        return attachmentMapper.findByMessageId(messageId);
    }

    public List<Long> getAttachmentByProfileId(long userId) {
        return attachmentMapper.findByProfileId(userId);
    }

    public Attachment getAttachmentById(long attachmentId) {
        return attachmentMapper.findById(attachmentId);
    }
}
