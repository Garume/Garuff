package com.garume.Garuff.mixin.mixins;

import java.util.UUID;

import javax.annotation.Nullable;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.garume.Garuff.Garuff;
import com.garume.Garuff.module.ModuleManager;

import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.util.ResourceLocation;

@Mixin(AbstractClientPlayer.class)
public abstract class MixinAbstractClientPlayer {

	@Shadow @Nullable protected abstract NetworkPlayerInfo getPlayerInfo();

	@Inject(method = "getLocationCape", at = @At("HEAD"), cancellable = true)
	public void getLocationCape(CallbackInfoReturnable<ResourceLocation> callbackInfoReturnable) {
		UUID uuid = getPlayerInfo().getGameProfile().getId();
		Garuff.getInstance();
		if (ModuleManager.isModuleEnabled("capes") && Garuff.cape.hasCape(uuid)) {
			callbackInfoReturnable.setReturnValue(new ResourceLocation(Garuff.MOD_ID, "textures/postmancape.png"));
		}
	}
}