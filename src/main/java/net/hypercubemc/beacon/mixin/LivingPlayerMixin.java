package net.hypercubemc.beacon.mixin;

import net.hypercubemc.beacon.api.events.BeaconPlayerDeathEvent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingPlayerMixin {
    @Inject(method = "onDeath", at = @At(value = "RETURN"))
    public void playerDeath(DamageSource source, CallbackInfo callbackInfo) {
        BeaconPlayerDeathEvent.fire(source, callbackInfo);
    }
}
