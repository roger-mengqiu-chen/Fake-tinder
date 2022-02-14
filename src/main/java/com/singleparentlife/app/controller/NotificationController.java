package com.singleparentlife.app.controller;

import com.singleparentlife.app.payload.response.JsonResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://locahost:3000", maxAge = 3600)
@RestController
@RequestMapping("/notification")
public class NotificationController {

    @PostMapping("/send/{userId}")
    public ResponseEntity<JsonResponse> sendNotificationToUser(Long userId){
        //TODO
        return null;
    }

    @GetMapping()
    public ResponseEntity<JsonResponse> getNotifications(){
        //TODO
        return null;
    }

    @PutMapping("/{notificationId}")
    public ResponseEntity<JsonResponse> markNotificationRead(@PathVariable Long notificationId) {
        //TOdo
        return null;
    }

    @PutMapping("/readAll")
    public ResponseEntity<JsonResponse> markAllNotificationRead() {
        //TODO
        return null;
    }

    @DeleteMapping("/{notificationId}")
    public ResponseEntity<JsonResponse> deleteNotification(@PathVariable Long notificationId) {
        //TODO
        return null;
    }
}
