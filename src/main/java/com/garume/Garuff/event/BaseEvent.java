package com.garume.Garuff.event;

/*
 * Written by Toshimiti on March 7th, 2021.
 */

public class BaseEvent implements Timing {

    private final EventTiming timing;

    public BaseEvent(EventTiming timing) {
        this.timing = timing;
    }

    @Override
    public EventTiming getTiming() {
        return timing;
    }
}
