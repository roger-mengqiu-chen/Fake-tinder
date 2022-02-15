package com.singleparentlife.app.payload.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class EventInvitationRequest {
    @JsonProperty
    private Long eventInvitationId;
    @JsonProperty
    private Long eventId;
    @JsonProperty
    private Long targetUserId;
    @JsonProperty
    private Short reactionId;
}
