package com.garume.Garuff.event.events;

/*
 * Written by Toshimiti on March 7th, 2021.
 */

import com.garume.Garuff.event.Timing;

public interface EventAdapter <T extends Timing>  {
    void call(T event);

    int getPriority();

    boolean isIgnoreCancelled();

    Class<T> getEventClass();
}
