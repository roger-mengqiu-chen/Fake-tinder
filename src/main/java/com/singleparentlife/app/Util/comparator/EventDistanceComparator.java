package com.singleparentlife.app.Util.comparator;

import com.singleparentlife.app.service.model.Event;

import java.util.Comparator;

public class EventDistanceComparator implements Comparator<Event> {
    @Override
    public int compare(Event o1, Event o2) {
        double distance1 = o1.getDistanceToMe();
        double distance2 = o2.getDistanceToMe();
        if (distance1 > distance2) {
            return 1;
        }
        else if (distance1 < distance2) {
            return -1;
        }
        return 0;
    }

    @Override
    public Comparator<Event> reversed() {
        return Comparator.super.reversed();
    }
}
