package com.garume.Garuff.command.client;

import com.garume.Garuff.command.Command;
import com.garume.Garuff.command.Commands;
import com.garume.Garuff.command.LogLevel;
import com.garume.Garuff.command.MessageHandler;
import com.garume.Garuff.command.annotation.CommandAlias;
import com.garume.Garuff.command.annotation.Default;

@CommandAlias(value = "help", description = "Shows all commands")
public class HelpCommand {

    @Default
    public void onDefault(MessageHandler out) {
        out.send("Commands: ", LogLevel.INFO);
        for (Command command : Commands.getCommands()) {
            if (command.getDescription() == null)
                out.send("  " + command.getName(), LogLevel.INFO);
            else
                out.send("  " + command.getName() + " - " + command.getDescription(), LogLevel.INFO);
        }
    }
}
