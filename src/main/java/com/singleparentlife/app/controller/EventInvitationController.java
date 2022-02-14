package com.singleparentlife.app.controller;

import com.singleparentlife.app.constants.Status;
import com.singleparentlife.app.model.EventInvitation;
import com.singleparentlife.app.payload.request.EventInvitationRequest;
import com.singleparentlife.app.payload.response.JsonResponse;
import com.singleparentlife.app.service.EventInvitationService;
import com.singleparentlife.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/eventInvitation")
public class EventInvitationController {

    @Autowired
    EventInvitationService eventInvitationService;

    @PostMapping("/send")
    public ResponseEntity<JsonResponse> sendEventInvitation(@RequestBody EventInvitation eventInvitation){
        // Json file might not contains all data field of EventInvitation
        // @RequestBody will put the matched data field into the eventInvitation instance.

        // service should return a result(JSON response) because it is responsible for business logic.
        // controller only match service and return the result.
        JsonResponse response = eventInvitationService.createEventInvitation(eventInvitation.getEventId(),
                eventInvitation.getTargetUserId());
        if (response.getStatus().equals(Status.SUCCESS))
            return ResponseEntity.ok().body(response);
        else
            return ResponseEntity.status(401).body(response);
    }
    @GetMapping("/get/{targetUserId}")
    public ResponseEntity<JsonResponse> getInvitations(@PathVariable Long targetUserId) {
        //LIST
        JsonResponse response = eventInvitationService.getEventInvitation(targetUserId);
        if (response.getStatus().equals(Status.SUCCESS))
            return ResponseEntity.ok().body(response);
        else
            return ResponseEntity.status(401).body(response);
    }
    @PutMapping("/react")
    public ResponseEntity<JsonResponse> updateInvitation(@RequestBody EventInvitationRequest request) {
        JsonResponse response = eventInvitationService.updateReactionId(request.getEventId(),
                request.getTargetUserId(), request.getReactionId());
        if (response.getStatus().equals(Status.SUCCESS))
            return ResponseEntity.ok().body(response);
        else
            return ResponseEntity.status(401).body(response);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<JsonResponse> deleteInvitation(@RequestBody EventInvitation request) {
        JsonResponse response = eventInvitationService.deleteEventInviataion(request.getEventId(),request.getTargetUserId());
        if (response.getStatus().equals(Status.SUCCESS))
            return ResponseEntity.ok().body(response);
        else
            return ResponseEntity.status(401).body(response);
    }

    /*
    @PostMapping("/send/{userId}")
    public ResponseEntity<JsonResponse> sendInvitationToUser(@PathVariable Long userId) {
        //TODO
        return null;
    }
    @GetMapping("/{invitationId}")
    public ResponseEntity<JsonResponse> getInvitationById(@PathVariable Long eventInvitationId) {
        //TODO
        return null;
    }
    @PostMapping("/react/{eventInvitationId}")
    public ResponseEntity<JsonResponse> reactToInvitation(@PathVariable Long eventInvitationId) {
        //TODO
        return null;
    }
    */
}
