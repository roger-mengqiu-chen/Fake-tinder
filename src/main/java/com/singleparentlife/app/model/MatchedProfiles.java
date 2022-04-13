package com.singleparentlife.app.model;

import java.util.List;
import lombok.Data;

@Data
public class MatchedProfiles {
    private Profile profile;
    List<String> attachmentLinks;
}
