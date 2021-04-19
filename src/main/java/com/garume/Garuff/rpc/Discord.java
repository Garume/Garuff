package com.garume.Garuff.rpc;

import com.garume.Garuff.Garuff;

import club.minnced.discord.rpc.DiscordEventHandlers;
import club.minnced.discord.rpc.DiscordRPC;
import club.minnced.discord.rpc.DiscordRichPresence;

public class Discord {

    private static String discordID = "817645833360375809";
    private static DiscordRichPresence discordRichPresence = new DiscordRichPresence();
    private static DiscordRPC discordRPC = DiscordRPC.INSTANCE;

    public static void startRPC(){
        DiscordEventHandlers eventHandlers = new DiscordEventHandlers();
        eventHandlers.disconnected = ((var1, var2) -> System.out.println("Discord RPC disconnected, var1: " + var1 + ", var2: " + var2));

        discordRPC.Discord_Initialize(discordID, eventHandlers, true, null);

        discordRichPresence.startTimestamp = System.currentTimeMillis() / 1000L;
        discordRichPresence.details = "version " + Garuff.MOD_VERSION + "!";
        discordRichPresence.largeImageKey = "garufflogo";
        discordRichPresence.largeImageText = "Garuff Client";
        discordRichPresence.smallImageKey = "garufflogo";
        discordRichPresence.smallImageText = "Small Garuff";
        discordRichPresence.state = "Developing now!! by garume";
        discordRPC.Discord_UpdatePresence(discordRichPresence);
    }

    public static void stopRPC(){
        discordRPC.Discord_Shutdown();
        discordRPC.Discord_ClearPresence();
    }
}