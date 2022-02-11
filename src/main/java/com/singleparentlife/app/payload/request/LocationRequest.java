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

    /**
     * Convert all the fields to lowercase
     */
    public void formatted() {
        this.country = this.country.trim().toLowerCase();
        this.province = this.province.trim().toLowerCase();
        this.city = this.city.trim().toLowerCase();
        this.street = this.street.trim().toLowerCase();
        this.postcode = this.postcode.trim().toLowerCase();
    }
}
