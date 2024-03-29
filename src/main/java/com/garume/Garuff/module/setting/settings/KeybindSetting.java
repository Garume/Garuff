package com.garume.Garuff.module.setting.settings;

import org.lwjgl.input.Keyboard;

import com.garume.Garuff.module.Module;
import com.garume.Garuff.module.setting.Setting;
import com.lukflug.panelstudio.theme.Renderer;


/*
 * Written by @SrgantMooMoo on 11/17/20.
 */

public class KeybindSetting extends Setting implements com.lukflug.panelstudio.settings.KeybindSetting {

	public int code;

	public KeybindSetting(int code) {
		this.name = "KeyBind";
		this.code = code;
	}

	public KeybindSetting(Renderer componentRenderer, Module module) {
		// TODO Auto-generated constructor stub
	}

	public int getKeyCode() {
		return this.code;
	}

	public void setKeyCode(int code) {
		this.code = code;
	}

	@Override
	public int getKey() {
		return code;
	}

	@Override
	public String getKeyName() {
		return Keyboard.getKeyName(code);
	}

	@Override
	public void setKey(int key) {
		code=key;
	}

}
