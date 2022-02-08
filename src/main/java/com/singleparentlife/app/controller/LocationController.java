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
        String country = request.getCountry().trim().toLowerCase();
        String province = request.getProvince().trim().toLowerCase();
        String city = request.getCity().trim().toLowerCase();
        String street = request.getStreet().trim().toLowerCase();
        String postcode = request.getPostcode().trim().toLowerCase();

        if (country == null || province == null || city == null || street == null || postcode == null
        || country.isEmpty() || province.isEmpty() || city.isEmpty() || street.isEmpty() || postcode.isEmpty()) {
            return ResponseEntity.badRequest().body(
                    new JsonResponse(Status.FAIL, DataType.INVALID_INPUT, "Request missed some fields"));
        }

        Location location = new Location();
        location.setCity(city);
        location.setCountry(country);
        location.setPostcode(postcode);
        location.setStreet(street);
        location.setProvince(province);
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

        Long locationId = request.getLocationId();
        String country = request.getCountry().trim().toLowerCase();
        String province = request.getProvince().trim().toLowerCase();
        String city = request.getCity().trim().toLowerCase();
        String street = request.getStreet().trim().toLowerCase();
        String postcode = request.getPostcode().trim().toLowerCase();

        if (locationId == null || country == null || province == null || city == null || street == null || postcode == null
                || country.isEmpty() || province.isEmpty() || city.isEmpty() || street.isEmpty() || postcode.isEmpty()) {
            return ResponseEntity.badRequest().body(
                    new JsonResponse(Status.FAIL, DataType.INVALID_INPUT, "Request missed some fields"));
        }

        Location location = new Location();
        location.setLocationId(locationId);
        location.setCountry(country);
        location.setProvince(province);
        location.setCity(city);
        location.setStreet(street);
        location.setPostcode(postcode);

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
