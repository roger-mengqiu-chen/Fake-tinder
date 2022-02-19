package com.singleparentlife.app.service;

import com.google.firebase.messaging.*;
import com.singleparentlife.app.constants.DataType;
import com.singleparentlife.app.constants.Status;
import com.singleparentlife.app.mapper.DeviceMapper;
import com.singleparentlife.app.mapper.NotificationMapper;
import com.singleparentlife.app.mapper.UserMapper;
import com.singleparentlife.app.model.AppNotification;
import com.singleparentlife.app.payload.request.NotificationRequest;
import com.singleparentlife.app.payload.response.JsonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class NotificationService {

    @Autowired
    private NotificationMapper notificationMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private DeviceMapper deviceMapper;

    public JsonResponse sendNotification(Long userId, NotificationRequest notificationRequest) {
        List<String> devices = deviceMapper.getAllDeviceTokensOfUser(userId);
        MulticastMessage multicastMessage =
                MulticastMessage.builder()
                        .addAllTokens(devices)
                        .setApnsConfig(getApnsConfig(notificationRequest.getTopic()))
                        .setAndroidConfig(getAndroidConfig(notificationRequest.getTopic()))
                        .setNotification(
                                Notification.builder()
                                        .setTitle(notificationRequest.getTitle())
                                        .setBody(notificationRequest.getBody())
                                        .build())
                .build();
        try {
            BatchResponse response = FirebaseMessaging.getInstance().sendMulticast(multicastMessage);
            log.info("{} messages has been sent successfully", response.getSuccessCount());
            AppNotification appNotification = new AppNotification();
            appNotification.setTopic(notificationRequest.getTopic());
            appNotification.setTitle(notificationRequest.getTitle());
            appNotification.setContent(notificationRequest.getBody());
            appNotification.setUserId(userId);
            appNotification.setIsRead(false);
            appNotification.setTime(LocalDateTime.now());
            notificationMapper.save(appNotification);
            log.info("Notification is saved into database: {}", appNotification.getNotificationId());
            return new JsonResponse(Status.SUCCESS, DataType.NOTIFICATION, appNotification);
        } catch (FirebaseMessagingException e) {
            log.error("Firebase exception: {}", e.getMessage());
            return new JsonResponse(Status.FAIL, DataType.SERVER_ERROR, null);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new JsonResponse(Status.FAIL, DataType.SERVER_ERROR, null);
        }
    }

    public JsonResponse readNotificationById(Long notificationId) {
        AppNotification notification = notificationMapper.getNotificationById(notificationId);

        if (notification == null) {
            return new JsonResponse(Status.FAIL, DataType.NOTIFICATION_NOT_FOUND, null);
        }
        try {
            notificationMapper.markStatusOfNotificationById(notificationId, true);
            log.info("Notification is updated: {}", notificationId);
            return new JsonResponse(Status.SUCCESS, DataType.NOTIFICATION, notification);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new JsonResponse(Status.FAIL, DataType.SERVER_ERROR, null);
        }
    }

    public JsonResponse unreadNotificationById(Long notificationId) {
        AppNotification notification = notificationMapper.getNotificationById(notificationId);

        if (notification == null) {
            return new JsonResponse(Status.FAIL, DataType.NOTIFICATION_NOT_FOUND, null);
        }
        try {
            notificationMapper.markStatusOfNotificationById(notificationId, false);
            log.info("Notification is updated: {}", notificationId);
            return new JsonResponse(Status.SUCCESS, DataType.NOTIFICATION, notification);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new JsonResponse(Status.FAIL, DataType.SERVER_ERROR, null);
        }
    }

    public JsonResponse readAllNotificationsOfUser(Long userId) {
        try {
            notificationMapper.markStatusOfAllNotification(userId, true);
            log.info("Notifications of user {} are updated", userId );
            return new JsonResponse(Status.SUCCESS, null, null);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new JsonResponse(Status.FAIL, DataType.SERVER_ERROR, null);
        }
    }

    public JsonResponse unreadAllNotificationsOfUser(Long userId) {
        try {
            notificationMapper.markStatusOfAllNotification(userId, false);
            log.info("Notifications of user {} are updated", userId );
            return new JsonResponse(Status.SUCCESS, null, null);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new JsonResponse(Status.FAIL, DataType.SERVER_ERROR, null);
        }
    }

    public JsonResponse deleteNotificationById(Long notificationId) {

        AppNotification notification = notificationMapper.getNotificationById(notificationId);
        if (notification == null) {
            return new JsonResponse(Status.FAIL, DataType.NOTIFICATION_NOT_FOUND, null);
        }
        try {
            notificationMapper.deleteNotificationById(notificationId);
            log.info("Notification is deleted: {}", notificationId);
            return new JsonResponse(Status.SUCCESS, DataType.NOTIFICATION, notification);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new JsonResponse(Status.FAIL, DataType.SERVER_ERROR, null);
        }
    }

    public JsonResponse deleteAllNotificationOfUser(Long userId) {
        try {
            notificationMapper.deleteAllNotification(userId);
            log.info("Notifications of user {} are deleted", userId);
            return new JsonResponse(Status.SUCCESS, null, null);
        } catch ( Exception e) {
            log.error(e.getMessage());
            return new JsonResponse(Status.FAIL, DataType.SERVER_ERROR, null);
        }

    }

    public JsonResponse getAllNotificationOfUser(Long userId) {
        try {
            List<AppNotification> notifications = notificationMapper.getAllNotificationOfUser(userId);
            return new JsonResponse(Status.SUCCESS, DataType.NOTIFICATION, notifications);
        } catch ( Exception e) {
            log.error(e.getMessage());
            return  new JsonResponse(Status.FAIL, DataType.SERVER_ERROR, null);
        }
    }

    private AndroidConfig getAndroidConfig(String topic) {
        return AndroidConfig.builder()
                .setTtl(Duration.ofMinutes(2).toMillis()).setCollapseKey(topic)
                .setPriority(AndroidConfig.Priority.HIGH)
                .setNotification(AndroidNotification.builder()
                        .setTag(topic).build()).build();
    }
    private ApnsConfig getApnsConfig(String topic) {
        return ApnsConfig.builder()
                .setAps(Aps.builder().setCategory(topic).setThreadId(topic).build()).build();
    }

}
