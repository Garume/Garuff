package com.garume.Garuff.module.setting.settings;

import com.garume.Garuff.Garuff;
import com.garume.Garuff.module.Module;
import com.garume.Garuff.module.setting.Setting;
import com.lukflug.panelstudio.settings.Toggleable;


/*
 * Written by @SrgantMooMoo on 11/17/20.
 */

public class BooleanSetting extends Setting implements Toggleable {
  public boolean enabled;

  public BooleanSetting(String name, Module parent, boolean enabled) {
    this.name = name;
    this.parent = parent;
    this.enabled = enabled;
  }

  public boolean isEnabled() {
    return this.enabled;
  }

  public void setEnabled(boolean enabled) {
    this.enabled = enabled;

    if(Garuff.saveLoad != null) {
    	Garuff.saveLoad.save();
	}
  }

  public void toggle() {
    this.enabled = !this.enabled;

    if(Garuff.saveLoad != null) {
		Garuff.saveLoad.save();
	}
  }

@Override
public boolean isOn() {
	return this.isEnabled();
}
}
