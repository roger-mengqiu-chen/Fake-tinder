package com.singleparentlife.app.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Preference {

    private Long preferenceId;

    private String content;

    public Preference(String content) {
        this.content = content;
    }

    public boolean equalsTo(Preference p) {
        return this.content.equals(p.getContent());
    }
}
