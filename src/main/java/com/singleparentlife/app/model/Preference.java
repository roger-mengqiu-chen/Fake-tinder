package com.singleparentlife.app.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Preference {

    private long preferenceId;

    private String content;

    public Preference(String content) {
        this.content = content;
    }
}
