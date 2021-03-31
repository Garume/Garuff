package com.garume.Garuff.module.modules.player;
import org.lwjgl.input.Keyboard;

import com.garume.Garuff.Garuff;
import com.garume.Garuff.module.Category;
import com.garume.Garuff.module.Module;
import com.garume.Garuff.module.setting.settings.BooleanSetting;

import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.init.Blocks;
import net.minecraftforge.client.event.InputUpdateEvent;

	public class NoSlow extends Module {
	public BooleanSetting food = new BooleanSetting("food", this, true);
	public BooleanSetting web = new BooleanSetting("web", this, true);
	public BooleanSetting soulSand = new BooleanSetting("soulSand", this, true);
	public BooleanSetting slimeBlock = new BooleanSetting("slimeBlock", this, true);

	public NoSlow() {
		super ("NoSlow", "slow? no.", Keyboard.KEY_NONE, Category.PLAYER);
		this.addSettings(food, web, soulSand, slimeBlock);
	}

	public void onEnable() {
		Garuff.EVENT_BUS.subscribe(this);
		Blocks.DIRT.setLightOpacity(10);
	}

	public void onDisable() {
		Garuff.EVENT_BUS.unsubscribe(this);
	}

	@EventHandler
	private final Listener<InputUpdateEvent> eventListener = new Listener<>(event -> {
			if (mc.player.isHandActive() && !mc.player.isRiding() && food.isEnabled()) {
				event.getMovementInput().moveStrafe *= 5;
				event.getMovementInput().moveForward *= 5;
		}
	});
}