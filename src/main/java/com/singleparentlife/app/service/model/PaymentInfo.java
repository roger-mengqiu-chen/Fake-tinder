package com.singleparentlife.app.service.model;

import lombok.Data;

@Data
public class PaymentInfo {

    private String cardNumber;

    private Integer expireData;

    private Integer ccv;

    private Long userId;

    private Long locationId;
}
