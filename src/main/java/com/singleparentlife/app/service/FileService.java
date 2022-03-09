package com.singleparentlife.app.service;

import com.singleparentlife.app.Util.FileUtil;
import com.singleparentlife.app.Util.LinkUtil;
import com.singleparentlife.app.constants.DataType;
import com.singleparentlife.app.constants.Status;
import com.singleparentlife.app.mapper.AttachmentMapper;
import com.singleparentlife.app.mapper.ProfileMapper;
import com.singleparentlife.app.model.Attachment;
import com.singleparentlife.app.model.Profile;
import com.singleparentlife.app.payload.response.AttachmentResponse;
import com.singleparentlife.app.payload.response.JsonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Slf4j
public class FileService {
    @Autowired
    private ProfileMapper profileMapper;
    @Autowired
    private AttachmentMapper attachmentMapper;
    @Autowired
    private FileUtil fileUtil;
    @Autowired
    private LinkUtil linkUtil;

    public JsonResponse uploadWithProfile(Long userId, MultipartFile file){
        if (!fileUtil.isValidImage(file)) {
            return new JsonResponse(Status.FAIL, DataType.INVALID_IMAGE, null);
        }
        Profile profile = profileMapper.findByUserId(userId);
        if (profile == null) {
            return new JsonResponse(Status.FAIL, DataType.PROFILE_NOT_FOUND, null);
        }
        short profileImgAmt = profile.getProfileImgAmt();
        // each profile should have <= 9 profile images
        if (profileImgAmt >= 9) {
            return new JsonResponse(Status.FAIL, DataType.TOO_MANY_PROFILE_IMG, null);
        }

        try {
            // create attachment object
            Attachment attachment = fileUtil.fileToAttachment(file);
            attachment.setUserId(userId);
            attachmentMapper.saveWithProfile(attachment);
            attachment.setAttachmentId(attachment.getAttachmentId());
            // profile image amount need update
            profile.increaseProfileImgAmt();
            profileMapper.updateProfileImgAmt(profile);
            AttachmentResponse attachmentResponse = new AttachmentResponse();
            BeanUtils.copyProperties(attachment, attachmentResponse);
            return new JsonResponse(Status.SUCCESS, DataType.ATTACHMENT, attachmentResponse);
        } catch (IOException e) {
            log.error(e.getMessage());
            return new JsonResponse(Status.FAIL, DataType.SERVER_ERROR, "IO Exception");
        } catch (Exception e) {
            log.error(e.getMessage());
            return new JsonResponse(Status.FAIL, DataType.SERVER_ERROR, null);
        }
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
            AttachmentResponse attachmentResponse = new AttachmentResponse();
            BeanUtils.copyProperties(attachment, attachmentResponse);
            log.info("Attachment updated: {}", attachmentId);
            return new JsonResponse(Status.SUCCESS, DataType.ATTACHMENT,  attachmentResponse);
        } catch (IOException e) {
            log.error(e.getMessage());
            return new JsonResponse(Status.FAIL, DataType.SERVER_ERROR, "IO Exception");
        } catch (Exception e) {
            log.error(e.getMessage());
            return new JsonResponse(Status.FAIL, DataType.SERVER_ERROR, null);
        }
    }

    public JsonResponse deleteAttachment(Long attachmentId) {
        Long existingId = attachmentMapper.findIdOfAttachment(attachmentId);
        Long profileId = attachmentMapper.getProfileIdOfAttachment(attachmentId);
        Profile profile = profileMapper.findByUserId(profileId);

        if (existingId == null) {
            log.error("Attachment not found: {}", attachmentId);
            return new JsonResponse(Status.FAIL, DataType.ATTACHMENT_NOT_FOUND, null);
        }
        try {
            attachmentMapper.delete(attachmentId);
            profile.reduceProfileImgAmt();
            log.info("Attachment deleted: {}", attachmentId);
            return new JsonResponse(Status.SUCCESS, DataType.ATTACHMENT_IDS,  attachmentId);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new JsonResponse(Status.FAIL, DataType.SERVER_ERROR, null);
        }
    }

    public Attachment getAttachmentByMessageId(long messageId) {
        return attachmentMapper.findByMessageId(messageId);
    }

    public List<String> getAttachmentByProfileId(long userId) {
        List<Long> attachmentIds = attachmentMapper.findByProfileId(userId);
        List<String> attachmentLinks = attachmentIds.stream().map(linkUtil::generateProfileImageLink).collect(Collectors.toList());
        return attachmentLinks;
    }

    public Attachment getAttachmentById(long attachmentId) {
        return attachmentMapper.findById(attachmentId);
    }

}
