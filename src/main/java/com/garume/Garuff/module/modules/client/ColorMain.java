package com.garume.Garuff.module.modules.client;

import java.awt.Color;
import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

import com.garume.Garuff.module.Category;
import com.garume.Garuff.module.Module;
import com.garume.Garuff.module.setting.settings.ModeSetting;
import com.garume.Garuff.util.api.render.JColor;

import net.minecraft.util.text.TextFormatting;

public class ColorMain extends Module {

	private static final Module ColorMain = null;
	public static ModeSetting colorModel = new ModeSetting("penis right?", ColorMain, "HSB", "RGB", "HSB");

	public ColorMain() {
		super ("colorMain", "world of colors", Keyboard.KEY_NONE, Category.CLIENT);
		this.addSettings(colorModel);
	}

		public void setup() {
			ArrayList<String> tab = new ArrayList<>();
			tab.add("Black");
			tab.add("Dark Green");
			tab.add("Dark Red");
			tab.add("Gold");
			tab.add("Dark Gray");
			tab.add("Green");
			tab.add("Red");
			tab.add("Yellow");
			tab.add("Dark Blue");
			tab.add("Dark Aqua");
			tab.add("Dark Purple");
			tab.add("Gray");
			tab.add("Blue");
			tab.add("Aqua");
			tab.add("Light Purple");
			tab.add("White");
			ArrayList<String> models=new ArrayList<>();
			models.add("RGB");
			models.add("HSB");
		}

		public void onEnable(){
			this.disable();
		}

		private static TextFormatting settingToFormatting () {
				return TextFormatting.AQUA;
		}

		public static TextFormatting getFriendColor(){
			return settingToFormatting();
		}

		public static TextFormatting getEnemyColor() {
			return settingToFormatting();
		}

		public static TextFormatting getEnabledColor(){return settingToFormatting();}

		public static TextFormatting getDisabledColor(){return settingToFormatting();}

		@SuppressWarnings("unused")
		private static Color settingToColor() {
				return Color.cyan;
		}

		public static JColor getFriendGSColor(){
			return new JColor(0xffffffff);
		}

		public static JColor getEnemyGSColor(){
			return new JColor(0xffffffff);
	}

}
