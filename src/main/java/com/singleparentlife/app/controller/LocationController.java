package com.singleparentlife.app.controller;

import com.singleparentlife.app.payload.request.LocationRequest;
import com.singleparentlife.app.payload.response.JsonResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/location")
public class LocationController {

    @PostMapping("")
    public JsonResponse createLocation(@RequestBody LocationRequest request) {
        //TODO
        return null;
    }

    @GetMapping("/{locationId}")
    public JsonResponse getLocation(@PathVariable Long locationId) {
        //TODO
        return null;
    }

    @PutMapping()
    public JsonResponse updateLocation(@RequestBody LocationRequest request) {
        //TODO
        return null;
    }

    @DeleteMapping("/{locationId}")
    public JsonResponse deleteLocation(@PathVariable Long locationId) {
        //TODO
        return null;
    }


}
