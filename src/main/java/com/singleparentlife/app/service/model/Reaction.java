package com.singleparentlife.app.service.model;

import lombok.Data;

@Data
public class Reaction {

    private Long userId;

    private Long targetId;

    private Short reactionId;
}
