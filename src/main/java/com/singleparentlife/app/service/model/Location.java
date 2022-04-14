package com.singleparentlife.app.service.model;

import lombok.Data;

@Data
public class Location {

    private Long locationId;

    private Double lat;

    private Double lon;

    private String country;

    private String province;

    private String city;

    private String street;

    private String postcode;

    @Override
    public String toString() {

        return String.format("{country: %s, province: %s, city: %s, street: %s, postcode: %s}",
                                country, province, city, street, postcode);
    }
}
