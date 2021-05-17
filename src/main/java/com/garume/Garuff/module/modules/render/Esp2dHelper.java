package com.garume.Garuff.module.modules.render;


import com.garume.Garuff.event.events.event.RenderEvent;
import com.garume.Garuff.module.Category;
import com.garume.Garuff.module.Module;
import com.garume.Garuff.module.ModuleManager;
import com.garume.Garuff.module.modules.pvp.Surround;
import com.garume.Garuff.util.api.render.JColor;
import com.garume.Garuff.util.api.render.JTessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;


import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.Vec3d;

import java.lang.reflect.Field;

/*
 * original code in this is from finz0's Osiris.
 * SrgantMooMoo 12/11/20.
 * ...put this here to help fix my shitty code, in no way is this the best way to do it xD
 */
public class Esp2dHelper extends Module {

    public Esp2dHelper() {
        super("Esp2dHelper", "eeeeeeeEsp2dHelper", Keyboard.KEY_NONE, Category.CLIENT);
        toggled = true;
    }
    public Esp esp = (Esp) ModuleManager.getModuleByName("esp's");

    JColor ppColor;
    int opacityGradient;
    private static final Field XPOS;
    private static final Field YPOS;
    private static final Field ZPOS;
    static  {
        XPOS = ObfuscationReflectionHelper.findField(RenderManager.class,"renderPosX");
        YPOS = ObfuscationReflectionHelper.findField(RenderManager.class,"renderPosY");
        ZPOS = ObfuscationReflectionHelper.findField(RenderManager.class,"renderPosZ");
    }
    public void onWorldRender(RenderEvent event) {
        Minecraft mc = Minecraft.getMinecraft();
        //add mobs and items to 2dEsp
        if (ModuleManager.getModuleByName("esp's") != null && esp.isToggled() && esp.entityMode.is("2dEsp")) {
            if ((mc.getRenderManager()).options == null)
                return;
            float viewerYaw = (mc.getRenderManager()).playerViewY;
            mc.world.loadedEntityList.stream().filter(entity -> entity != mc.player).forEach(e -> {
                JTessellator.prepare();
                GlStateManager.pushMatrix();
                Vec3d pos = Surround.getInterpolatedPos(e, mc.getRenderPartialTicks());
                try {
                    GlStateManager.translate(pos.x - (double)XPOS.get(mc.getRenderManager()), pos.y - (double)YPOS.get(mc.getRenderManager()), pos.z - (double)ZPOS.get(mc.getRenderManager()));
                } catch (IllegalAccessException illegalAccessException) {
                    illegalAccessException.printStackTrace();
                }
                GlStateManager.glNormal3f(0.0F, 1.0F, 0.0F);
                GlStateManager.rotate(-viewerYaw, 0.0F, 1.0F, 0.0F);
                GL11.glEnable(2848);
                if (e instanceof net.minecraft.entity.player.EntityPlayer) {
                    ppColor = new JColor(((Esp) ModuleManager.getModuleByName("esp's")).playerColor.getValue());
                    GlStateManager.glLineWidth((float) esp.lineWidth.getValue());
                    ppColor.glColor();
                    GL11.glBegin(2);
                    GL11.glVertex2d(-e.width, 0.0D);
                    GL11.glVertex2d(-e.width, (e.height / 4.0F));
                    GL11.glVertex2d(-e.width, 0.0D);
                    GL11.glVertex2d((-e.width / 4.0F * 2.0F), 0.0D);
                    GL11.glEnd();
                    GL11.glBegin(2);
                    GL11.glVertex2d(-e.width, e.height);
                    GL11.glVertex2d((-e.width / 4.0F * 2.0F), e.height);
                    GL11.glVertex2d(-e.width, e.height);
                    GL11.glVertex2d(-e.width, (e.height / 2.5F * 2.0F));
                    GL11.glEnd();
                    GL11.glBegin(2);
                    GL11.glVertex2d(e.width, e.height);
                    GL11.glVertex2d((e.width / 4.0F * 2.0F), e.height);
                    GL11.glVertex2d(e.width, e.height);
                    GL11.glVertex2d(e.width, (e.height / 2.5F * 2.0F));
                    GL11.glEnd();
                    GL11.glBegin(2);
                    GL11.glVertex2d(e.width, 0.0D);
                    GL11.glVertex2d((e.width / 4.0F * 2.0F), 0.0D);
                    GL11.glVertex2d(e.width, 0.0D);
                    GL11.glVertex2d(e.width, (e.height / 4.0F));
                    GL11.glEnd();
                }
                JTessellator.release();
                GlStateManager.popMatrix();
            });
        }
    }

    public boolean rangeEntityCheck(Entity entity) {
        if (entity.getDistance(mc.player) > esp.range.getValue()){
            return false;
        }

        if (entity.getDistance(mc.player) >= 180){
            opacityGradient = 50;
        }
        else if (entity.getDistance(mc.player) >= 130 && entity.getDistance(mc.player) < 180){
            opacityGradient = 100;
        }
        else if (entity.getDistance(mc.player) >= 80 && entity.getDistance(mc.player) < 130){
            opacityGradient = 150;
        }
        else if (entity.getDistance(mc.player) >= 30 && entity.getDistance(mc.player) < 80){
            opacityGradient = 200;
        }
        else {
            opacityGradient = 255;
        }

        return true;
    }

    public boolean rangeTileCheck(TileEntity tileEntity) {
        //the range value has to be squared for this
        if (tileEntity.getDistanceSq(mc.player.posX, mc.player.posY, mc.player.posZ) > esp.range.getValue() * esp.range.getValue()){
            return false;
        }

        if (tileEntity.getDistanceSq(mc.player.posX, mc.player.posY, mc.player.posZ) >= 32400){
            opacityGradient = 50;
        }
        else if (tileEntity.getDistanceSq(mc.player.posX, mc.player.posY, mc.player.posZ) >= 16900 && tileEntity.getDistanceSq(mc.player.posX, mc.player.posY, mc.player.posZ) < 32400){
            opacityGradient = 100;
        }
        else if (tileEntity.getDistanceSq(mc.player.posX, mc.player.posY, mc.player.posZ) >= 6400 && tileEntity.getDistanceSq(mc.player.posX, mc.player.posY, mc.player.posZ) < 16900){
            opacityGradient = 150;
        }
        else if (tileEntity.getDistanceSq(mc.player.posX, mc.player.posY, mc.player.posZ) >= 900 && tileEntity.getDistanceSq(mc.player.posX, mc.player.posY, mc.player.posZ) < 6400){
            opacityGradient = 200;
        }
        else {
            opacityGradient = 255;
        }

        return true;
    }
}