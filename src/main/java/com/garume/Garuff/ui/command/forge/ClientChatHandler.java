package com.garume.Garuff.ui.command.forge;


import com.garume.Garuff.event.events.command.ChatSendEvent;
import com.garume.Garuff.event.events.command.CommandCallHandler;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ClientChatHandler {

    @SubscribeEvent
    public void onChat(ClientChatEvent e) {
        ChatSendEvent event = new ChatSendEvent(e.getMessage());
        CommandCallHandler.callEvent(event);
        e.setCanceled(event.isCancelled());
    }
}