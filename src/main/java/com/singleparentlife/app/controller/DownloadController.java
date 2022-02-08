package com.singleparentlife.app.controller;

import com.singleparentlife.app.constants.DataType;
import com.singleparentlife.app.constants.Status;
import com.singleparentlife.app.model.Attachment;
import com.singleparentlife.app.payload.response.JsonResponse;
import com.singleparentlife.app.service.DownloadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/download")
public class DownloadController {

    @Autowired
    private DownloadService downloadService;

    @GetMapping("/message/{messageId}")
    public ResponseEntity getAttachmentByMessageId(@PathVariable Long messageId){

        if (messageId == null) {
            return ResponseEntity.badRequest().body(
                    new JsonResponse(Status.FAIL, DataType.INVALID_INPUT, "Message shouldn't be empty"));
        }
        Attachment attachment = downloadService.getAttachmentByMessageId(messageId);
        if (attachment == null) {
            return ResponseEntity.badRequest().body(
                    new JsonResponse(Status.FAIL, DataType.MESSAGE_NOT_FOUND, null));
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(attachment.getAttachmentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION)
                .body(attachment.getAttachmentContent());
    }

    @GetMapping("/profile/{profileId}")
    public byte[] getAttachmentByProfileId(){
        //TODO
        return null;
    }
}
