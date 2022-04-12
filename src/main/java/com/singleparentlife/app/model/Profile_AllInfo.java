package com.singleparentlife.app.model;

import lombok.Data;
import java.util.List;

@Data
public class Profile_AllInfo {
    private List<Preference> preferences;
    private Location location;
    List<String> attachmentLinks;
}
