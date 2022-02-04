package com.singleparentlife.app.payload.request;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EventRequest {

    private Long eventId;

    private String eventName;

    private String eventTime;

    private LocationRequest location;

    private String eventDescription;

}
