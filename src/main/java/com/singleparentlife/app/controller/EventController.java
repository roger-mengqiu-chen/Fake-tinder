package com.singleparentlife.app.controller;

import com.singleparentlife.app.payload.request.EventRequest;
import com.singleparentlife.app.payload.response.JsonResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/event")
public class EventController {

    @PostMapping()
    public JsonResponse createEvent(@RequestBody EventRequest request){
        //TODO
        return null;
    }

    @GetMapping("/{eventId}")
    public JsonResponse getEvent(@PathVariable Long eventId) {
        //TODO
        return null;
    }

    @PutMapping()
    public JsonResponse updateEvent(@RequestBody EventRequest request) {
        //TODO
        return null;
    }

    @DeleteMapping("/{eventId}")
    public JsonResponse deleteEvent(@PathVariable Long eventId) {
        //TODO
        return null;
    }


}
