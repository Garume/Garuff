package com.garume.Garuff.event.events.packet;

import net.minecraft.network.Packet;

public class PacketSendEvent extends PacketEvent {
    public PacketSendEvent(Packet<?> packet) {
        super(packet);
    }
}