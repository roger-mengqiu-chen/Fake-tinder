package com.singleparentlife.app.controller;

import com.singleparentlife.app.payload.request.LocationRequest;
import com.singleparentlife.app.payload.response.JsonResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/location")
public class LocationController {

    @PostMapping("")
    public ResponseEntity<JsonResponse> createLocation(@RequestBody LocationRequest request) {
        //TODO
        return null;
    }

    @GetMapping("/{locationId}")
    public ResponseEntity<JsonResponse> getLocation(@PathVariable Long locationId) {
        //TODO
        return null;
    }

    @PutMapping()
    public ResponseEntity<JsonResponse> updateLocation(@RequestBody LocationRequest request) {
        //TODO
        return null;
    }

    @DeleteMapping("/{locationId}")
    public ResponseEntity<JsonResponse> deleteLocation(@PathVariable Long locationId) {
        //TODO
        return null;
    }


}
