package com.garume.Garuff.mixin.mixins;

import com.garume.Garuff.Garuff;
import com.garume.Garuff.event.events.TransformSideFirstPersonEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.At;


import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.util.EnumHandSide;

@Mixin(ItemRenderer.class)
public class MixinItemRenderer {

	@Inject(method = "transformSideFirstPerson", at = @At("HEAD"))
	public void transformSideFirstPerson(EnumHandSide hand, float p_187459_2_, CallbackInfo callbackInfo) {
		TransformSideFirstPersonEvent event = new TransformSideFirstPersonEvent(hand);
		Garuff.EVENT_BUS.post(event);
	}

	@Inject(method = "transformFirstPerson", at = @At("HEAD"))
	public void transformFirstPerson(EnumHandSide hand, float p_187453_2_, CallbackInfo callbackInfo) {
		TransformSideFirstPersonEvent event = new TransformSideFirstPersonEvent(hand);
		Garuff.EVENT_BUS.post(event);
	}

}