package net.hypercubemc.beacon.api.events;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.lang.reflect.Method;
import java.util.List;

public class BeaconPlayerBreakBlockEvent extends BeaconEvent {
    public static void firePre(BlockPos pos, CallbackInfoReturnable<Boolean> cir, BlockState blockState, BlockEntity entity, Block block, ServerPlayerEntity player, ServerWorld world) {
        BeaconEventManager.fire(BeaconEventManager.prePlayerBreakBlockEventHandlerMethodHandles, pos, cir, blockState, entity, block, player, world);
    }
    public static void firePost(BlockPos pos, BlockState blockState, BlockEntity entity, Block block, ServerPlayerEntity player, ServerWorld world) {
        BeaconEventManager.fire(BeaconEventManager.postPlayerBreakBlockEventHandlerMethodHandles, pos, blockState, entity, block, player, world);
    }
}
