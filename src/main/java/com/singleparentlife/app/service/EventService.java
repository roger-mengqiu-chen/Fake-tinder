package com.singleparentlife.app.service;

import com.singleparentlife.app.constants.DataType;
import com.singleparentlife.app.constants.Status;
import com.singleparentlife.app.mapper.EventMapper;
import com.singleparentlife.app.model.Event;
import com.singleparentlife.app.payload.response.JsonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class EventService {
    @Autowired
    private EventMapper eventMapper;

    public JsonResponse getEventByEventId(Long eventId){
        Event event = eventMapper.getByEventId(eventId);
        if (event == null) {
            return new JsonResponse(Status.FAIL, DataType.EVENT_NOT_FOUND, "Event not found: " + eventId);
        }
        else {
            return new JsonResponse(Status.SUCCESS, DataType.EVENT, event);
        }
    }

    public JsonResponse getEventByLocationId(Long locationId){
        List<Event> eventL = eventMapper.getByLocationId(locationId);
        if (eventL.size() == 0){
            return new JsonResponse(Status.FAIL, DataType.EVENT_NOT_FOUND, null);
        }
        else {
            return new JsonResponse(Status.SUCCESS, DataType.LIST_OF_EVENT, eventL);
        }
    }

    public JsonResponse getAllEvent() {
        List<Event> eventL = eventMapper.getAll();
        if (eventL.size() == 0){
            return new JsonResponse(Status.FAIL, DataType.EVENT_NOT_FOUND, null);
        }
        else {
            return new JsonResponse(Status.SUCCESS, DataType.LIST_OF_EVENT, eventL);
        }
    }

    public JsonResponse createEvent(Event event) {
        try {
            eventMapper.save(event);
            log.info("New event created");
            return new JsonResponse(Status.SUCCESS, DataType.EVENT, event);
        }
        catch(Exception e) {
            log.error(e.getMessage());
            return new JsonResponse(Status.FAIL, DataType.SERVER_ERROR, null);
        }
    }
    public JsonResponse updateEvent(Event event) {
        Event eventRecord = eventMapper.getByEventId(event.getEventId());
        if (eventRecord == null) {
            return new JsonResponse(Status.FAIL, DataType.EVENT_NOT_FOUND, null);
        }
        else {
            try {
                eventMapper.update(event);
                //return the event object
                log.info("Event is updated {}", event.getEventId());
                return new JsonResponse(Status.SUCCESS, DataType.EVENT, event);
            }
            catch (Exception e){
                log.error(e.getMessage());
                return new JsonResponse(Status.FAIL, DataType.SERVER_ERROR, null);
            }
        }
    }
    public JsonResponse deleteEvent(Long eventId) {
        Event eventRecord = eventMapper.getByEventId(eventId);
        if (eventRecord == null) {
            return new JsonResponse(Status.FAIL, DataType.EVENT_NOT_FOUND, null);
        }
        else {
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
        try {
            List<Event> events = eventMapper.getAllByUserId(userId);
            return new JsonResponse(Status.SUCCESS, DataType.LIST_OF_EVENT, events);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new JsonResponse(Status.FAIL, DataType.SERVER_ERROR, null);
        }

    }
}
