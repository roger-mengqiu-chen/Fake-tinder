package com.singleparentlife.app.service;

import com.singleparentlife.app.constants.DataType;
import com.singleparentlife.app.constants.Status;
import com.singleparentlife.app.mapper.LocationMapper;
import com.singleparentlife.app.model.Location;
import com.singleparentlife.app.payload.response.JsonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class LocationService {
    @Autowired
    private LocationMapper locationMapper;

    public JsonResponse createLocation (Location location) {
        Location existedLocation = locationMapper.find(location);

        if (existedLocation == null) {
            locationMapper.save(location);
            log.info("New location saved: {}", location.toString());
            return new JsonResponse(Status.SUCCESS, DataType.LOCATION, location);
        }
        else {
            log.error("Location existed: {}", location.toString());
            return new JsonResponse(Status.FAIL, DataType.LOCATION_EXISTED, null);
        }
    }

    public JsonResponse getLocationById (Long locationId) {
        Location location = locationMapper.findById(locationId);

        return new JsonResponse(Status.SUCCESS, DataType.LOCATION, location);

    }

    public JsonResponse updateLocation (Location location) {
        Location existedLocation = locationMapper.findById(location.getLocationId());

        if (existedLocation == null) {
            log.error("Location not found: {}", location.getLocationId());
            return new JsonResponse(Status.FAIL, DataType.LOCATION_NOT_FOUND, null);
        }
        else {
            try {
                locationMapper.update(location);
                log.info("Location is updated: {}", location.getLocationId());
                return new JsonResponse(Status.SUCCESS, DataType.LOCATION, location);
            } catch (Exception e) {
                log.error(e.getMessage());
                return new JsonResponse(Status.FAIL, DataType.SERVER_ERROR, e.getMessage());
            }
        }
    }

    public JsonResponse deleteLocationById (Long locationId) {
        Location location = locationMapper.findById(locationId);
        if (location == null) {
            log.error("Location not found: {}", locationId);
            return new JsonResponse(Status.FAIL, DataType.LOCATION_NOT_FOUND, null);
        }
        else {
            try {
                locationMapper.delete(location);
                log.info("Location is deleted {}", locationId);
                return new JsonResponse(Status.SUCCESS, null, null);
            } catch (Exception e) {
                log.error(e.getMessage());
                return new JsonResponse(Status.FAIL, DataType.SERVER_ERROR, e.getMessage());
            }
        }
    }
}
