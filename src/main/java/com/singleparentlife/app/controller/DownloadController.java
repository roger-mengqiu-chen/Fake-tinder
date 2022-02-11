package com.singleparentlife.app.controller;

import com.singleparentlife.app.constants.DataType;
import com.singleparentlife.app.constants.Status;
import com.singleparentlife.app.model.Attachment;
import com.singleparentlife.app.payload.response.JsonResponse;
import com.singleparentlife.app.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/download")
public class DownloadController {

    @Autowired
    private FileService fileService;

    @GetMapping("/message/{messageId}")
    public ResponseEntity getAttachmentByMessageId(@PathVariable Long messageId){

        if (messageId == null) {
            return ResponseEntity.badRequest().body(
                    new JsonResponse(Status.FAIL, DataType.INVALID_INPUT, "Message shouldn't be empty"));
        }
        Attachment attachment = fileService.getAttachmentByMessageId(messageId);
        if (attachment == null) {
            return ResponseEntity.badRequest().body(
                    new JsonResponse(Status.FAIL, DataType.MESSAGE_NOT_FOUND, null));
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(attachment.getAttachmentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION)
                .body(attachment.getAttachmentContent());
    }

    @GetMapping("/profile/{userId}")
    public ResponseEntity getAttachmentByProfileId(@PathVariable Long userId){

        if (userId == null) {
            return ResponseEntity.badRequest().body(
                    new JsonResponse(Status.FAIL, DataType.INVALID_INPUT, "UserId can't be empty")
            );
        }
        List<Long> attachmentIds = fileService.getAttachmentByProfileId(userId);

        return ResponseEntity.ok().body(
                new JsonResponse(Status.SUCCESS, DataType.ATTACHMENT_IDS, attachmentIds)
        );
    }

    @GetMapping("/{attachmentId}")
    public ResponseEntity getAttachmentById(@PathVariable Long attachmentId) {
        Attachment attachment = fileService.getAttachmentById(attachmentId);
        if (attachment == null) {
            return ResponseEntity.badRequest().body(
                    new JsonResponse(Status.FAIL, DataType.ATTACHMENT_NOT_FOUND, null)
            );
        }
        else {
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(attachment.getAttachmentType()))
                    .header(HttpHeaders.CONTENT_DISPOSITION)
                    .body(attachment.getAttachmentContent());
        }
    }
}
