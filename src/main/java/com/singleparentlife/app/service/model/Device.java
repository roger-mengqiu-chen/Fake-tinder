package com.singleparentlife.app.service.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Device {

    private Long deviceId;

    private Long userId;

    private String deviceToken;

    private LocalDateTime registerTime;
}
