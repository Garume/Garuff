package com.garume.Garuff.mixin.mixins;

import com.garume.Garuff.event.events.event.RenderRainEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.garume.Garuff.Garuff;

import net.minecraft.world.World;

@Mixin(World.class)
public class MixinWorld {

    @Inject(method = "getRainStrength", at = @At("HEAD"), cancellable = true)
    public void getRainStrength(float delta, CallbackInfoReturnable<Float> callback) {
        RenderRainEvent event = new RenderRainEvent();
        Garuff.EVENT_BUS.post(event);
        if (event.isCancelled()) {
            callback.cancel();
            callback.setReturnValue(0.0f);
        }
    }
}
