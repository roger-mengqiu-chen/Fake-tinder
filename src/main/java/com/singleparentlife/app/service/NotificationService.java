package com.singleparentlife.app.service;

import com.singleparentlife.app.mapper.NotificationMapper;
import com.singleparentlife.app.model.Notification;
import com.singleparentlife.app.payload.response.JsonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    @Autowired
    private NotificationMapper notificationMapper;

    public JsonResponse sendNotification(Long userId, Notification notification) {
        //TODO
        return null;
    }

    public JsonResponse readNotification(Notification notification) {
        //TODO
        return null;
    }

    public JsonResponse deleteNotification(Notification notification) {
        //TODO
        return null;
    }
}
