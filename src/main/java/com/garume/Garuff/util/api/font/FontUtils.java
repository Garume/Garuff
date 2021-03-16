package com.garume.Garuff.util.api.font;

import com.garume.Garuff.Garuff;
import com.garume.Garuff.util.api.render.JColor;

import net.minecraft.client.Minecraft;

public class FontUtils {

	private static final Minecraft mc = Minecraft.getMinecraft();

	public static float drawStringWithShadow(boolean customFont, String text, int x, int y, JColor color) {
		if(customFont) {
			return Garuff.getInstance().customFontRenderer.drawStringWithShadow(text, x, y, color);
		}
		else {
			return mc.fontRenderer.drawStringWithShadow(text, x, y, color.getRGB());
		}
	}

	public static int getStringWidth(boolean customFont, String string) {
		if (customFont) {
			return Garuff.getInstance().customFontRenderer.getStringWidth(string);
		}
		else {
			return mc.fontRenderer.getStringWidth(string);
		}
	}

	public static int getFontHeight(boolean customFont) {
		if (customFont) {
			return Garuff.getInstance().customFontRenderer.getHeight();
		}
		else {
			return mc.fontRenderer.FONT_HEIGHT;
		}
	}
}