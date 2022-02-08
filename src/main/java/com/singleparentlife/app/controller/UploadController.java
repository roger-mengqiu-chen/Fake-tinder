package com.singleparentlife.app.controller;

import com.singleparentlife.app.model.Message;
import com.singleparentlife.app.payload.request.MessageRequest;
import com.singleparentlife.app.payload.response.JsonResponse;
import com.singleparentlife.app.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/upload")
public class UploadController {
    @Autowired
    private UploadService uploadService;

    @PostMapping("/message")
    public ResponseEntity<JsonResponse> uploadWithMessage(@RequestPart MessageRequest msg, @RequestPart MultipartFile file) {

        Long senderId = msg.getSenderId();
        Long receiverId = msg.getReceiverId();
        String content = msg.getContent();
        Message message = new Message();
        message.setReceiverId(receiverId);
        message.setSenderId(senderId);
        message.setContent(content);
        message.setTime(LocalDateTime.now());

        JsonResponse response = uploadService.uploadWithMessage(message, file);
        return response.toResponseEntity();
    }

    @PostMapping("/profile")
    public ResponseEntity<JsonResponse> uploadWithProfile(@RequestBody MultipartFile file) {
        //TODO
        return null;
    }
}
