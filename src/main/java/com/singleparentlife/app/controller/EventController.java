package com.singleparentlife.app.controller;

import com.singleparentlife.app.constants.Status;
import com.singleparentlife.app.model.Event;
import com.singleparentlife.app.payload.request.EventRequest;
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

    @PostMapping()
    public ResponseEntity<JsonResponse> createEvent(@RequestBody Event event){
        JsonResponse response = eventService.createEvent(event);
        if (response.getStatus().equals(Status.SUCCESS))
            return ResponseEntity.ok().body(response);
        else
            return ResponseEntity.status(401).body(response);

    }

    @GetMapping("/all")
    public ResponseEntity<JsonResponse> getAllEvent() {
        JsonResponse response = eventService.getAllEvent();
        if (response.getStatus().equals(Status.SUCCESS))
            return ResponseEntity.ok().body(response);
        else
            return ResponseEntity.status(401).body(response);
    }
    @GetMapping("/locationId/{locationId}")
    public ResponseEntity<JsonResponse> getEventByLocationId(@PathVariable Long locationId) {
        JsonResponse response = eventService.getEventByLocationId(locationId);
        if (response.getStatus().equals(Status.SUCCESS))
            return ResponseEntity.ok().body(response);
        else
            return ResponseEntity.status(401).body(response);
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<JsonResponse> getEventByEventId(@PathVariable Long eventId) {
        JsonResponse response = eventService.getEventByEventId(eventId);
        if (response.getStatus().equals(Status.SUCCESS))
            return ResponseEntity.ok().body(response);
        else
            return ResponseEntity.status(401).body(response);
    }

    @PutMapping()
    public ResponseEntity<JsonResponse> updateEvent(@RequestBody Event event) {
        JsonResponse response = eventService.updateEvent(event);
        if (response.getStatus().equals(Status.SUCCESS))
            return ResponseEntity.ok().body(response);
        else
            return ResponseEntity.status(401).body(response);
    }

    @DeleteMapping("/{eventId}")
    public ResponseEntity<JsonResponse> deleteEvent(@PathVariable Long eventId) {
        JsonResponse response = eventService.deleteEvent(eventId);
        if (response.getStatus().equals(Status.SUCCESS))
            return ResponseEntity.ok().body(response);
        else
            return ResponseEntity.status(401).body(response);
    }
}
