package com.garume.Garuff.module.modules.player;

import org.lwjgl.input.Keyboard;

import com.garume.Garuff.module.Category;
import com.garume.Garuff.module.Module;
import com.garume.Garuff.module.setting.settings.BooleanSetting;
import com.garume.Garuff.module.setting.settings.NumberSetting;

import net.minecraft.client.gui.GuiChat;
import net.minecraftforge.client.event.InputUpdateEvent;

public class GuiMove extends Module {

	public GuiMove() {
		super ("inventoryMove", "lets you move while in ur a gui screen.", Keyboard.KEY_NONE, Category.PLAYER);
		this.addSettings(pitchSpeed,yawSpeed,chat,sneak);
	}

    public NumberSetting pitchSpeed = new NumberSetting("PitchSpeed", this, 6, 0, 20 , 1);
    public NumberSetting yawSpeed = new NumberSetting("YawSpeed",this,  6, 0, 20 , 1);
    public BooleanSetting chat = new BooleanSetting("Chat", this, false);
    public BooleanSetting sneak = new BooleanSetting("Sneak", this, false);

    @Override
    public void onUpdate() {
        if (mc.currentScreen != null && !(mc.currentScreen instanceof GuiChat) || (mc.currentScreen instanceof GuiChat && chat.isEnabled())) {
            if (Keyboard.isKeyDown(Keyboard.KEY_UP)) {
                for (int i = 0; i < pitchSpeed.getValue(); ++i) {
                    mc.player.rotationPitch --;
                }
            }
            if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
                for (int i = 0; i < pitchSpeed.getValue(); ++i) {
                    mc.player.rotationPitch ++;
                }
            }
            if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
                for (int i = 0; i < yawSpeed.getValue(); ++i) {
                    mc.player.rotationYaw ++;
                }
            }
            if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
                for (int i = 0; i < yawSpeed.getValue(); ++i) {
                    mc.player.rotationYaw --;
                }
            }
            if (Keyboard.isKeyDown(mc.gameSettings.keyBindSprint.getKeyCode())) {
                mc.player.setSprinting(true);
            }
            if (mc.player.rotationPitch > 90) mc.player.rotationPitch = 90;
            if (mc.player.rotationPitch < -90) mc.player.rotationPitch = -90;
        }
    }

    @Override
    public void onKey(InputUpdateEvent event){
        if (isToggled() && mc.currentScreen != null && !(mc.currentScreen instanceof GuiChat) || (mc.currentScreen instanceof GuiChat && chat.isEnabled())) {
            if (Keyboard.isKeyDown(mc.gameSettings.keyBindForward.getKeyCode())) {
                event.getMovementInput().moveForward = getSpeed();
            }

            if (Keyboard.isKeyDown(mc.gameSettings.keyBindBack.getKeyCode())) {
                event.getMovementInput().moveForward = -getSpeed();
            }

            if (Keyboard.isKeyDown(mc.gameSettings.keyBindLeft.getKeyCode())) {
                event.getMovementInput().moveStrafe = getSpeed();
            }

            if (Keyboard.isKeyDown(mc.gameSettings.keyBindRight.getKeyCode())) {
                event.getMovementInput().moveStrafe = -getSpeed();
            }

            if (Keyboard.isKeyDown(mc.gameSettings.keyBindJump.getKeyCode())) {
                event.getMovementInput().jump = true;
            }

            if (Keyboard.isKeyDown(mc.gameSettings.keyBindSneak.getKeyCode()) && sneak.isEnabled()) {
                event.getMovementInput().sneak = true;
            }
        }
    }

    private float getSpeed() {
        float x = 1;
        if (Keyboard.isKeyDown(mc.gameSettings.keyBindSneak.getKeyCode()) && sneak.isEnabled()) {
            x = 0.30232558139f;
        }
        return x;
    }

}