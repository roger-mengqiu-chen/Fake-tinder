package com.singleparentlife.app.payload.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ReactToProfile {
    @JsonProperty
    private Long targetId;
    @JsonProperty
    private String reaction;
}
