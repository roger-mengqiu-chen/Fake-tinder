package com.singleparentlife.app.service;

import com.singleparentlife.app.constants.Status;
import com.singleparentlife.app.mapper.EventInvitationMapper;
import com.singleparentlife.app.mapper.UserMapper;
import com.singleparentlife.app.model.EventInvitation;
import com.singleparentlife.app.payload.response.JsonResponse;
import org.apache.ibatis.jdbc.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EventInvitationService {
    @Autowired
    private EventInvitationMapper eventInvitationMapper;
    List<String> getEventId(long targetUserId){
        //TODO
        List<EventInvitation> eventInvitationL = eventInvitationMapper.findByTargetUserId(targetUserId);
        List<String> eventIdL= new ArrayList();
        for (int i=0;i<eventInvitationL.size();i++)
            eventIdL.add(String.valueOf(eventInvitationL.get(i).getEventId()));
        return eventIdL;
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
}
