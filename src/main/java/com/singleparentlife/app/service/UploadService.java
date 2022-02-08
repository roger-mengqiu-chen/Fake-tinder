package com.singleparentlife.app.service;

import com.singleparentlife.app.constants.DataType;
import com.singleparentlife.app.constants.Status;
import com.singleparentlife.app.mapper.AttachmentMapper;
import com.singleparentlife.app.mapper.MessageMapper;
import com.singleparentlife.app.model.Attachment;
import com.singleparentlife.app.model.Message;
import com.singleparentlife.app.model.Profile;
import com.singleparentlife.app.payload.response.JsonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@Slf4j
public class UploadService {
    @Autowired
    private MessageMapper messageMapper;

    @Autowired
    private AttachmentMapper attachmentMapper;

    public JsonResponse uploadWithMessage(Message message, MultipartFile file) {

        long messageId = messageMapper.save(message);

        if (!file.isEmpty()) {
            try {
                byte[] attachmentContent = file.getBytes();
                String attachmentType = file.getContentType();
                Attachment attachment = new Attachment();
                attachment.setMessageId(messageId);
                attachment.setAttachmentType(attachmentType);
                attachment.setAttachmentContent(attachmentContent);
                attachmentMapper.saveWithMessage(attachment);
                return new JsonResponse(Status.SUCCESS, null, null);
            } catch (IOException e) {
                log.error(e.getMessage());
                return new JsonResponse(Status.FAIL, DataType.SERVER_ERROR, "IO Exception");
            } catch (Exception e) {
                log.error(e.getMessage());
                return new JsonResponse(Status.FAIL, DataType.SERVER_ERROR, null);
            }
        }
        return new JsonResponse(Status.FAIL, DataType.INVALID_INPUT, "File can't be empty");
    }

    public JsonResponse uploadWithProfile(Profile profile, MultipartFile file){
        //TODO
        return null;
    }
}
