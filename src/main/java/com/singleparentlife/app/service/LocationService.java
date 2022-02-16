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

    /**
     * Create a location
     * If the location existed, find out the location and return it
     * @param location location
     * @return location in database
     */
    public JsonResponse createLocation (Location location) {

        Location existedLocation = locationMapper.find(location);

        if (existedLocation == null) {
            locationMapper.save(location);
            log.info("New location saved: {}", location.toString());
            return new JsonResponse(Status.SUCCESS, DataType.LOCATION, location);
        }
        else {
            return new JsonResponse(Status.SUCCESS, DataType.LOCATION, existedLocation);
        }
    }

    /**
     *
     * @param locationId locationId
     * @return the location in database
     */
    public JsonResponse getLocationById (long locationId) {
        Location location = locationMapper.findById(locationId);

        return new JsonResponse(Status.SUCCESS, DataType.LOCATION, location);
    }

    /**
     * Detailed search for location
     * This search matches all the attributes of this location
     * Not recommended for searching location due to bad performance
     * @param location location
     * @return location in database if found.
     */
    public JsonResponse findLocation (Location location) {
        Location existedlocation = locationMapper.find(location);
        if (existedlocation != null) {
            return new JsonResponse(Status.SUCCESS, DataType.LOCATION, existedlocation);
        }
        return new JsonResponse(Status.FAIL, DataType.LOCATION_NOT_FOUND, location);
    }

    /**
     * Update a location.
     * @param location location
     * @return new location
     */
    public JsonResponse updateLocation (Location location) {

        Location existedLocation = locationMapper.findById(location.getLocationId());

        if (existedLocation == null) {
            log.error("Location not found: {}", location.getLocationId());
            return new JsonResponse(Status.FAIL, DataType.LOCATION_NOT_FOUND, location);
        }
        else {
            try {
                locationMapper.update(location);
                log.info("Location is updated: {}", location.getLocationId());
                return new JsonResponse(Status.SUCCESS, DataType.LOCATION, location);
            } catch (Exception e) {
                log.error(e.getMessage());
                return new JsonResponse(Status.FAIL, DataType.SERVER_ERROR, null);
            }
        }
    }

    /**
     * Delete a location
     * @param locationId locationId
     * @return deleted location
     */
    public JsonResponse deleteLocationById (long locationId) {
        Location location = locationMapper.findById(locationId);
        if (location == null) {
            log.error("Location not found: {}", locationId);
            return new JsonResponse(Status.FAIL, DataType.LOCATION_NOT_FOUND, locationId);
        }
        else {
            try {
                locationMapper.delete(location);
                log.info("Location is deleted {}", locationId);
                return new JsonResponse(Status.SUCCESS, DataType.LOCATION, location);
            } catch (Exception e) {
                log.error(e.getMessage());
                return new JsonResponse(Status.FAIL, DataType.SERVER_ERROR, null);
            }
        }
    }

}
