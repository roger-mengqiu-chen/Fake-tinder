package com.singleparentlife.app.payload.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AppTermRequest {
    @JsonProperty
    private Integer versionId;
    @JsonProperty
    private String context;
}
