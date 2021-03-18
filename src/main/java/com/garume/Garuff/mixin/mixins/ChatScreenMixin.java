package com.garume.Garuff.mixin.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.garume.Garuff.module.ModuleManager;

import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiTextField;



@Mixin(GuiChat.class)
public class ChatScreenMixin
{
	@Shadow
    protected GuiTextField inputField;

    @Inject(at = @At("TAIL"), method = "initGui")
	protected void onInit(CallbackInfo ci)
	{
		if(ModuleManager.isModuleEnabled("InfinityChat"))
			System.out.print("working");
			inputField.setMaxStringLength(Integer.MAX_VALUE);
	}
}
