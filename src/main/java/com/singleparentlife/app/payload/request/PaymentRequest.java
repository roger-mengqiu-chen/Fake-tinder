package com.singleparentlife.app.payload.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PaymentRequest {
    @JsonProperty
    private String cardNumber;
    @JsonProperty
    private Integer expireData;
    @JsonProperty
    private Integer CCV;
    @JsonProperty
    private Long userId;
    @JsonProperty
    private LocationRequest location;
}
