package com.garume.Garuff.module.modules.client;

import java.awt.Font;

import org.lwjgl.input.Keyboard;

import com.garume.Garuff.Garuff;
import com.garume.Garuff.module.Category;
import com.garume.Garuff.module.Module;
import com.garume.Garuff.module.setting.settings.ModeSetting;
import com.garume.Garuff.util.api.font.CustomFontRenderer;



public class ClientFont extends Module {
	public ModeSetting font = new ModeSetting("font", this, "Comic Sans Ms", "Comic Sans Ms", "Arial", "Verdana");

	public ClientFont() {
		super ("clientFont", "u have to re enable for it to change :(", Keyboard.KEY_NONE, Category.CLIENT);
		this.addSettings(font);
	}

	public void onEnable() {
		if(font.is("Comic Sans Ms")) {
			Garuff.getInstance().customFontRenderer = new CustomFontRenderer(new Font("Comic Sans MS", Font.PLAIN, 18), true, true);
		}

		if(font.is("Arial")) {
			Garuff.getInstance().customFontRenderer = new CustomFontRenderer(new Font("Arial", Font.PLAIN, 18), true, true);
		}

		if(font.is("Verdana")) {
			Garuff.getInstance().customFontRenderer = new CustomFontRenderer(new Font("Verdana", Font.PLAIN, 18), true, true);
		}
	}
}
