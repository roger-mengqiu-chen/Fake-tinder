package com.singleparentlife.app.controller;

import com.singleparentlife.app.payload.request.MessageRequest;
import com.singleparentlife.app.payload.response.JsonResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/message")
public class MessageController {

    @PostMapping()
    public ResponseEntity<JsonResponse> sendMessage(@RequestBody MessageRequest request) {
        //TODO
        return null;
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
