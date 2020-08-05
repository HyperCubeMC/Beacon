package net.hypercubemc.beacon.api.events;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.lang.reflect.Method;
import java.util.List;

public class BeaconPlayerBreakBlockEvent extends BeaconEvent {
    public static void firePre(BlockPos pos, CallbackInfoReturnable<Boolean> cir, BlockState blockState, BlockEntity entity, Block block, PlayerEntity player, ServerWorld world) {
        final List<Method> prePlayerBreakBlockEventHandlerMethods = BeaconEventManager.prePlayerBreakBlockEventHandlerMethods;
        BeaconEventManager.fire(prePlayerBreakBlockEventHandlerMethods, pos, cir, blockState, entity, block, player, world);
    }
    public static void firePost(BlockPos pos, BlockState blockState, BlockEntity entity, Block block, PlayerEntity player, ServerWorld world) {
        final List<Method> postPlayerBreakBlockEventHandlerMethods = BeaconEventManager.postPlayerBreakBlockEventHandlerMethods;
        BeaconEventManager.fire(postPlayerBreakBlockEventHandlerMethods, pos, blockState, entity, block, player, world);
    }
}
