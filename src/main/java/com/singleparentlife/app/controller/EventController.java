package com.singleparentlife.app.controller;

import com.singleparentlife.app.Util.AuthUtil;
import com.singleparentlife.app.payload.request.EventRequest;
import com.singleparentlife.app.payload.request.EventRequestWithAddress;
import com.singleparentlife.app.payload.response.JsonResponse;
import com.singleparentlife.app.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://locahost:3000", maxAge = 3600)
@RestController
@RequestMapping("/event")
public class EventController {

    @Autowired
    private EventService eventService;
    @Autowired
    private AuthUtil authUtil;

    @PostMapping("/gps")
    public ResponseEntity<JsonResponse> createEventWithGPS(@RequestBody EventRequest request){
        Long userId = authUtil.getCurrentUserId();
        JsonResponse response = eventService.createEvent(userId, request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/address")
    public ResponseEntity<JsonResponse> createEventWithAddress(@RequestBody EventRequestWithAddress request) {
        Long userId = authUtil.getCurrentUserId();
        JsonResponse response = eventService.createEventWithAddress(userId, request);
        return  ResponseEntity.ok(response);
    }

    @GetMapping("/all")
    public ResponseEntity<JsonResponse> getAllEvent() {
        JsonResponse response = eventService.getAllEvent();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/location")
    public ResponseEntity<JsonResponse> getEventsSortedByLocation() {
        Long userId = authUtil.getCurrentUserId();
        JsonResponse response = eventService.getEventNearBy(userId);
        return ResponseEntity.ok(response);
    }

    @GetMapping()
    public ResponseEntity<JsonResponse> getAllEventCreatedByUser() {
        Long userId = authUtil.getCurrentUserId();
        JsonResponse response = eventService.getAllEventOfUser(userId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/location/{locationId}")
    public ResponseEntity<JsonResponse> getEventByLocationId(@PathVariable Long locationId) {
        JsonResponse response = eventService.getEventByLocationId(locationId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<JsonResponse> getEventByEventId(@PathVariable Long eventId) {
        JsonResponse response = eventService.getEventByEventId(eventId);
        return ResponseEntity.ok(response);
    }

    @PutMapping()
    public ResponseEntity<JsonResponse> updateEvent(@RequestBody EventRequest eventRequest) {
        JsonResponse response = eventService.updateEvent(eventRequest);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{eventId}")
    public ResponseEntity<JsonResponse> deleteEvent(@PathVariable Long eventId) {
        JsonResponse response = eventService.deleteEvent(eventId);
       return ResponseEntity.ok(response);
    }
}
