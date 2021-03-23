package com.garume.Garuff.mixin.mixins;

import com.garume.Garuff.Garuff;
import com.garume.Garuff.event.Event;
import com.garume.Garuff.event.events.PlayerMotionUpdateEvent;
import com.garume.Garuff.event.events.PlayerMoveEvent;
import com.garume.Garuff.event.events.PlayerUpdateEvent;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.MoverType;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;



@Mixin(net.minecraft.client.entity.EntityPlayerSP.class)
public abstract class MixinEntityPlayerSP extends AbstractClientPlayer {

    public MixinEntityPlayerSP() {
        super(null, null);
    }

    @Inject(method = "onUpdateWalkingPlayer", at = @At("HEAD"), cancellable = true)
    public void OnPreUpdateWalkingPlayer(CallbackInfo info) {
        PlayerMotionUpdateEvent event = new PlayerMotionUpdateEvent(Event.Era.PRE);
        Garuff.EVENT_BUS.post(event);
        if (event.isCancelled())
            info.cancel();
    }

    @Inject(method = "onUpdateWalkingPlayer", at = @At("RETURN"), cancellable = true)
    public void OnPostUpdateWalkingPlayer(CallbackInfo p_Info) {
        PlayerMotionUpdateEvent event = new PlayerMotionUpdateEvent(Event.Era.POST);
        Garuff.EVENT_BUS.post(event);
        if (event.isCancelled())
            p_Info.cancel();
    }

    @Inject(method = "onUpdate", at = @At("HEAD"), cancellable = true)
    public void onUpdate(CallbackInfo info) {
        PlayerUpdateEvent event = new PlayerUpdateEvent();
        Garuff.EVENT_BUS.post(event);
        if (event.isCancelled())
            info.cancel();
    }

    @Redirect(method = "move", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/entity/AbstractClientPlayer;move(Lnet/minecraft/entity/MoverType;DDD)V"))
    public void move(AbstractClientPlayer player, MoverType type, double x, double y, double z) {
        PlayerMoveEvent moveEvent = new PlayerMoveEvent(type, x, y, z);
        Garuff.EVENT_BUS.post(moveEvent);
        super.move(type, moveEvent.x, moveEvent.y, moveEvent.z);
    }
}