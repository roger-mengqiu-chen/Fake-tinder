package com.singleparentlife.app.service;

import com.singleparentlife.app.constants.DataType;
import com.singleparentlife.app.constants.Status;
import com.singleparentlife.app.mapper.EventInvitationMapper;
import com.singleparentlife.app.mapper.EventMapper;
import com.singleparentlife.app.mapper.UserMapper;
import com.singleparentlife.app.model.Event;
import com.singleparentlife.app.model.EventInvitation;
import com.singleparentlife.app.model.User;
import com.singleparentlife.app.payload.response.JsonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class EventInvitationService {

    @Autowired
    private EventInvitationMapper eventInvitationMapper;
    @Autowired
    private EventMapper eventMapper;
    @Autowired
    private UserMapper userMapper;

    public JsonResponse  getEventInvitation(Long targetUserId){
        List<EventInvitation> eventInvitationL = eventInvitationMapper.findByTargetUserId(targetUserId);
        if (eventInvitationL.size() == 0) {
            return new JsonResponse(Status.FAIL, DataType.EVENT_INVITATION_NOT_FOUND, null);
        }
        else {
            //return the List<EventInvitation> object
            return new JsonResponse(Status.SUCCESS, DataType.LIST_OF_EVENT_INVITATION, eventInvitationL);
        }
    }

    public JsonResponse createEventInvitation(Long eventId, Long targetUserId) {
        Event event = eventMapper.getByEventId(eventId);
        if(event == null) {
            log.error("Event not found: {}", eventId);
            return new JsonResponse(Status.FAIL, DataType.EVENT_NOT_FOUND, null);
        }

        User user = userMapper.findById(targetUserId);
        if (user == null) {
            log.error("User not found: {}", targetUserId);
            return new JsonResponse(Status.FAIL, DataType.USER_NOT_FOUND, null);
        }

        EventInvitation eventInvitation = new EventInvitation();
        eventInvitation.setEventId(eventId);
        eventInvitation.setTargetUserId(targetUserId);
        eventInvitation.setReactionId((short) 0);
        try {
            eventInvitationMapper.save(eventInvitation);
            log.info("Event invitation created: event {} -> userId {}", eventId, targetUserId);
            return new JsonResponse(Status.SUCCESS,null,null);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new JsonResponse(Status.FAIL, DataType.SERVER_ERROR, null);
        }
    }

    public JsonResponse deleteEventInvitation(Long eventId, Long targetUserId) {
        EventInvitation eventInvitation = eventInvitationMapper.findByEventIdAndTargetUserId(eventId, targetUserId);
        if (eventInvitation == null) {
            return new JsonResponse(Status.FAIL, DataType.EVENT_INVITATION_NOT_FOUND, null);
        }
        else {
            eventInvitationMapper.deleteByEventInvitationId(eventInvitation.getEventInvitationId());

            return new JsonResponse(Status.SUCCESS, DataType.EVENT_INVITATION, eventInvitation);
        }
    }

    public JsonResponse updateReactionId(Long eventId, Long targetUserId, Short reactionId) {
        EventInvitation eventInvitation = eventInvitationMapper.findByEventIdAndTargetUserId(eventId, targetUserId);
        if (eventInvitation == null) {
            log.error("Event invitation not found: eventId {} -> userId {}", eventId, targetUserId);
            return new JsonResponse(Status.FAIL, DataType.EVENT_INVITATION_NOT_FOUND, null);
        }

        else {
            try {
                eventInvitationMapper.updateReactionId(eventInvitation.getEventInvitationId(), reactionId);
                eventInvitation.setReactionId(reactionId);
                log.info("Event invitation is updated: {}", eventInvitation.getEventInvitationId());
                return new JsonResponse(Status.SUCCESS, DataType.EVENT_INVITATION, eventInvitation);
            } catch (Exception e) {
                log.error(e.getMessage());
                return new JsonResponse(Status.FAIL, DataType.SERVER_ERROR, null);
            }
        }
    }
}

