package com.singleparentlife.app.payload.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class MessageRequest {
    @JsonProperty
    private Long senderId;
    @JsonProperty
    private Long receiverId;
    @JsonProperty
    private String content;
}
