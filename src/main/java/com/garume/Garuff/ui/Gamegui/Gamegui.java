package com.garume.Garuff.ui.Gamegui;

import baritone.utils.GuiClick;
import com.garume.Garuff.module.Category;
import com.garume.Garuff.module.Module;
import com.garume.Garuff.module.ModuleManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;

import java.util.ArrayList;


public class Gamegui extends GuiScreen {
    public static ArrayList<Runnable> renderList = new ArrayList<Runnable>();
    public static Timer timer = new Timer();
    public static Minecraft mc = Minecraft.getMinecraft();
    public static ArrayList<GuiClick> visibleNodes = new ArrayList<GuiClick>();
    public static boolean isOpen;
    public static GuiClick selected, description;
    public static int lastMouseX, lastMouseY, oldScale;
    public static boolean pasting;
    public static char pasteChar;
    public static Gamegui gui = new Gamegui();
    public static ArrayList<Point> groupCoords = new ArrayList<Point>();

    @Override
    public void initGui() {
        super.initGui();
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    @Override
    public void drawScreen(int mouseX2, int mouseY2, float partialTicks) {
        //Scale the gui to match the resolution and the gui scale.
        Dimension resolution = Toolkit.getDefaultToolkit().getScreenSize();
        GlStateManager.pushMatrix();
        float guiScale = (float) ((float) mc.displayWidth / resolution.getWidth());
        this.setGuiSize(Integer.MAX_VALUE, Integer.MAX_VALUE);
        final int mouseX = (int) (mouseX2 / guiScale);
        final int mouseY = (int) (mouseY2 / guiScale);

        GlStateManager.scale(guiScale, guiScale, guiScale);

        //Render the old things because theres no need to recalculate all the things like 140 times a second
        if (!timer.hasPassed(25)) {
            for (Runnable render : renderList) {
                render.run();
            }

            renderGames(mouseX, mouseY, partialTicks);
            GlStateManager.popMatrix();
            return;
        }

        renderList.clear();
        timer.reset();

        //Draw background
        drawRect(0, 0, 2000, 2000, 0x99000000);
        drawRect(0, 0, 2000, 2000, 0x45000000);

        visibleNodes.clear();
        description = null;

        //Draw changelog

        //Drag group

        // Top left info text
        //  if (GuiSettings.infoBox.booleanValue()) {
        //      renderList.add(() -> GlStateManager.pushMatrix());
        //    renderList.add(() -> GlStateManager.scale(0.9, 0.9, 0.9));
        //    int infoWidth = mc.fontRenderer.getStringWidth(ChatFormatting.RED + "Discord: " + ChatFormatting.GREEN + Mod.DISCORD + 2);
        //    drawRect(0, 0, infoWidth, 44, 0xFF000000);
        //    drawRect(0, 43, infoWidth, 44, 0x99d303fc);
        //    drawRect(infoWidth, 0, infoWidth + 1, 44, 0x99d303fc);
        //    drawStringWithShadow(ChatFormatting.RED + Mod.NAME + ChatFormatting.GREEN + " v" + Mod.VERSION, 2, 2, 0xFF000000);
        //    drawStringWithShadow(ChatFormatting.RED + "Made by: " + ChatFormatting.GREEN + "bebeli555", 2, 12, 0xFF000000);
        //    drawStringWithShadow(ChatFormatting.RED + "Discord: " + ChatFormatting.GREEN + Mod.DISCORD, 2, 22, 0xFF000000);
        //    drawStringWithShadow(ChatFormatting.RED + "Github: " + ChatFormatting.GREEN + "bebeli555/CookieClient", 2, 32, 0xFF000000);
        //    renderList.add(() -> GlStateManager.popMatrix());
        //}
        //Draw all visible nodes

        //Draw descriptions so they will overlay everything else


        for (Runnable render : renderList) {
            render.run();
        }

        renderGames(mouseX, mouseY, partialTicks);
        lastMouseX = mouseX;
        lastMouseY = mouseY;
        GlStateManager.popMatrix();
    }

    @Override
    protected void mouseClicked(int x, int y, int button) {
        //Just use the lastMouse positions saved by the rendering loop because these are different
        x = lastMouseX;
        y = lastMouseY;

        for (Module module : ModuleManager.modules) {
            if (module.isOn()) {
                if (module.onGuiClick(x, y, button)) {
                    return;
                }
            }
        }
    }

    @SubscribeEvent
    public void onKeyPress(GuiScreenEvent.KeyboardInputEvent.Post e) {
        for (Module module : ModuleManager.modules) {
            if (module.isOn()) {
                module.onGuiKeyPress(e);
            }
        }
    }


    public static double getGuiScale(float start) {
        ScaledResolution scaledResolution = new ScaledResolution(mc);
        if (scaledResolution.getScaleFactor() == 4) {
            start += -0.5 * start;
        } else if (scaledResolution.getScaleFactor() == 1) {
            start += 1 * start;
        } else if (scaledResolution.getScaleFactor() == 3) {
            start += -0.3 * start;
        }

        return start;
    }

    public static void drawRectDouble(double left, double top, double right, double bottom, int color) {
        if (left < right) {
            double i = left;
            left = right;
            right = i;
        }

        if (top < bottom) {
            double j = top;
            top = bottom;
            bottom = j;
        }

        float f3 = (float) (color >> 24 & 255) / 255.0F;
        float f = (float) (color >> 16 & 255) / 255.0F;
        float f1 = (float) (color >> 8 & 255) / 255.0F;
        float f2 = (float) (color & 255) / 255.0F;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.color(f, f1, f2, f3);
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION);
        bufferbuilder.pos(left, bottom, 0.0D).endVertex();
        bufferbuilder.pos(right, bottom, 0.0D).endVertex();
        bufferbuilder.pos(right, top, 0.0D).endVertex();
        bufferbuilder.pos(left, top, 0.0D).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }


    public static void renderGames(int mouseX, int mouseY, float partialTicks) {
        for (Module module : ModuleManager.modules) {
            if (module.isOn()) {
                module.onGuiDrawScreen(mouseX, mouseY, partialTicks);
            }
        }
    }
}