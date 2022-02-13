package com.singleparentlife.app.service;

import com.singleparentlife.app.constants.DataType;
import com.singleparentlife.app.constants.Status;
import com.singleparentlife.app.mapper.EventInvitationMapper;
import com.singleparentlife.app.mapper.UserMapper;
import com.singleparentlife.app.model.EventInvitation;
import com.singleparentlife.app.payload.response.JsonResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.jdbc.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class EventInvitationService {
    @Autowired
    private EventInvitationMapper eventInvitationMapper;
    public JsonResponse  getEventInvitation(long targetUserId){
        List<EventInvitation> eventInvitationL = eventInvitationMapper.findByTargetUserId(targetUserId);
        if (eventInvitationL == null) {
            return new JsonResponse(Status.FAIL, DataType.STATUS_MESSAGE, "Cannot find event Invitation");
        } else
        {
            //return the List<EventInvitation> object
            return new JsonResponse(Status.SUCCESS, DataType.LIST_OF_EVENT_INVITATION, eventInvitationL);
        }
    }
    public JsonResponse createEventInvitation(long eventId, long targetUserId)
    {
        EventInvitation eventInvitation = new EventInvitation();
        eventInvitation.setEventId(eventId);
        eventInvitation.setTargetUserId(targetUserId);
        eventInvitation.setReactionId((short) 0);
        eventInvitationMapper.save(eventInvitation);
        return new JsonResponse(Status.SUCCESS,null,null);
    }
    public JsonResponse deleteEventInviataion(long eventId, long targetUserId) {
        EventInvitation eventInvitation = eventInvitationMapper.findByEventIdAndTargetUserId(eventId, targetUserId);
        if (eventInvitation == null) {
            return new JsonResponse(Status.FAIL, DataType.STATUS_MESSAGE, "Cannot find event Invitation");
        } else
        {
            eventInvitationMapper.deleteByEventInvitationId(eventInvitation.getEventInvitationId());
            //return the eventInvitation object
            return new JsonResponse(Status.SUCCESS, DataType.EVENT_INVITATION, eventInvitation);
        }
    }
    public JsonResponse updateReactionId(long eventId, long targetUserId, short reactionId)
    {
        EventInvitation eventInvitation = eventInvitationMapper.findByEventIdAndTargetUserId(eventId, targetUserId);
        if (eventInvitation == null) {
            return new JsonResponse(Status.FAIL, DataType.STATUS_MESSAGE, "Cannot find event Invitation");
        } else
        {
            eventInvitationMapper.updateReactionId(eventInvitation.getEventInvitationId(), reactionId);
            //return the eventInvitation object
            eventInvitation.setReactionId(reactionId);
            return new JsonResponse(Status.SUCCESS, DataType.EVENT_INVITATION, eventInvitation);
        }
    }
}

