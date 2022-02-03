package com.singleparentlife.app.model;

import lombok.Data;

@Data
public class Reaction {

    private long userId;

    private long targetId;

    private short reactionId;
}
