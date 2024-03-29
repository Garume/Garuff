package com.garume.Garuff.mixin.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.garume.Garuff.module.ModuleManager;
import com.garume.Garuff.module.modules.player.NoSlow;

import net.minecraft.block.BlockSlime;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

@Mixin(BlockSlime.class)
public class MixinBlockSlimeBlock {
    @Inject(method = "onEntityWalk", at = @At("HEAD"), cancellable = true)
    private void onSteppedOn(World world, BlockPos pos, Entity entity, CallbackInfo info) {
    	if (ModuleManager.isModuleEnabled("noSlow") && ((NoSlow)ModuleManager.getModuleByName("noSlow")).slimeBlock.isEnabled())
        	info.cancel();
    }
}
