package com.garume.Garuff.ui.command;

import com.garume.Garuff.Garuffhub;
import com.garume.Garuff.command.Commands;
import com.garume.Garuff.event.GaruffEventHandler;
import com.garume.Garuff.event.EventTiming;
import com.garume.Garuff.event.events.packet.PacketSendEvent;
import net.minecraft.network.play.client.CPacketChatMessage;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CommandHandler {

    @GaruffEventHandler(timing = EventTiming.PRE)
    public void onChatSend(PacketSendEvent e) {
        if (!(e.getPacket() instanceof CPacketChatMessage)) return;
        String message = ((CPacketChatMessage) e.getPacket()).getMessage();
        if (message.isEmpty()) return;
        if (message.charAt(0) != Garuffhub.getProfile().getPrefix()) return;
        e.setCancelled(true);
        List<String> list = Arrays.asList(message.substring(1).split("\\s+"));
        List<String> args = list.size() > 1 ? list.subList(1, list.size()) : Collections.emptyList();
        Commands.execute(Garuffhub.getProfile().getMessageHandler(), list.get(0), args);
    }
}