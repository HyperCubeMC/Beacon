package net.hypercubemc.beacon.mixin;

import net.hypercubemc.beacon.api.events.BeaconPlayerBreakBlockEvent;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.network.ServerPlayerInteractionManager;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerPlayerInteractionManager.class)
public abstract class ServerPlayerInteractionManagerMixin {
    @Shadow
    public ServerPlayerEntity player;

    @Shadow
    public ServerWorld world;

    @Inject(method = "tryBreakBlock",
            at = @At(value = "INVOKE",
            target = "Lnet/minecraft/block/Block;onBreak(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;Lnet/minecraft/entity/player/PlayerEntity;)V"),
            cancellable = true)
    private void preBreakBlock(BlockPos pos, CallbackInfoReturnable<Boolean> cir, BlockState blockState, BlockEntity entity, Block block) {
        BeaconPlayerBreakBlockEvent.firePre(pos, cir, blockState, entity, block, player, world);
    }

    @Inject(method = "tryBreakBlock",
            at = @At(value = "INVOKE",
            target = "Lnet/minecraft/block/Block;afterBreak(Lnet/minecraft/world/World;Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;Lnet/minecraft/block/entity/BlockEntity;Lnet/minecraft/item/ItemStack;)V")
    )
    private void postBreakBlock(BlockPos pos, CallbackInfoReturnable<Boolean> cir, BlockState blockState, BlockEntity entity, Block block) {
        BeaconPlayerBreakBlockEvent.firePost(pos, blockState, entity, block, player, world);
    }
}
