package com.garume.Garuff.ui;
import org.lwjgl.input.Keyboard;

import com.garume.Garuff.Garuff;
import com.garume.Garuff.module.Category;
import com.garume.Garuff.module.Module;
import com.garume.Garuff.module.setting.settings.BooleanSetting;


public class HudEditor extends Module {
	public BooleanSetting exitToClickGui = new BooleanSetting("exitToClickGui", this, true);

	public HudEditor() {
		super("hudEditor", "descrp", Keyboard.KEY_NONE, Category.HUD);
		this.addSettings(exitToClickGui);
	}

	public void onEnable() {
		Garuff.getInstance().clickGui.enterHUDEditor();
	}

	public void onUpdate() {

		if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
			if(exitToClickGui.isEnabled()) {
				this.setToggled(false);
				Garuff.getInstance().clickGui.enterGUI();
			}else {
				this.setToggled(false);
			}
		}

	}

	public void onDisable() {
	}
}
