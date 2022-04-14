package com.singleparentlife.app.Util.comparator;

import com.singleparentlife.app.service.model.Profile;

import java.util.Comparator;

public class ProfilePreferenceComparator implements Comparator<Profile> {
    @Override
    public int compare(Profile o1, Profile o2) {
        int count1 = o1.getNumberOfMatchedPreferencesWithMe();
        int count2 = o2.getNumberOfMatchedPreferencesWithMe();
        if (count1 > count2) {
            return 1;
        }
        else if (count1 < count2) {
            return -1;
        }
        return 0;
    }

    @Override
    public Comparator<Profile> reversed() {
        return Comparator.super.reversed();
    }
}
