package com.singleparentlife.app.service;

import com.singleparentlife.app.mapper.AttachmentMapper;
import com.singleparentlife.app.mapper.MessageMapper;
import com.singleparentlife.app.model.Attachment;
import com.singleparentlife.app.payload.response.JsonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DownloadService {

    @Autowired
    private AttachmentMapper attachmentMapper;

    public Attachment getAttachmentByMessageId(long messageId) {

        return attachmentMapper.findByMessageId(messageId);

    }
}
