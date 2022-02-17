package com.singleparentlife.app.controller;

import com.singleparentlife.app.Util.LocationUtil;
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
    @Autowired
    private LocationUtil locationUtil;

    @PostMapping("")
    public ResponseEntity<JsonResponse> createLocation(@RequestBody LocationRequest request) {
        // format request to trim the input and convert all of them to lower case
        Double lat = request.getLat();
        Double lon = request.getLon();

        if (lat == null || lon == null) {
            return ResponseEntity.badRequest().body(new JsonResponse(Status.FAIL, DataType.INVALID_INPUT, "Lat and Lon can't be empty"));
        }
        Location location = locationUtil.GPSToLocation(lat, lon);

        if (location == null) {
            return ResponseEntity.badRequest().body(new JsonResponse(Status.FAIL, DataType.INVALID_INPUT, "Not address found for coordinates"));
        }
        JsonResponse response = locationService.createLocation(location);

        return ResponseEntity.ok(response);
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
        Double lat = request.getLat();
        Double lon = request.getLon();

        if (lat == null || lon == null) {
            return ResponseEntity.badRequest().body(new JsonResponse(Status.FAIL, DataType.INVALID_INPUT, "Lat and Lon can't be empty"));
        }

        Location location = locationUtil.GPSToLocation(lat, lon);
        if (location == null) {
            return ResponseEntity.badRequest().body(new JsonResponse(Status.FAIL, DataType.INVALID_INPUT, "Not address found for coordinates"));
        }
        location.setLocationId(request.getLocationId());
        JsonResponse response = locationService.updateLocation(location);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{locationId}")
    public ResponseEntity<JsonResponse> deleteLocation(@PathVariable Long locationId) {
        if (locationId == null) {
            return ResponseEntity.badRequest().body(
                    new JsonResponse(Status.FAIL, DataType.INVALID_INPUT, "LocationId can't be null"));
        }
        JsonResponse response = locationService.deleteLocationById(locationId);
        return ResponseEntity.ok(response);
    }
}
