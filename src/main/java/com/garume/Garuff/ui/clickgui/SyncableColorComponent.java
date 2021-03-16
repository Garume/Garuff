package com.garume.Garuff.ui.clickgui;


import com.garume.Garuff.module.ModuleManager;
import com.garume.Garuff.module.setting.settings.ColorSetting;
import com.lukflug.panelstudio.Animation;
import com.lukflug.panelstudio.Context;
import com.lukflug.panelstudio.FocusableComponent;
import com.lukflug.panelstudio.Interface;
import com.lukflug.panelstudio.settings.ColorComponent;
import com.lukflug.panelstudio.settings.Toggleable;
import com.lukflug.panelstudio.theme.Renderer;
import com.lukflug.panelstudio.theme.Theme;

import net.minecraft.util.text.TextFormatting;

public class SyncableColorComponent extends ColorComponent {
	public SyncableColorComponent (Theme theme, ColorSetting setting, Toggleable colorToggle, Animation animation) {
		super(TextFormatting.BOLD+setting.name,null,theme.getContainerRenderer(),animation,theme.getComponentRenderer(),setting,true,true,colorToggle);
		if (setting!=((ClickGuiModule) ModuleManager.getModuleByName("clickGuiModule")).enabledColor) addComponent(new SyncButton(theme.getComponentRenderer()));
	}

	private class SyncButton extends FocusableComponent {
		public SyncButton (Renderer renderer) {
			super("Sync Color",null,renderer);
		}

		@Override
		public void render (Context context) {
			super.render(context);
			renderer.overrideColorScheme(overrideScheme);
			renderer.renderTitle(context,title,hasFocus(context),false);
			renderer.restoreColorScheme();
		}

		@Override
		public void handleButton (Context context, int button) {
			super.handleButton(context,button);
			if (button==Interface.LBUTTON && context.isClicked()) {
				setting.setValue(((ClickGuiModule) ModuleManager.getModuleByName("clickGuiModule")).enabledColor.getColor());
				setting.setRainbow(((ClickGuiModule) ModuleManager.getModuleByName("clickGuiModule")).enabledColor.getRainbow());
			}
		}
	}
}
