package com.garume.Garuff.event.events.event;

import com.garume.Garuff.event.Event;
import net.minecraft.client.entity.AbstractClientPlayer;

public class RenderEntityNameEvent extends Event {
	
	public AbstractClientPlayer Entity;
    public double X;
    public double Y;
    public double Z;
    public String Name;
    public double DistanceSq;

    public RenderEntityNameEvent(AbstractClientPlayer entityIn, double x, double y, double z, String name, double distanceSq) {
        Entity = entityIn;
        x = X;
        y = Y;
        z = Z;
        Name = name;
        DistanceSq = distanceSq;
    }

}
