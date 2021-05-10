package com.garume.Garuff.module.modules.player;

import com.garume.Garuff.module.Category;
import com.garume.Garuff.module.Module;
import com.garume.Garuff.module.setting.settings.NumberSetting;
import com.garume.Garuff.util.api.world.EntityUtil;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

public class Timer extends Module {
    public NumberSetting speed = new NumberSetting("speed", this, 20, 1, 300, 1);

    public Timer() {
        super ("timer", "changes player timer.", Keyboard.KEY_NONE, Category.EXPLOITS);
        this.addSettings(speed);
    }

    @SubscribeEvent
    public void onTick(final TickEvent.ClientTickEvent event) {
        EntityUtil.setTimer((float)speed.getValue());
    }

    @Override
    public void onEnable() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public void onDisable() {
        EntityUtil.resetTimer();
        MinecraftForge.EVENT_BUS.unregister(this);
    }
}