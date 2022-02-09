package com.singleparentlife.app.payload.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class EventRequest {
    @JsonProperty
    private Long eventId;
    @JsonProperty
    private String eventName;
    @JsonProperty
    private String eventTime;
    @JsonProperty
    private LocationRequest location;
    @JsonProperty
    private String eventDescription;

}
