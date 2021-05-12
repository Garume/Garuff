package com.garume.Garuff.module.modules.render;

import com.garume.Garuff.Garuff;
import com.garume.Garuff.event.events.event.TransformSideFirstPersonEvent;
import com.garume.Garuff.module.Category;
import com.garume.Garuff.module.Module;
import com.garume.Garuff.module.setting.settings.BooleanSetting;
import com.garume.Garuff.module.setting.settings.NumberSetting;
import org.lwjgl.input.Keyboard;

import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.EnumHandSide;
import net.minecraftforge.common.MinecraftForge;

public class ViewModel extends Module {
    public BooleanSetting cancelEating = new BooleanSetting("noEat", this, false);
    public NumberSetting LeftX = new NumberSetting("LeftX", this, 0, -2, 2, 0.1);
    public NumberSetting LeftY = new NumberSetting("LeftY", this, 0, -2, 2, 0.1);
    public NumberSetting LeftZ = new NumberSetting("LeftZ", this, 0, -2, 2, 0.1);
    public NumberSetting RightX = new NumberSetting("RightX", this, 0, -2, 2, 0.1);
    public NumberSetting RightY = new NumberSetting("RightY", this, 0, -2, 2, 0.1);
    public NumberSetting RightZ = new NumberSetting("RightZ", this, 0, -2, 2, 0.1);

    public ViewModel() {
        super("viewModel", "allows u to change how ur model look in 1st person.", Keyboard.KEY_NONE, Category.RENDER);
        this.addSettings(LeftX, LeftY, LeftZ, RightX, RightY, RightZ);
    }

    @EventHandler
    private final Listener<TransformSideFirstPersonEvent> listener = new Listener<>(event -> {
        if (event.getEnumHandSide() == EnumHandSide.RIGHT) {
            GlStateManager.translate(RightX.getValue(), RightY.getValue(), RightZ.getValue());
        } else if (event.getEnumHandSide() == EnumHandSide.LEFT) {
            GlStateManager.translate(LeftX.getValue(), LeftY.getValue(), LeftZ.getValue());
        }
    });

    public void onEnable() {
        Garuff.EVENT_BUS.subscribe(this);
        MinecraftForge.EVENT_BUS.register(this);
    }

    public void onDisable() {
        Garuff.EVENT_BUS.unsubscribe(this);
        MinecraftForge.EVENT_BUS.unregister(this);
    }
}