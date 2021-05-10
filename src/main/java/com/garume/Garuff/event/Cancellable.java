package com.garume.Garuff.event;

/*
 * Written by Toshimiti on March 7th, 2021.
 */

public interface Cancellable {

    boolean isCancelled();

    void setCancelled(boolean cancelled);
}
