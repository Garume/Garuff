package com.garume.Garuff.module;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import com.garume.Garuff.Garuff;
import com.garume.Garuff.event.events.RenderEvent;
import com.garume.Garuff.module.setting.Setting;
import com.garume.Garuff.module.setting.settings.KeybindSetting;
import com.lukflug.panelstudio.settings.Toggleable;

import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.InputUpdateEvent;
import net.minecraftforge.common.MinecraftForge;

public class Module implements Toggleable{

	public String name, description;
	public KeybindSetting keyCode = new KeybindSetting(0);
	private Category category;
	public boolean toggled;
	public boolean expanded;
	public List<Setting> settings = new ArrayList<Setting>();
	public int index;

	protected static final Minecraft mc = Minecraft.getMinecraft();

	public Module(String name, String description , int key , Category category) {
		super();
		this.name = name;
		this.description = description;
		keyCode.code = key;
		this.addSettings(keyCode);
		this.category = category;
		this.toggled = false;
	}

	public void onUpdate(){}

	public void onRender(){}

	public void onKey(InputUpdateEvent event){}

	public void enable() {
		setToggled(true);
	}
	public void onWorldRender(RenderEvent event) {}

	public void disable() {
		setToggled(false);
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getKey(){
		return keyCode.code;
	}

	public void setkey(int key) {
		this.keyCode.code = key;

		if(Garuff.saveLoad != null) {
			Garuff.saveLoad.save();
		}
	}

	public boolean isToggled() {
		return toggled;
	}

	public void setToggled(boolean toggled) {
		this.toggled = toggled;
		if(this.toggled) {
			this.onEnable();
		}else {
			this.onDisable();
		}
	}

	public void toggle() {
		this.toggled = !this.toggled;

		if(this.toggled) {
			this.onEnable();
		}else {
			this.onDisable();
		}

	}

	public void onEnable() {
		MinecraftForge.EVENT_BUS.register(this);
	}

	public void onDisable() {
		MinecraftForge.EVENT_BUS.unregister(this);
	}

	public void addSettings(Setting... settings) {
		this.settings.addAll(Arrays.asList(settings));
		this.settings.sort(Comparator.comparingInt(s -> s == keyCode ? 1 : 0));
	}

	public String getName() {
		return this.name;
	}

	public Category getCategory() {
		return this.category;
	}

	public final boolean isOn() {
		return toggled;
	}


}
