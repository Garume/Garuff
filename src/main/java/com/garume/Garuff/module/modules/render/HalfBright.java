package com.garume.Garuff.module.modules.render;

import org.lwjgl.input.Keyboard;

import com.garume.Garuff.Garuff;
import com.garume.Garuff.event.events.PlayerUpdateEvent;
import com.garume.Garuff.module.Category;
import com.garume.Garuff.module.Module;

import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.client.Minecraft;
import net.minecraft.init.MobEffects;

public class HalfBright  extends Module{

	public HalfBright() {
		super("HalfBright", "Bright",Keyboard.KEY_M, Category.RENDER);
	}
	 private float lastGamma;
	 private Minecraft mc = Minecraft.getMinecraft();
	 @Override
	 public void onEnable() {
	     Garuff.EVENT_BUS.subscribe(this);

	     lastGamma = mc.gameSettings.gammaSetting;
	 }

	 @Override
	 public void onDisable() {
	     Garuff.EVENT_BUS.unsubscribe(this);

	     mc.gameSettings.gammaSetting = this.lastGamma;
	 }

	 @EventHandler
	 private Listener<PlayerUpdateEvent> OnPlayerUpdate = new Listener<>(p_Event -> {
		 mc.gameSettings.gammaSetting = 1000;
		 mc.player.removePotionEffect(MobEffects.NIGHT_VISION);
	 });
}