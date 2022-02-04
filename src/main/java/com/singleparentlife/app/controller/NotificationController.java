package com.singleparentlife.app.controller;

import com.singleparentlife.app.payload.response.JsonResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notification")
public class NotificationController {

    @PostMapping("/send/{userId}")
    public JsonResponse sendNotificationToUser(Long userId){
        //TODO
        return null;
    }

    @GetMapping()
    public JsonResponse getNotifications(){
        //TODO
        return null;
    }

    @PutMapping("/{notificationId}")
    public JsonResponse markNotificationRead(@PathVariable Long notificationId) {
        //TOdo
        return null;
    }

    @PutMapping("/readAll")
    public JsonResponse markAllNotificationRead() {
        //TODO
        return null;
    }

    @DeleteMapping("/{notificationId}")
    public JsonResponse deleteNotification(@PathVariable Long notificationId) {
        //TODO
        return null;
    }
}
