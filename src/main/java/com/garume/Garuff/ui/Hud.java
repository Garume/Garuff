package com.garume.Garuff.ui;

import java.awt.Color;
import java.util.Collections;
import java.util.Comparator;

import com.garume.Garuff.Garuff;
import com.garume.Garuff.module.Module;
import com.garume.Garuff.module.ModuleManager;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Hud extends Gui {

	private Minecraft mc = Minecraft.getMinecraft();

	public static class ModuleComparetor implements Comparator<Module> {
		@Override
		public int compare(Module arg0, Module arg1) {
			if(Minecraft.getMinecraft().fontRenderer.getStringWidth(arg0.getName()) > Minecraft.getMinecraft().fontRenderer.getStringWidth(arg1.getName())) {
				return -1;
			}
			if(Minecraft.getMinecraft().fontRenderer.getStringWidth(arg0.getName()) > Minecraft.getMinecraft().fontRenderer.getStringWidth(arg1.getName())) {
				return 1;
			}
			return 0;
		}
	}

	private final ResourceLocation wartermark = new ResourceLocation(Garuff.MOD_ID,"textures/watermark.png");

	@SubscribeEvent
	public void renderOverlay(RenderGameOverlayEvent event) {
		Collections.sort(ModuleManager.modules, new ModuleComparetor());
		ScaledResolution sr = new ScaledResolution(mc);
		FontRenderer fr = mc.fontRenderer;

		//clientlogo
		if(event.getType() == RenderGameOverlayEvent.ElementType.TEXT) {
			mc.renderEngine.bindTexture(wartermark);
			drawScaledCustomSizeModalRect(0, 0, 0, 0, 50, 50, 50, 50, 50, 50);
		}

		//client name
		if(event.getType() == RenderGameOverlayEvent.ElementType.TEXT) {
			fr.drawStringWithShadow("Garuff", 2, 1, 0xffffffff);
			fr.drawStringWithShadow(Garuff.MOD_VERSION, 30, 1, 0xfffffacd);
		}
		//client arraylist
		if(event.getType() == RenderGameOverlayEvent.ElementType.TEXT) {
			int y = 2;
			final int[] counter = {1};
			for(Module module : ModuleManager.getModules()) {
				if(!module.getName().equalsIgnoreCase("TabGUI") && module.isToggled()) {
					fr.drawStringWithShadow(module.getName(), sr.getScaledWidth() - fr.getStringWidth(module.getName()) - 2 , y, rainbow(counter[0]*300 ));
					y += fr.FONT_HEIGHT;
					counter[0]++;

				}
			}
		}
	}

		public static int rainbow(int delay) {
			double rainbowState = Math.ceil((System.currentTimeMillis() + delay) / 10.0);
			rainbowState %= 360;
			return Color.getHSBColor((float)(rainbowState / 360.0f), 1f, 1f).getRGB();


	}
}
