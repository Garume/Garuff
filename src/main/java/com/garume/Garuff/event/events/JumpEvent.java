package com.garume.Garuff.event.events;

import com.garume.Garuff.event.Event;
import com.garume.Garuff.util.api.world.Location;

public class JumpEvent extends Event {

	private Location location;

	public JumpEvent(Location location) {
		this.location = location;
	}

	public Location getLocation() {
		return this.location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}
}