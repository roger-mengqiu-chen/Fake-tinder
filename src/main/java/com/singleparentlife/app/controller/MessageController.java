package com.singleparentlife.app.controller;

import com.singleparentlife.app.model.Message;
import com.singleparentlife.app.payload.request.MessageRequest;
import com.singleparentlife.app.payload.response.JsonResponse;
import com.singleparentlife.app.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;


@RestController
@RequestMapping("/message")
public class MessageController {
    @Autowired
    private MessageService messageService;

    @PostMapping()
    public ResponseEntity<JsonResponse>sendMessage (@RequestPart("message") MessageRequest message, @RequestPart("file")MultipartFile file){

        Long senderId = message.getSenderId();
        Long receiverId = message.getReceiverId();
        String content = message.getContent();
        Message msg = new Message();
        msg.setReceiverId(receiverId);
        msg.setSenderId(senderId);
        msg.setContent(content);
        msg.setTime(LocalDateTime.now());

        JsonResponse response = messageService.sendMessage(msg, file);

        return response.toResponseEntity();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<JsonResponse> getChatHistoryWithUser(@PathVariable Long userId) {
        //TODO
        return null;
    }

    @DeleteMapping("/{messageId}")
    public ResponseEntity<JsonResponse> deleteMessage(@PathVariable Long messageId) {
        //TODO
        return null;
    }

    @DeleteMapping("/user-{userId}")
    public ResponseEntity<JsonResponse> deleteChatHistoryWithUser(@PathVariable Long userId) {
        //TODO
        return null;
    }

    @DeleteMapping("/allmessage")
    public ResponseEntity<JsonResponse> deleteAllMessageOfUser() {
        //TODO
        return null;
    }
}
