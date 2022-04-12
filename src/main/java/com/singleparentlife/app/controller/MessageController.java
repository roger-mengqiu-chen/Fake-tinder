package com.singleparentlife.app.controller;

import com.singleparentlife.app.Util.AuthUtil;
import com.singleparentlife.app.model.Message;
import com.singleparentlife.app.payload.request.MessageRequest;
import com.singleparentlife.app.payload.request.NotificationRequest;
import com.singleparentlife.app.payload.response.JsonResponse;
import com.singleparentlife.app.service.MessageService;
import com.singleparentlife.app.service.NotificationService;
import com.singleparentlife.app.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@CrossOrigin(origins = "http://locahost:3000", maxAge = 3600)
@RestController
@RequestMapping("/message")
public class MessageController {

    @Autowired
    private SimpMessagingTemplate simpleMessagingTemplate;

    @Autowired
    private MessageService messageService;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private ProfileService profileService;

    @Autowired
    private AuthUtil authUtil;

    @PostMapping()
    public ResponseEntity<JsonResponse>sendMessage (@RequestPart("message") MessageRequest message, @RequestPart(value = "file", required = false)MultipartFile file){

        Long senderId = authUtil.getCurrentUserId();
        Long receiverId = message.getReceiverId();
        String content = message.getContent();
        Message msg = new Message();
        msg.setReceiverId(receiverId);
        msg.setSenderId(senderId);
        msg.setContent(content);
        msg.setTime(LocalDateTime.now());

        JsonResponse response = messageService.sendMessage(msg, file);

        //To send using socket
        receivePrivateMessage(msg);

        NotificationRequest notificationRequest = new NotificationRequest();
        notificationRequest.setTopic("Message");
        notificationRequest.setTitle("You have a new message");
        notificationRequest.setBody("You received a new text message from " + senderId);
        notificationService.sendNotification(receiverId, notificationRequest);

        return ResponseEntity.ok(response);
    }

    @MessageMapping("/privateMessage") // /app/privateMessage
    public Message receivePrivateMessage(@Payload Message message){

        simpleMessagingTemplate.convertAndSendToUser(Long.toString(message.getReceiverId()), "/private", message); // /user/userId/private
        return message;
    }


    @GetMapping("/singleHistory/{userId}")
    public ResponseEntity<JsonResponse> getChatHistoryWithUser(@PathVariable Long userId) {
        long currentUser = authUtil.getCurrentUserId();
        JsonResponse response = messageService.getCombinedMessageHistory(currentUser, userId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/allHistory/{userId}")
    public ResponseEntity<JsonResponse> getAllChatHistory(@PathVariable Long userId){
        long currentUser = authUtil.getCurrentUserId();
        JsonResponse response = messageService.getAllMessage(userId);
        return ResponseEntity.ok(response);
    }

    //@DeleteMapping("/{messageId}")
    //public ResponseEntity<JsonResponse> deleteMessage(@PathVariable Long messageId) {
    //TODO
    //  return null;
    //}

    //  @DeleteMapping("/user-{userId}")
    //public ResponseEntity<JsonResponse> deleteChatHistoryWithUser(@PathVariable Long userId) {
    //   JsonResponse response = messageService.deleteChatHistoryWithUser(userId);
    // return null;
    // }

    @DeleteMapping("/allmessage")
    public ResponseEntity<JsonResponse> deleteAllMessageOfUser(@PathVariable long userId) {
        JsonResponse response = messageService.deleteChatHistoryWithUser(userId);
        return null;
    }
}