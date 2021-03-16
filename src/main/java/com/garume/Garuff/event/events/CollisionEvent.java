package com.garume.Garuff.event.events;

import com.garume.Garuff.event.Event;

import net.minecraft.entity.Entity;

public final class CollisionEvent extends Event {
    private final Entity entity;

    public CollisionEvent(Entity entity)
    {
        this.entity = entity;
    }

    public Entity getEntity()
    {
        return entity;
    }
}