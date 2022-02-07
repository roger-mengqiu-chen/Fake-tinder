package com.singleparentlife.app.payload.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class PreferenceRequest {

    @JsonProperty
    private List<String> tagNames;

}
