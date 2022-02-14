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

@CrossOrigin(origins = "http://locahost:3000", maxAge = 3600)
@RestController
@RequestMapping("/eventInvitation")
public class EventInvitationController {

    @Autowired
    EventInvitationService eventInvitationService;

    /*
    @PostMapping("/send/{userId}")
    public ResponseEntity<JsonResponse> sendInvitationToUser(@PathVariable Long userId) {
        //TODO
        return null;
    }
    */
    @PostMapping("/send")
    public ResponseEntity<JsonResponse> sendEventInvitation(@RequestBody EventInvitation eventInvitation){
        System.out.println(eventInvitation.getEventId());
        System.out.println(eventInvitation.getTargetUserId());

        JsonResponse response = eventInvitationService.createEventInvitation(eventInvitation.getEventId(),
                eventInvitation.getTargetUserId());
        if (response.getStatus().equals(Status.SUCCESS))
            return ResponseEntity.ok().body(response);
        else
            return ResponseEntity.status(401).body(response);
    }
    @GetMapping()
    public ResponseEntity<JsonResponse> getInvitations() {
        //TODO
        return null;
    }

    @GetMapping("/{invitationId}")
    public ResponseEntity<JsonResponse> getInvitationById(@PathVariable Long eventInvitationId) {
        //TODO
        return null;
    }

    @PutMapping()
    public ResponseEntity<JsonResponse> updateInvitation(@RequestBody EventInvitationRequest request) {
        //TODO
        return null;
    }

    @DeleteMapping("/{eventInvitationId}")
    public ResponseEntity<JsonResponse> deleteInvitation(@PathVariable Long eventInvitationId) {
        //TODO
        return null;
    }

    @PostMapping("/react/{eventInvitationId}")
    public ResponseEntity<JsonResponse> reactToInvitation(@PathVariable Long eventInvitationId) {
        //TODO
        return null;
    }
}
