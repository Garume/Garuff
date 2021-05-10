package com.garume.Garuff.event.events.command;

import com.garume.Garuff.event.Cancellable;
import com.garume.Garuff.event.Timing;
import com.garume.Garuff.event.events.EventAdapter;
import com.garume.Garuff.event.events.MethodEventAdapter;


import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;

/*
 * Written by Toshimiti on March 7th, 2021.
 */

public class CommandCallHandler {

    private static final HashSet<EventMap> eventMaps = new HashSet<>();

    @SuppressWarnings("unchecked")
    public static <T extends Timing> void callEvent(T timing) {
        LinkedList<EventAdapter<T>> adapters = new LinkedList<>();
        for (EventMap map : getAllEventMap(timing.getClass())) {
            for (EventAdapter<?> adapter : map.adapters) {
                adapters.add((EventAdapter<T>) adapter);
            }
        }
        adapters.sort(Comparator.comparingInt(EventAdapter::getPriority));
        for (EventAdapter<T> adapter : adapters) {
            if (timing instanceof Cancellable && ((Cancellable) timing).isCancelled() && adapter.isIgnoreCancelled())
                continue;
            adapter.call(timing);
        }
    }


    public static void register(Object obj, EventAdapter<?> adapter) {
        EventMap map = getEventMap(adapter.getEventClass());
        if (map == null) {
            map = new EventMap(obj, adapter.getEventClass(), new ArrayList<>());
            eventMaps.add(map);
        }
        map.adapters.add(adapter);
        map.adapters.sort(Comparator.comparingInt(EventAdapter::getPriority));
    }

    public static void register(Object o) {
        for (Method method : o.getClass().getMethods()) {
            try {
                register(o, new MethodEventAdapter(o, method));
            } catch (IllegalArgumentException e) {
                // skip
            }
        }
    }

    private static EventMap getEventMap(Class<?> eventClass) {
        for (EventMap map : eventMaps) {
            if (eventClass.equals(map.getClass()))
                return map;
        }
        return null;
    }

    private static HashSet<EventMap> getAllEventMap(Class<?> eventClass) {
        HashSet<EventMap> result = new HashSet<>();
        for (EventMap map : eventMaps) {
            if (!map.eventClass.isAssignableFrom(eventClass)) continue;
            result.add(map);
        }
        return result;
    }

    private static class EventMap {
        final Object obj;
        final Class<?> eventClass;
        final ArrayList<EventAdapter<?>> adapters;

        EventMap(Object obj, Class<?> eventClass, ArrayList<EventAdapter<?>> adapters) {
            this.obj = obj;
            this.eventClass = eventClass;
            this.adapters = adapters;
        }
    }
}
