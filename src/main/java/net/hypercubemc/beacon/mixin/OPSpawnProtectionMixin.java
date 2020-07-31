package net.hypercubemc.beacon.mixin;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.GameProfileRepository;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import com.mojang.datafixers.DataFixer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.resource.ResourcePackManager;
import net.minecraft.resource.ResourcePackProfile;
import net.minecraft.resource.ServerResourceManager;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.WorldGenerationProgressListenerFactory;
import net.minecraft.server.dedicated.DedicatedPlayerManager;
import net.minecraft.server.dedicated.MinecraftDedicatedServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.UserCache;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.RegistryTracker;
import net.minecraft.world.SaveProperties;
import net.minecraft.world.level.storage.LevelStorage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.net.Proxy;

@Mixin(MinecraftDedicatedServer.class)
public abstract class OPSpawnProtectionMixin extends MinecraftServer {
    public OPSpawnProtectionMixin(Thread thread, RegistryTracker.Modifiable modifiable, LevelStorage.Session session, SaveProperties saveProperties, ResourcePackManager<ResourcePackProfile> resourcePackManager, Proxy proxy, DataFixer dataFixer, ServerResourceManager serverResourceManager, MinecraftSessionService minecraftSessionService, GameProfileRepository gameProfileRepository, UserCache userCache, WorldGenerationProgressListenerFactory worldGenerationProgressListenerFactory) {
        super(thread, modifiable, session, saveProperties, resourcePackManager, proxy, dataFixer, serverResourceManager, minecraftSessionService, gameProfileRepository, userCache, worldGenerationProgressListenerFactory);
    }

    @Redirect(
            method = "isSpawnProtected",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/server/dedicated/DedicatedPlayerManager;isOperator(Lcom/mojang/authlib/GameProfile;)Z"
            )
    )
    public boolean isSpawnProtected(DedicatedPlayerManager redirectPlayer, GameProfile redirectProfile, ServerWorld serverWorld, BlockPos pos, PlayerEntity player) {
        return this.getPermissionLevel(player.getGameProfile()) >= 3;
    }
}
