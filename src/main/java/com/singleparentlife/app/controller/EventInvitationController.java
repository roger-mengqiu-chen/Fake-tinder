package com.singleparentlife.app.controller;

import com.singleparentlife.app.Util.AuthUtil;
import com.singleparentlife.app.constants.Status;
import com.singleparentlife.app.model.Event;
import com.singleparentlife.app.model.EventInvitation;
import com.singleparentlife.app.payload.request.EventInvitationRequest;
import com.singleparentlife.app.payload.request.NotificationRequest;
import com.singleparentlife.app.payload.response.JsonResponse;
import com.singleparentlife.app.service.EventInvitationService;
import com.singleparentlife.app.service.EventService;
import com.singleparentlife.app.service.NotificationService;
import com.singleparentlife.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://locahost:3000", maxAge = 3600)
@RestController
@RequestMapping("/eventInvitation")
public class EventInvitationController {

    @Autowired
    private EventInvitationService eventInvitationService;
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private EventService eventService;
    @Autowired
    private AuthUtil authUtil;

    @PostMapping("/send")
    public ResponseEntity<JsonResponse> sendEventInvitation(@RequestBody EventInvitation eventInvitation){
        // Json file might not contains all data field of EventInvitation
        // @RequestBody will put the matched data field into the eventInvitation instance.

        // service should return a result(JSON response) because it is responsible for business logic.
        // controller only match service and return the result.
        Long userId = eventInvitation.getTargetUserId();
        Long eventId = eventInvitation.getEventId();
        JsonResponse response = eventInvitationService.createEventInvitation(eventId, userId);
        if (response.getStatus().equals(Status.SUCCESS)) {
            Event event = (Event)eventService.getEventByEventId(eventId).getData();
            NotificationRequest notification = new NotificationRequest();
            notification.setTopic("Event");
            notification.setTitle("You are invited to an event");
            notification.setBody("You are invited to attend event " + event.getEventName());
            JsonResponse notificationResponse = notificationService.sendNotification(userId, notification);
            if (notificationResponse.getStatus().equals(Status.FAIL)) {
                return ResponseEntity.ok(notificationResponse);
            }
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping()
    public ResponseEntity<JsonResponse> getInvitations() {
        Long userId = authUtil.getCurrentUserId();
        JsonResponse response = eventInvitationService.getEventInvitation(userId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{targetUserId}")
    public ResponseEntity<JsonResponse> getInvitationsOfUser(@PathVariable Long targetUserId) {
        JsonResponse response = eventInvitationService.getEventInvitation(targetUserId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/react")
    public ResponseEntity<JsonResponse> updateInvitation(@RequestBody EventInvitationRequest request) {
        JsonResponse response = eventInvitationService.updateReactionId(request.getEventId(),
                request.getTargetUserId(), request.getReactionId());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<JsonResponse> deleteInvitation(@RequestBody EventInvitation request) {
        JsonResponse response = eventInvitationService.deleteEventInvitation(request.getEventId(),request.getTargetUserId());
        return ResponseEntity.ok(response);
    }

}
