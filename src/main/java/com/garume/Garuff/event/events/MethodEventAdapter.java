package com.garume.Garuff.event.events;


import com.garume.Garuff.event.EventTiming;
import com.garume.Garuff.event.GaruffEventHandler;
import com.garume.Garuff.event.Timing;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

public class MethodEventAdapter implements EventAdapter<Timing> {

    private final Object obj;
    private final Method method;
    private final Class<Timing> eventClass;
    private final List<EventTiming> timings;
    private final int priority;
    private final boolean ignoreCancelled;

    @SuppressWarnings("unchecked")
    public MethodEventAdapter(Object obj, Method method) {
        this.obj = obj;
        this.method = method;
        Class<?>[] parameters = method.getParameterTypes();
        if (parameters.length != 1)
            throw new IllegalArgumentException("Only 1 parameter type can exist");
        try {
            eventClass = (Class<Timing>) parameters[0];
        } catch (ClassCastException e) {
            throw new IllegalArgumentException("Parameter type must implement Event");
        }
        GaruffEventHandler handler = method.getAnnotation(GaruffEventHandler.class);
        if (handler == null)
            throw new IllegalArgumentException("@EventHandler is missing");
        this.timings = Arrays.asList(handler.timing());
        this.priority = handler.priority();
        this.ignoreCancelled = handler.ignoreCancelled();
    }

    @Override
    public void call(Timing event) {
        try {
            if (timings.contains(event.getTiming()))
                method.invoke(obj, event);
        } catch (InvocationTargetException e) {
            e.getTargetException().printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getPriority() {
        return priority;
    }

    @Override
    public boolean isIgnoreCancelled() {
        return ignoreCancelled;
    }

    @Override
    public Class<Timing> getEventClass() {
        return eventClass;
    }
}