package com.garume.Garuff.event.events.packet;

import com.garume.Garuff.event.CancellableEvent;
import net.minecraft.network.Packet;

public class PacketEvent extends CancellableEvent {
    private Packet<?> packet;

    public PacketEvent(Packet<?> packet) {
        this.packet = packet;
    }

    public Packet<?> getPacket() {
        return packet;
    }

    public void setPacket(Packet<?> packet) {
        this.packet = packet;
    }
}
