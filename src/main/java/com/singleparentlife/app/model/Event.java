package com.singleparentlife.app.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Event {

    private long eventId;

    private String eventName;

    private LocalDateTime eventTime;

    private long locationId;

    private String eventDescription;

    private String eventLink;
}
