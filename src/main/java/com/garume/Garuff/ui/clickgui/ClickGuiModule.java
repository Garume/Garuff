package com.garume.Garuff.ui.clickgui;

import com.garume.Garuff.ui.Gamegui.Gamegui;
import com.garume.Garuff.util.api.Wrapper;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

import com.garume.Garuff.Garuff;
import com.garume.Garuff.module.Category;
import com.garume.Garuff.module.Module;
import com.garume.Garuff.module.ModuleManager;
import com.garume.Garuff.module.setting.settings.BooleanSetting;
import com.garume.Garuff.module.setting.settings.ColorSetting;
import com.garume.Garuff.module.setting.settings.ModeSetting;
import com.garume.Garuff.module.setting.settings.NumberSetting;
import com.garume.Garuff.util.api.render.JColor;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ClickGuiModule extends Module {
	public static ClickGuiModule INSTANCE;

	public ModeSetting theme = new ModeSetting("theme", this, "new", "new", "old");
	public NumberSetting animationSpeed = new NumberSetting("animation", this, 150, 0, 1000, 50);
	public NumberSetting scrolls = new NumberSetting("scrollSpeed", this, 10, 0, 100, 1);
	public ModeSetting scrollMode = new ModeSetting("scroll", this, "container", "container", "screen");
	public ModeSetting description = new ModeSetting("description", this, "mouse", "mouse", "fixed");
	public ColorSetting enabledColor = new ColorSetting("enabledColor", this, new JColor(121, 193, 255, 255)); //(0, 157, 255, 255));
	public ColorSetting backgroundColor = new ColorSetting("bgColor", this, new JColor(0, 0, 0, 255)); //(0, 121, 194, 255));
	public ColorSetting settingBackgroundColor = new ColorSetting("settinBgColor", this, new JColor(0, 0, 0, 255));
	public ColorSetting outlineColor = new ColorSetting("settingsHighlight", this, new JColor(255, 255, 255, 150));
	public ColorSetting fontColor = new ColorSetting("categoryColor", this, new JColor(255, 255, 255, 255));
	public NumberSetting opacity = new NumberSetting("opacity", this, 255, 0, 255, 5);

	public ModeSetting backgroundParticle = new ModeSetting("Particle", this, "Blur", "Blur", "None");

	public BooleanSetting thinGui = new BooleanSetting("thinGui", this, false);

	private final ResourceLocation watermark = new ResourceLocation("garuff", "textures/watermark.png");

	public ClickGuiModule() {
		super("ClickGuiModule", "classic hud", Keyboard.KEY_RSHIFT, Category.CLIENT);
		this.addSettings(scrollMode, scrolls, description, animationSpeed, opacity, fontColor, enabledColor, backgroundColor, settingBackgroundColor, outlineColor, backgroundParticle);
		INSTANCE = this;
	}

	@SubscribeEvent
	public void renderOverlay(RenderGameOverlayEvent event) {
		ScaledResolution sr = new ScaledResolution(mc);
		if (event.getType() == RenderGameOverlayEvent.ElementType.BOSSHEALTH) {
			mc.renderEngine.bindTexture(watermark);
			Gui.drawScaledCustomSizeModalRect(sr.getScaledWidth() - 200, sr.getScaledHeight() - 200, 0, 0, 100, 100, 200, 200, 100, 100);
		}
	}

	public static Module getClickGuiModule() {
		return INSTANCE;
	}

	public void onEnable() {
		super.onEnable();
		MinecraftForge.EVENT_BUS.register(this);
		Garuff.getInstance().clickGui.enterGUI();
		if (backgroundParticle.is("Blur")) {
			if (Wrapper.getMinecraft().entityRenderer.getShaderGroup() != null) {
				Wrapper.getMinecraft().entityRenderer.getShaderGroup().deleteShaderGroup();
			}
			Wrapper.getMinecraft().entityRenderer.loadShader(new ResourceLocation("shaders/post/blur.json"));
		}
	}

	public void onUpdate() {
		if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
			this.setToggled(!toggled);
		}
		if (ModuleManager.getModuleByName("hudEditor").isToggled()) {
			this.setToggled(!toggled);
		}
	}

	public void onDisable() {
		super.onDisable();
		for (Module module : ModuleManager.getModulesInCategory(Category.GAME)){
			module.disable();
		}
		MinecraftForge.EVENT_BUS.unregister(this);
		if(Wrapper.getMinecraft().entityRenderer.getShaderGroup() != null) {
			Wrapper.getMinecraft().entityRenderer.getShaderGroup().deleteShaderGroup();
		}
	}
	@SubscribeEvent
	public void onTick(TickEvent.ClientTickEvent e){
		for (Module module : ModuleManager.getModulesInCategory(Category.GAME)){
			if (module.isOn()){
				mc.displayGuiScreen(new Gamegui());
				if (backgroundParticle.is("Blur")) {
					Wrapper.getMinecraft().entityRenderer.getShaderGroup().deleteShaderGroup();}
			}
		}
	}
}