package com.singleparentlife.app.payload.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AddressRequest {

    @JsonProperty
    private String street;
    @JsonProperty
    private String city;
    @JsonProperty
    private String province;
    @JsonProperty
    private String country;
}
