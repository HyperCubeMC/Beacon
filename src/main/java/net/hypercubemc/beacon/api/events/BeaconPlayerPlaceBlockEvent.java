package net.hypercubemc.beacon.api.events;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.c2s.play.PlayerInteractBlockC2SPacket;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.lang.reflect.Method;
import java.util.List;

public class BeaconPlayerPlaceBlockEvent extends BeaconEvent {
    public static void firePre(PlayerInteractBlockC2SPacket packet, CallbackInfo callbackInfo, PlayerEntity player) {
        final List<Method> prePlayerPlaceBlockEventHandlerMethods = BeaconEventManager.prePlayerPlaceBlockEventHandlerMethods;
        BeaconEventManager.fire(prePlayerPlaceBlockEventHandlerMethods, packet, callbackInfo, player);
    }
    public static void firePost(PlayerInteractBlockC2SPacket packet, PlayerEntity player) {
        final List<Method> postPlayerPlaceBlockEventHandlerMethods = BeaconEventManager.postPlayerPlaceBlockEventHandlerMethods;
        BeaconEventManager.fire(postPlayerPlaceBlockEventHandlerMethods, packet, player);
    }
}
