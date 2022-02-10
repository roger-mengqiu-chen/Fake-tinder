package com.singleparentlife.app.payload.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class LocationRequest {
    @JsonProperty
    Long locationId;
    @JsonProperty
    String country;
    @JsonProperty
    String province;
    @JsonProperty
    String city;
    @JsonProperty
    String street;
    @JsonProperty
    String postcode;
}
