package com.singleparentlife.app.service;

import com.singleparentlife.app.Util.LinkUtil;
import com.singleparentlife.app.Util.LocationUtil;
import com.singleparentlife.app.Util.comparator.EventDistanceComparator;
import com.singleparentlife.app.constants.DataType;
import com.singleparentlife.app.constants.Status;
import com.singleparentlife.app.mapper.EventMapper;
import com.singleparentlife.app.mapper.LocationMapper;
import com.singleparentlife.app.mapper.ProfileMapper;
import com.singleparentlife.app.model.Event;
import com.singleparentlife.app.model.Location;
import com.singleparentlife.app.model.Profile;
import com.singleparentlife.app.payload.request.AddressRequest;
import com.singleparentlife.app.payload.request.EventRequest;
import com.singleparentlife.app.payload.request.EventRequestWithAddress;
import com.singleparentlife.app.payload.request.LocationRequest;
import com.singleparentlife.app.payload.response.JsonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
@Slf4j
public class EventService {
    @Autowired
    private EventMapper eventMapper;
    @Autowired
    private LocationMapper locationMapper;
    @Autowired
    private ProfileMapper profileMapper;
    @Autowired
    private LocationUtil locationUtil;
    @Autowired
    private LinkUtil linkUtil;

    public JsonResponse getEventByEventId(Long eventId){
        // Receive the event record by the event ID and return the result.
        Event event = eventMapper.getByEventId(eventId);
        if (event == null) {
            return new JsonResponse(Status.FAIL, DataType.EVENT_NOT_FOUND, "Event not found: " + eventId);
        }
        else {
            return new JsonResponse(Status.SUCCESS, DataType.EVENT, event);
        }
    }

    public JsonResponse getEventByLocationId(Long locationId){
        // Receive event records (List) by the location ID and return the result.
        List<Event> eventL = eventMapper.getByLocationId(locationId);
        if (eventL.size() == 0){
            return new JsonResponse(Status.FAIL, DataType.EVENT_NOT_FOUND, null);
        }
        else {
            return new JsonResponse(Status.SUCCESS, DataType.LIST_OF_EVENT, eventL);
        }
    }

    public JsonResponse getAllEvent() {
        // Receive all events in database. If there is no record in the event table, return Status.FAIL.
        List<Event> eventL = eventMapper.getAll();
        if (eventL.size() == 0){
            return new JsonResponse(Status.FAIL, DataType.EVENT_NOT_FOUND, null);
        }
        else {
            return new JsonResponse(Status.SUCCESS, DataType.LIST_OF_EVENT, eventL);
        }
    }

    public JsonResponse createEvent(Long userId, EventRequest request) {

        // Get the LocationRequest from EventRequest.
        LocationRequest locationRequest = request.getLocation();
        Location location = null;

        // Verify the LocationRequest information and transfer to Location object
        if (locationRequest != null) {
            location = locationUtil.GPSToLocation(locationRequest.getLat(), locationRequest.getLon());
            if (location == null) {
                return new JsonResponse(Status.FAIL, DataType.LOCATION_NOT_FOUND, "There's no such location");
            }
            Location existedLocation = locationMapper.find(location);

            if (existedLocation == null) {
                locationMapper.save(location);
            } else {
                location.setLocationId(existedLocation.getLocationId());
            }
        }

        // Get the event information
        Event event = new Event();
        event.setEventName(request.getEventName());
        event.setEventDescription(request.getEventDescription());
        event.setEventTime(request.getEventTime());

        // Save event record
        try {
            saveEventAtLocation(userId, event, location);
            return new JsonResponse(Status.SUCCESS, DataType.EVENT, event);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new JsonResponse(Status.FAIL, DataType.SERVER_ERROR, null);
        }
    }

    public JsonResponse createEventWithAddress(Long userId, EventRequestWithAddress request) {
        // Get the AddressRequest from EventRequest.
        AddressRequest addressRequest = request.getLocation();
        Location location = null;

        // Verify the LocationRequest information and transfer to Location object
        if (addressRequest != null) {
            location = locationUtil.AddressToGPS(
                    addressRequest.getStreet(),
                    addressRequest.getCity(),
                    addressRequest.getProvince(),
                    addressRequest.getCountry());
            if (location == null) {
                return new JsonResponse(Status.FAIL, DataType.LOCATION_NOT_FOUND, "There's no such location");
            }
            Location existedLocation = locationMapper.find(location);

            if (existedLocation == null) {
                locationMapper.save(location);
            } else {
                location.setLocationId(existedLocation.getLocationId());
            }
        }

        // Get the event information
        Event event = new Event();
        event.setEventName(request.getEventName());
        event.setEventDescription(request.getEventDescription());
        event.setEventTime(request.getEventTime());

        // Save event record
        try {
            saveEventAtLocation(userId, event, location);
            return new JsonResponse(Status.SUCCESS, DataType.EVENT, event);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new JsonResponse(Status.FAIL, DataType.SERVER_ERROR, null);
        }
    }

    public JsonResponse updateEvent(EventRequest eventRequest) {
        // Receive event information by event ID
        Event eventRecord = eventMapper.getByEventId(eventRequest.getEventId());
        if (eventRecord == null) {
            return new JsonResponse(Status.FAIL, DataType.EVENT_NOT_FOUND, null);
        }

        // Get the LocationRequest from EventRequest.
        LocationRequest locationRequest = eventRequest.getLocation();
        Location location = null;

        // Verify the LocationRequest information and transfer to Location object
        if (locationRequest != null) {
            location = locationUtil.GPSToLocation(locationRequest.getLat(), locationRequest.getLon());
            if (location == null) {
                return new JsonResponse(Status.FAIL, DataType.LOCATION_NOT_FOUND, "There's no such location");
            }
            Location existedLocation = locationMapper.find(location);

            if (existedLocation == null) {
                locationMapper.save(location);
            } else {
                location.setLocationId(existedLocation.getLocationId());
            }
        }
        if (location != null) {
            eventRecord.setLocationId(location.getLocationId());
        }

        // Get the event information
        eventRecord.setEventTime(eventRequest.getEventTime());
        eventRecord.setEventName(eventRequest.getEventName());
        eventRecord.setEventDescription(eventRequest.getEventDescription());

        // Verify the LocationRequest information and transfer to Location object
        try {
            eventMapper.update(eventRecord);
            //return the event object
            log.info("Event is updated {}", eventRecord.getEventId());
            return new JsonResponse(Status.SUCCESS, DataType.EVENT, eventRecord);
        }
        catch (Exception e){
            log.error(e.getMessage());
            return new JsonResponse(Status.FAIL, DataType.SERVER_ERROR, null);
        }

    }

    public JsonResponse deleteEvent(Long eventId) {
        // Verify the event ID
        Event eventRecord = eventMapper.getByEventId(eventId);
        if (eventRecord == null) {
            return new JsonResponse(Status.FAIL, DataType.EVENT_NOT_FOUND, null);
        }
        else {
            // If the event ID is valid, delete the event record
            try {
                eventMapper.delete(eventId);
                //return the eventInvitation object
                log.info("Event is deleted {}", eventId);
                return new JsonResponse(Status.SUCCESS, DataType.EVENT, eventRecord);
            }
            catch (Exception e){
                log.error(e.getMessage());
                return new JsonResponse(Status.FAIL, DataType.SERVER_ERROR, null);
            }
        }
    }

    public JsonResponse getAllEventOfUser(Long userId) {
        // Get all event record from database
        try {
            List<Event> events = eventMapper.getAllByUserId(userId);
            return new JsonResponse(Status.SUCCESS, DataType.LIST_OF_EVENT, events);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new JsonResponse(Status.FAIL, DataType.SERVER_ERROR, null);
        }
    }

    public JsonResponse getEventNearBy(Long userId) {
        Profile profile = profileMapper.findByUserId(userId);
        if(profile == null) {
            return new JsonResponse(Status.FAIL, DataType.PROFILE_NOT_FOUND, null);
        }

        Location userLocation = locationMapper.findById(profile.getLocationId());
        List<Event> events = eventMapper.getAll();
        List<Event> sortedEvent = new ArrayList<>();
        List<Double> distances = new ArrayList<>();
        for (Event e : events) {
            Location l = locationMapper.findById(e.getLocationId());
            e.setDistanceToMe(Double.MAX_VALUE);
            if (l != null) {
                double distance = locationUtil.distanceBetweenLocations(userLocation, l);
                e.setDistanceToMe(distance);
            }
            sortedEvent.add(e);
        }
        sortedEvent.sort(new EventDistanceComparator());

        return new JsonResponse(Status.SUCCESS, DataType.LIST_OF_EVENT, sortedEvent);
    }

    private void saveEventAtLocation (Long userId, Event event, Location location) {
        // Import address object to the event object
        if (location != null) {
            event.setLocationId(location.getLocationId());
        }

        // Save the event
        eventMapper.save(event);
        event.setEventLink(linkUtil.generateEventLink(event.getEventId()));
        eventMapper.update(event);
        log.info("New event created");
        eventMapper.saveUserEvent(userId, event.getEventId());

    }
}
