package com.singleparentlife.app.controller;

import com.singleparentlife.app.constants.DataType;
import com.singleparentlife.app.constants.Status;
import com.singleparentlife.app.model.Location;
import com.singleparentlife.app.payload.request.LocationRequest;
import com.singleparentlife.app.payload.response.JsonResponse;
import com.singleparentlife.app.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/location")
public class LocationController {

    @Autowired
    private LocationService locationService;

    @PostMapping("")
    public ResponseEntity<JsonResponse> createLocation(@RequestBody LocationRequest request) {

        Location location = new Location();
        location.setCity(request.getCity().toLowerCase());
        location.setCountry(request.getCountry().toLowerCase());
        location.setPostcode(request.getPostcode().toLowerCase());
        location.setStreet(request.getStreet().toLowerCase());
        location.setProvince(request.getProvince().toLowerCase());
        JsonResponse response = locationService.createLocation(location);

        return response.toResponseEntity();
    }

    @GetMapping("/{locationId}")
    public ResponseEntity<JsonResponse> getLocation(@PathVariable Long locationId) {
        JsonResponse response = locationService.getLocationById(locationId);
        return ResponseEntity.ok(response);
    }

    @PutMapping()
    public ResponseEntity<JsonResponse> updateLocation(@RequestBody LocationRequest request) {
        Location location = new Location();
        location.setLocationId(request.getLocationId());
        location.setCountry(request.getCountry());
        location.setProvince(request.getProvince());
        location.setCity(request.getCity());
        location.setStreet(request.getStreet());
        location.setPostcode(request.getPostcode());

        JsonResponse response = locationService.updateLocation(location);

        return response.toResponseEntity();
    }

    @DeleteMapping("/{locationId}")
    public ResponseEntity<JsonResponse> deleteLocation(@PathVariable Long locationId) {

        JsonResponse response = locationService.deleteLocationById(locationId);
        return response.toResponseEntity();
    }
}
