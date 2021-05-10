package com.garume.Garuff.event;

import com.garume.Garuff.event.events.command.ChatSendEvent;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/*
 * Written by Toshimiti on March 7th, 2021.
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface GaruffEventHandler {

    int priority() default 0;

    boolean ignoreCancelled() default true;

    EventTiming[] timing();

}