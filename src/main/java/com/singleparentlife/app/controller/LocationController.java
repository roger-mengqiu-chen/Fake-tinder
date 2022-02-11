package com.singleparentlife.app.controller;

import com.singleparentlife.app.constants.DataType;
import com.singleparentlife.app.constants.Status;
import com.singleparentlife.app.model.Location;
import com.singleparentlife.app.payload.request.LocationRequest;
import com.singleparentlife.app.payload.response.JsonResponse;
import com.singleparentlife.app.service.LocationService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/location")
public class LocationController {

    @Autowired
    private LocationService locationService;

    @PostMapping("")
    public ResponseEntity<JsonResponse> createLocation(@RequestBody LocationRequest request) {
        // format request to trim the input and convert all of them to lower case
        request.formatted();

        Location location = new Location();

        BeanUtils.copyProperties(request, location);
        JsonResponse response = locationService.createLocation(location);

        return response.toResponseEntity();
    }

    @GetMapping("/{locationId}")
    public ResponseEntity<JsonResponse> getLocation(@PathVariable Long locationId) {
        if (locationId == null) {
            return ResponseEntity.badRequest().body(
                    new JsonResponse(Status.FAIL, DataType.INVALID_INPUT, "LocationId can't be null"));
        }
        JsonResponse response = locationService.getLocationById(locationId);
        return ResponseEntity.ok(response);
    }

    @PutMapping()
    public ResponseEntity<JsonResponse> updateLocation(@RequestBody LocationRequest request) {
        request.formatted();

        Location location = new Location();

        BeanUtils.copyProperties(request, location);

        JsonResponse response = locationService.updateLocation(location);

        return response.toResponseEntity();
    }

    @DeleteMapping("/{locationId}")
    public ResponseEntity<JsonResponse> deleteLocation(@PathVariable Long locationId) {
        if (locationId == null) {
            return ResponseEntity.badRequest().body(
                    new JsonResponse(Status.FAIL, DataType.INVALID_INPUT, "LocationId can't be null"));
        }
        JsonResponse response = locationService.deleteLocationById(locationId);
        return response.toResponseEntity();
    }
}
