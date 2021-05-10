package com.garume.Garuff.command;

import net.minecraft.client.Minecraft;
import net.minecraft.util.text.ChatType;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;

import static net.minecraft.util.text.TextFormatting.*;

public class ChatMessageHandler implements MessageHandler {
    @Override
    public void send(String message, LogLevel level) {
        TextFormatting color;
        if (level == LogLevel.WARN) color = YELLOW;
        else if (level == LogLevel.ERROR) color = RED;
        else color = AQUA;
        Minecraft.getMinecraft().ingameGUI.addChatMessage(ChatType.CHAT, new TextComponentString(color + message));
    }
}
