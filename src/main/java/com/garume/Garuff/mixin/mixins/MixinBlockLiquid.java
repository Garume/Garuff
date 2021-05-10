package com.garume.Garuff.mixin.mixins;

import com.garume.Garuff.event.events.event.CanCollideCheckEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.garume.Garuff.Garuff;

import net.minecraft.block.BlockLiquid;
import net.minecraft.block.state.IBlockState;

@Mixin(BlockLiquid.class)
public class MixinBlockLiquid {

	@Inject(method = "canCollideCheck", at = @At("HEAD"), cancellable = true)
    public void canCollideCheck(final IBlockState blockState, final boolean b, final CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
        CanCollideCheckEvent event = new CanCollideCheckEvent();
        Garuff.EVENT_BUS.post(event);
        callbackInfoReturnable.setReturnValue(event.isCancelled());
    }
}
