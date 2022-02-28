package com.singleparentlife.app.Util;

import com.singleparentlife.app.constants.SecurityConstant;
import org.springframework.stereotype.Component;

@Component
public class LinkUtil {

    public String generateEventLink(Long eventId) {
        return SecurityConstant.SERVER + "/event/" + eventId;
    }
}
