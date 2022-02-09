package com.singleparentlife.app.service;

import com.singleparentlife.app.constants.DataType;
import com.singleparentlife.app.constants.Status;
import com.singleparentlife.app.mapper.AttachmentMapper;
import com.singleparentlife.app.mapper.MessageMapper;
import com.singleparentlife.app.mapper.ProfileMapper;
import com.singleparentlife.app.model.Attachment;
import com.singleparentlife.app.model.Message;
import com.singleparentlife.app.model.Profile;
import com.singleparentlife.app.payload.response.JsonResponse;
import com.singleparentlife.app.payload.response.MessageResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@Slf4j
public class MessageService {

    @Autowired
    private MessageMapper messageMapper;

    @Autowired
    private ProfileMapper profileMapper;

    @Autowired
    private AttachmentMapper attachmentMapper;

    public JsonResponse sendMessage (Message message, MultipartFile file) {
        long messageId = messageMapper.save(message);
        message.setMessageId(messageId);

        Profile sender = profileMapper.findByUserId(message.getSenderId());
        Profile receiver = profileMapper.findByUserId(message.getReceiverId());

        MessageResponse messageResponse = new MessageResponse();
        messageResponse.setMessageId(messageId);
        messageResponse.setSenderId(message.getSenderId());
        messageResponse.setSenderName(sender.getFirstname());
        messageResponse.setReceiverId(message.getReceiverId());
        messageResponse.setReceiverName(receiver.getFirstname());
        messageResponse.setAttachmentId(message.getMessageId());
        messageResponse.setContent(message.getContent());
        messageResponse.setTime(message.getTime());

        if (!file.isEmpty()) {
            try {
                byte[] attachmentContent = file.getBytes();
                String attachmentType = file.getContentType();
                Attachment attachment = new Attachment();
                attachment.setMessageId(messageId);
                attachment.setAttachmentType(attachmentType);
                attachment.setAttachmentContent(attachmentContent);
                long attachmentId = attachmentMapper.saveWithMessage(attachment);
                message.setAttachmentId(attachmentId);
                messageMapper.updateAttachmentId(message);
                messageResponse.setAttachmentId(attachmentId);

            } catch (IOException e) {
                log.error(e.getMessage());
                return new JsonResponse(Status.FAIL, DataType.SERVER_ERROR, "IO Exception");
            } catch (Exception e) {
                log.error(e.getMessage());
                return new JsonResponse(Status.FAIL, DataType.SERVER_ERROR, null);
            }
        }
        return new JsonResponse(Status.SUCCESS, DataType.MESSAGE, messageResponse);
    }
}
