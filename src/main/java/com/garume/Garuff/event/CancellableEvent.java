package com.garume.Garuff.event;

/*
 * Written by Toshimiti on March 7th, 2021.
 */

public class CancellableEvent extends BaseEvent implements Cancellable {

    private boolean isCancelled;

    public CancellableEvent() {
        super(EventTiming.PRE);
    }

    public CancellableEvent(EventTiming timing) {
        super(timing);
    }

    @Override
    public boolean isCancelled() {
        return isCancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.isCancelled = cancelled;
    }
}
