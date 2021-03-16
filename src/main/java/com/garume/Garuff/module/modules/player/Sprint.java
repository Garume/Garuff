package com.garume.Garuff.module.modules.player;

import org.lwjgl.input.Keyboard;

import com.garume.Garuff.module.Category;
import com.garume.Garuff.module.Module;

import net.minecraft.client.Minecraft;

public class Sprint extends Module{
	private Minecraft mc = Minecraft.getMinecraft();
	public boolean on;

	public Sprint() {
		super("Sprint", "auto run", Keyboard.KEY_B , Category.PLAYER);
	}

	@Override
    public void onUpdate() {
        try {
            if (!mc.player.collidedHorizontally && mc.player.moveForward > 0)
                mc.player.setSprinting(true);
            else
                mc.player.setSprinting(false);
        } catch (Exception ignored) {}
    }
}