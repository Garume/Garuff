 package com.garume.Garuff.module.modules.render;

import org.lwjgl.input.Keyboard;

import com.garume.Garuff.module.Category;
import com.garume.Garuff.module.Module;

import net.minecraft.init.MobEffects;

/*
 * Written by @SrgantMooMoo on 11/17/20.
 * Rewritten by @SrgantMooMoo on 1/4/21.
 */

public class FullBright extends Module {

	public FullBright() {
		super ("fullBright", "makes everything fully bright.", Keyboard.KEY_NONE, Category.RENDER);
	}
	private float lastGamma;

	@Override
	public void onEnable() {
	    lastGamma = mc.gameSettings.gammaSetting;
	}

	@Override
	public void onDisable() {

	    mc.gameSettings.gammaSetting = this.lastGamma;
	}
	@Override
	public void onUpdate() {
		mc.gameSettings.gammaSetting = 666f;
		mc.player.removePotionEffect(MobEffects.NIGHT_VISION);
	}
}