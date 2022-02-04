package com.singleparentlife.app.controller;

import com.singleparentlife.app.payload.request.MessageRequest;
import com.singleparentlife.app.payload.response.JsonResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/message")
public class MessageController {

    @PostMapping()
    public JsonResponse sendMessage(@RequestBody MessageRequest request) {
        //TODO
        return null;
    }

    @GetMapping("/{userId}")
    public JsonResponse getChatHistoryWithUser(@PathVariable Long userId) {
        //TODO
        return null;
    }

    @DeleteMapping("/{messageId}")
    public JsonResponse deleteMessage(@PathVariable Long messageId) {
        //TODO
        return null;
    }

    @DeleteMapping("/user-{userId}")
    public JsonResponse deleteChatHistoryWithUser(@PathVariable Long userId) {
        //TODO
        return null;
    }

    @DeleteMapping("/allmessage")
    public JsonResponse deleteAllMessageOfUser() {
        //TODO
        return null;
    }
}
