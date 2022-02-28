package com.singleparentlife.app.payload.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EventRequest {
    @JsonProperty
    private Long eventId;
    @JsonProperty
    private String eventName;
    @JsonProperty
    private LocalDateTime eventTime;
    @JsonProperty
    private LocationRequest location;
    @JsonProperty
    private String eventDescription;

}
