package com.singleparentlife.app.service.model;

import lombok.Data;

@Data
public class EventInvitation {

    private Long eventInvitationId;

    private Long eventId;

    private Long targetUserId;

    private Short reactionId;
}
