package com.garume.Garuff.ui.hud;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.garume.Garuff.Garuff;
import com.garume.Garuff.module.Category;
import com.garume.Garuff.module.HudModule;
import com.garume.Garuff.module.Module;
import com.garume.Garuff.module.ModuleManager;
import com.garume.Garuff.module.setting.settings.BooleanSetting;
import com.garume.Garuff.module.setting.settings.ColorSetting;
import com.garume.Garuff.module.setting.settings.ModeSetting;
import com.garume.Garuff.util.api.render.JColor;
import com.lukflug.panelstudio.hud.HUDList;
import com.lukflug.panelstudio.hud.ListComponent;
import com.lukflug.panelstudio.theme.Theme;


public class ArrayListt extends HudModule {
	private ModuleArrayList list=new ModuleArrayList();

	public ColorSetting color = new ColorSetting("color", this, new JColor(255, 255, 255, 255));
	public ModeSetting sortHeight = new ModeSetting("sortHeight", this, "betic", "betic", "up", "down");
	public ModeSetting sortLength = new ModeSetting("sortLength", this, "left", "left", "right");
	public BooleanSetting forgeHax = new BooleanSetting("forgeHax", this, true);
	public BooleanSetting showHidden = new BooleanSetting("showHidden", this, false);

	public ArrayListt() {
		super("arrayList", "shows currently enabled modules.", new Point(-3,59), Category.HUD);
		this.addSettings(color, sortHeight, sortLength, showHidden, forgeHax);
	}

    @Override
    public void populate (Theme theme) {
    	component = new ListComponent(getName(),theme.getPanelRenderer(),position,list);
    }

    public void onRender() {
    	list.activeModules.clear();
    	for (Module module: ModuleManager.getModules()) {
    		if(!showHidden.isEnabled()) {
    			if (module.isToggled()
    					&& !module.getName().equalsIgnoreCase("Watermark")
    					&& !module.getName().equalsIgnoreCase("Totems")
    					&& !module.getName().equalsIgnoreCase("Ping")
    					&& !module.getName().equalsIgnoreCase("Frames")
    					&& !module.getName().equalsIgnoreCase("AutoCrystalInfo")
    					&& !module.getName().equalsIgnoreCase("SurroundInfo")
    					&& !module.getName().equalsIgnoreCase("ArrayList")
    					&& !module.getName().equalsIgnoreCase("InventoryViewer")
    					&& !module.getName().equalsIgnoreCase("Hey")
    					&& !module.getName().equalsIgnoreCase("ArmorHud")
    					&& !module.getName().equalsIgnoreCase("KeyStrokes")
    					&& !module.getName().equalsIgnoreCase("DiscordRpc")
    					&& !module.getName().equalsIgnoreCase("ClickGuiModule")
    					&& !module.getName().equalsIgnoreCase("HudEditor")
    					&& !module.getName().equalsIgnoreCase("TabGui")
    					&& !module.getName().equalsIgnoreCase("MainMenuInfo")
    					&& !module.getName().equalsIgnoreCase("coords")
    					&& !module.getName().equalsIgnoreCase("Esp2dHelper")
    					&& !module.getName().equalsIgnoreCase("killAuraInfo")
    					&& !module.getName().equalsIgnoreCase("capes")
    					&& !module.getName().equalsIgnoreCase("clientFont")) {
    				list.activeModules.add(module);
    			}
    		}else
    			if (module.isToggled() && !module.getName().equalsIgnoreCase("Esp2dHelper")) list.activeModules.add(module);
    	}
    	if(sortHeight.is("up") || sortHeight.is("down")) {
    	list.activeModules.sort(Comparator.comparing(module -> -Garuff.getInstance().clickGui.guiInterface.getFontWidth(module.getName())));
    	}
    }

    private class ModuleArrayList implements HUDList {

		public List<Module> activeModules=new ArrayList<Module>();

		@Override
		public int getSize() {
			return activeModules.size();
		}

		@Override
		public String getItem(int index) {
			Module module = activeModules.get(index);
			if(forgeHax.isEnabled() && sortLength.is("right")) return module.getName() + "<";
			else if(forgeHax.isEnabled() && sortLength.is("left")) return ">" + module.getName();
			else return module.getName();
		}

		@Override
		public Color getItemColor(int index) {
			JColor c = color.getValue();
			return Color.getHSBColor(c.getHue() + (color.getRainbow() ? .05f * index : 0), (color.getRainbow() ? 0.5f : c.getSaturation()), c.getBrightness());
		}

		@Override
		public boolean sortUp() {
			return sortHeight.is("up");
		}

		@Override
		public boolean sortRight() {
			return sortLength.is("right");
		}
	}
}