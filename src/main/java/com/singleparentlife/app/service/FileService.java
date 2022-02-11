package com.singleparentlife.app.service;

import com.singleparentlife.app.Util.FileUtil;
import com.singleparentlife.app.constants.DataType;
import com.singleparentlife.app.constants.Status;
import com.singleparentlife.app.mapper.AttachmentMapper;
import com.singleparentlife.app.mapper.ProfileMapper;
import com.singleparentlife.app.model.Attachment;
import com.singleparentlife.app.payload.response.JsonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.List;


@Service
@Slf4j
public class FileService {
    @Autowired
    private ProfileMapper profileMapper;
    @Autowired
    private AttachmentMapper attachmentMapper;
    @Autowired
    private FileUtil fileUtil;


    public JsonResponse uploadWithProfile(Long userId, MultipartFile file){
        try {
            // create attachment object
            Attachment attachment = fileUtil.fileToAttachment(file);
            attachment.setUserId(userId);

            attachmentMapper.saveWithProfile(attachment);
        } catch (IOException e) {
            log.error(e.getMessage());
            return new JsonResponse(Status.FAIL, DataType.SERVER_ERROR, "IO Exception");
        } catch (Exception e) {
            log.error(e.getMessage());
            return new JsonResponse(Status.FAIL, DataType.SERVER_ERROR, null);
        }
        return new JsonResponse(Status.SUCCESS, DataType.PROFILE, userId);
    }

    public JsonResponse updateExistingAttachment(Long attachmentId, MultipartFile file) {
        try {
            Attachment attachment = fileUtil.fileToAttachment(file);
            Long existingAttachmentId = attachmentMapper.findIdOfAttachment(attachmentId);
            if (existingAttachmentId == null) {
                return new JsonResponse(Status.FAIL, DataType.ATTACHMENT_NOT_FOUND, null);
            }
            attachment.setAttachmentId(existingAttachmentId);
            attachmentMapper.updateAttachment(attachment);
            log.info("Attachment updated: {}", attachmentId);
            return new JsonResponse(Status.SUCCESS, DataType.ATTACHMENT_IDS,  attachmentId);
        } catch (IOException e) {
            log.error(e.getMessage());
            return new JsonResponse(Status.FAIL, DataType.SERVER_ERROR, "IO Exception");
        } catch (Exception e) {
            log.error(e.getMessage());
            return new JsonResponse(Status.FAIL, DataType.SERVER_ERROR, null);
        }
    }

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
