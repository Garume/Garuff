package com.garume.Garuff.command.client;

import com.garume.Garuff.command.LogLevel;
import com.garume.Garuff.command.MessageHandler;
import com.garume.Garuff.command.annotation.CommandAlias;
import com.garume.Garuff.command.annotation.Default;
import com.garume.Garuff.module.Module;
import com.mojang.realmsclient.gui.ChatFormatting;

@CommandAlias(value = "toggle", aliases = "t", description = "Toggles a module", syntax = "<module>")
public class ToggleCommand {

    @Default
    public void onDefault(MessageHandler out, Module module) {
        module.toggle();
        out.send(module.name + " " + (module.isToggled() ? ChatFormatting.GREEN + "enabled" + ChatFormatting.GRAY + "." : ChatFormatting.RED + "disabled" + ChatFormatting.GRAY + "."), LogLevel.INFO);
    }
}
