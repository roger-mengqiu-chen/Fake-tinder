package com.singleparentlife.app.payload.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class LocationRequest {

    @JsonProperty
    private Long locationId;
    @JsonProperty
    private Double lat;
    @JsonProperty
    private Double lon;

}
