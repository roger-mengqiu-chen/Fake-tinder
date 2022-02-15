package com.singleparentlife.app.controller;

import com.singleparentlife.app.payload.request.EventRequest;
import com.singleparentlife.app.payload.response.JsonResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://locahost:3000", maxAge = 3600)
@RestController
@RequestMapping("/event")
public class EventController {

    @PostMapping()
    public ResponseEntity<JsonResponse> createEvent(@RequestBody EventRequest request){
        //TODO
        return null;
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<JsonResponse> getEvent(@PathVariable Long eventId) {
        //TODO
        return null;
    }

    @PutMapping()
    public ResponseEntity<JsonResponse> updateEvent(@RequestBody EventRequest request) {
        //TODO
        return null;
    }

    @DeleteMapping("/{eventId}")
    public ResponseEntity<JsonResponse> deleteEvent(@PathVariable Long eventId) {
        //TODO
        return null;
    }


}
