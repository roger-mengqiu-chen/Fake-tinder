package com.singleparentlife.app.model;

import lombok.Data;

@Data
public class Location {

    private long locationId;

    private String country;

    private String province;

    private String city;

    private String street;

    private String postcode;
}
