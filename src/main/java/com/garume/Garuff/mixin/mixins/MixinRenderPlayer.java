package com.garume.Garuff.mixin.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.garume.Garuff.Garuff;
import com.garume.Garuff.event.events.RenderEntityNameEvent;

import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.RenderPlayer;

@Mixin(RenderPlayer.class)
public class MixinRenderPlayer {
    @Inject(method = "renderEntityName", at = @At("HEAD"), cancellable = true)
    public void renderLivingLabel(AbstractClientPlayer entityIn, double x, double y, double z, String name, double distanceSq, CallbackInfo info) {
        RenderEntityNameEvent event = new RenderEntityNameEvent(entityIn, x, y, z, name, distanceSq);
        Garuff.EVENT_BUS.post(event);
        if (event.isCancelled())
            info.cancel();
    }
}