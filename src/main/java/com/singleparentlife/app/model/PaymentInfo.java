package com.singleparentlife.app.model;

import lombok.Data;

@Data
public class PaymentInfo {

    private String cardNumber;

    private int expireData;

    private int ccv;

    private long userId;

    private long locationId;
}
