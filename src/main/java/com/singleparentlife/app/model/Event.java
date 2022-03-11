package com.singleparentlife.app.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Event {

    private Long eventId;

    private String eventName;

    private LocalDateTime eventTime;

    private Long locationId;

    private String eventDescription;

    private String eventLink;

    private double distanceToMe;
}
