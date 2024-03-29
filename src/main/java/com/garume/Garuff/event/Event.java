package com.garume.Garuff.event;

import com.garume.Garuff.util.api.Wrapper;

import me.zero.alpine.type.Cancellable;

public class Event extends Cancellable{

	private Era era = Era.PRE;
    private final float partialTicks;


    public Event() {
        partialTicks = Wrapper.getMinecraft().getRenderPartialTicks();
    }

    public Event(Era p_Era) {
        partialTicks = Wrapper.getMinecraft().getRenderPartialTicks();
        era = p_Era;
    }

    public Era getEra() {
        return era;
    }

    public float getPartialTicks() {
        return partialTicks;
    }

    public enum Era {
        PRE,
        PERI,
        POST
    }
}