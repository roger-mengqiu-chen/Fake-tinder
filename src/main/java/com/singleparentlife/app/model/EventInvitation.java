package com.singleparentlife.app.model;

import lombok.Data;

@Data
public class EventInvitation {

    private long eventInvitationId;

    private long eventId;

    private long targetUserId;

    private short reactionId;
}
