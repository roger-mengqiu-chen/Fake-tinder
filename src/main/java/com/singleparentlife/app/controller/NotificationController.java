package com.singleparentlife.app.controller;

import com.singleparentlife.app.Util.AuthUtil;
import com.singleparentlife.app.payload.response.JsonResponse;
import com.singleparentlife.app.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://locahost:3000", maxAge = 3600)
@RestController
@RequestMapping("/notification")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;
    @Autowired
    private AuthUtil authUtil;

    @GetMapping()
    public ResponseEntity<JsonResponse> getNotifications(){
        Long userId = authUtil.getCurrentUserId();
        JsonResponse response = notificationService.getAllNotificationOfUser(userId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{notificationId}")
    public ResponseEntity<JsonResponse> getNotificationById(@PathVariable Long notificationId) {
        JsonResponse response = notificationService.getNotificationById(notificationId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{notificationId}")
    public ResponseEntity<JsonResponse> markNotificationRead(@PathVariable Long notificationId) {
        JsonResponse response = notificationService.readNotificationById(notificationId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/readAll")
    public ResponseEntity<JsonResponse> markAllNotificationRead() {
        Long userId = authUtil.getCurrentUserId();
        JsonResponse response = notificationService.readAllNotificationsOfUser(userId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{notificationId}")
    public ResponseEntity<JsonResponse> deleteNotification(@PathVariable Long notificationId) {
        JsonResponse response = notificationService.deleteNotificationById(notificationId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping()
    public ResponseEntity<JsonResponse> deleteAllNotification() {
        Long userId = authUtil.getCurrentUserId();
        JsonResponse response = notificationService.deleteAllNotificationOfUser(userId);
        return ResponseEntity.ok(response);
    }
}
