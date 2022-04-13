package com.singleparentlife.app.service;

import com.singleparentlife.app.Util.FileUtil;
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
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
@Slf4j
public class MessageService {

    @Autowired
    private MessageMapper messageMapper;

    @Autowired
    private ProfileMapper profileMapper;

    @Autowired
    private AttachmentMapper attachmentMapper;

    @Autowired
    private FileUtil fileUtil;

    public JsonResponse sendMessage (Message message, MultipartFile file) {
        messageMapper.save(message);
        message.setMessageId(message.getMessageId());

        Profile sender = profileMapper.findByUserId(message.getSenderId());
        Profile receiver = profileMapper.findByUserId(message.getReceiverId());

        MessageResponse messageResponse = new MessageResponse();

        BeanUtils.copyProperties(message, messageResponse);
        messageResponse.setSenderName(sender.getFirstname());
        messageResponse.setReceiverName(receiver.getFirstname());

        if (file != null && !file.isEmpty()) {
            try {
                Attachment attachment = fileUtil.fileToAttachment(file);
                attachment.setMessageId(message.getMessageId());
                attachmentMapper.saveWithMessage(attachment);
                message.setAttachmentId(message.getMessageId());
                messageMapper.updateAttachmentId(message);
                messageResponse.setAttachmentId(message.getAttachmentId());
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

    public JsonResponse getCombinedMessageHistory(long currentUser, long otherUser){
        List<Message> messageHistory = messageMapper.getCombinedMessage(currentUser, otherUser);

        if (messageHistory.equals(null) || messageHistory.isEmpty()){
            try {
                throw new IOException();
            } catch (IOException e){
                log.error((e.getMessage()));
                return new JsonResponse(Status.FAIL, DataType.SERVER_ERROR, "IO Exception");
            }
        }
        return new JsonResponse(Status.SUCCESS, DataType.MESSAGE, messageHistory);

    }

    public JsonResponse getAllMessage(long userId){
        List<Message> messageHistory = messageMapper.getAllUserMessage(userId);

        if(messageHistory.isEmpty()){
            try {
                throw new IOException();
            } catch (IOException e){
                log.error((e.getMessage()));
                return new JsonResponse(Status.FAIL, DataType.SERVER_ERROR, "IO Exception");
            }
        }
        return new JsonResponse(Status.SUCCESS, DataType.MESSAGE, messageHistory);
    }


    public JsonResponse deleteChatHistoryWithUser(long userId){
        messageMapper.deleteAll(userId);
        return new JsonResponse(Status.SUCCESS, null, null);
    }


}
