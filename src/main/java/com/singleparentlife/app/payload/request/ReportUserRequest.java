package com.singleparentlife.app.payload.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ReportUserRequest {
    @JsonProperty
    private Long userId;
    @JsonProperty
    private Long reporterId;
    @JsonProperty
    private String reason;

}
