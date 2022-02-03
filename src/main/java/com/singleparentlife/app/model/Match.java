package com.singleparentlife.app.model;

import lombok.Data;

@Data
public class Match {

    private long userId;

    private long targetId;

    private short reactionId;
}
