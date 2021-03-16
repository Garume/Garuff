package com.garume.Garuff.mixin.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.client.entity.EntityPlayerSP;

/*
 * this is from gamesense, im just a gamesense skid don't talk to me.
 */

@Mixin(EntityPlayerSP.class)
public interface AccessorEntityPlayerSP {

    @Accessor("handActive")
    void gsSetHandActive(boolean value);

}