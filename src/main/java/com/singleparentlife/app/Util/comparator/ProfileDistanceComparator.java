package com.singleparentlife.app.Util.comparator;

import com.singleparentlife.app.model.Profile;

import java.util.Comparator;

public class ProfileDistanceComparator implements Comparator<Profile> {

    @Override
    public int compare(Profile p1, Profile p2) {
        double distance1 = p1.getDistanceToMe();
        double distance2 = p2.getDistanceToMe();
        if (distance1 > distance2) {
            return 1;
        }
        else if (distance1 < distance2) {
            return -1;
        }
        return 0;
    }

    @Override
    public Comparator<Profile> reversed() {
        return Comparator.super.reversed();
    }
}
