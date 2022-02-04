package com.singleparentlife.app.controller;

import com.singleparentlife.app.payload.request.EventInvitationRequest;
import com.singleparentlife.app.payload.response.JsonResponse;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/eventInvitation")
public class EventInvitationController {

    @PostMapping("/send/{userId}")
    public JsonResponse sendInvitationToUser(@PathVariable Long userId) {
        //TODO
        return null;
    }

    @GetMapping()
    public JsonResponse getInvitations() {
        //TODO
        return null;
    }

    @GetMapping("/{invitationId}")
    public JsonResponse getInvitationById(@PathVariable Long eventInvitationId) {
        //TODO
        return null;
    }

    @PutMapping()
    public JsonResponse updateInvitation(@RequestBody EventInvitationRequest request) {
        //TODO
        return null;
    }

    @DeleteMapping("/{eventInvitationId}")
    public JsonResponse deleteInvitation(@PathVariable Long eventInvitationId) {
        //TODO
        return null;
    }

    @PostMapping("/react/{eventInvitationId}")
    public JsonResponse reactToInvitation(@PathVariable Long eventInvitationId) {
        //TODO
        return null;
    }
}
