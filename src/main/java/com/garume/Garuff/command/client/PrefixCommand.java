package com.garume.Garuff.command.client;

import com.garume.Garuff.Garuffhub;
import com.garume.Garuff.command.LogLevel;
import com.garume.Garuff.command.MessageHandler;
import com.garume.Garuff.command.annotation.CommandAlias;
import com.garume.Garuff.command.annotation.Default;

@CommandAlias("prefix")
public class PrefixCommand {

    @Default
    public void onDefault(MessageHandler out, Character prefix) {
        Garuffhub.getProfile().setPrefix(prefix);
        out.send("Set prefix to " + prefix, LogLevel.INFO);
    }
}
