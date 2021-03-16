package com.garume.Garuff.mixin.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;

@Mixin(Render.class)
abstract class MixinRender<T extends Entity> {

    @Shadow
    protected abstract boolean bindEntityTexture(T entity);
}