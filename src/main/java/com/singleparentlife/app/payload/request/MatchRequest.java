package com.singleparentlife.app.payload.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class MatchRequest {
    @JsonProperty
    Long targetId;
    @JsonProperty
    String reaction;
}
