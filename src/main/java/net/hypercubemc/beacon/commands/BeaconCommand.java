package net.hypercubemc.beacon.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Formatting;
import net.hypercubemc.beacon.Mod;

public class BeaconCommand {
    private Mod mod;

    public BeaconCommand(Mod mod) {
      this.mod = mod;
    }

    public void register(CommandDispatcher<ServerCommandSource> commandDispatcher) {
        LiteralCommandNode<ServerCommandSource> node = registerMain(commandDispatcher); // Registers main command
    }

    public LiteralCommandNode<ServerCommandSource> registerMain(CommandDispatcher<ServerCommandSource> commandDispatcher) {
        return commandDispatcher.register(CommandManager.literal("beacon")
                .then(CommandManager.literal("version")
                        // The command to be executed if the command "beacon" is entered with the argument "version"
                        .executes(this::BeaconVersion))
                // The command "beacon" to execute if there are no arguments.
                .executes(ctx -> {
                    ServerCommandSource source = ctx.getSource();
                    source.sendFeedback(new LiteralText("Beacon Help:").formatted(Formatting.BLUE), false);
                    source.sendFeedback(new LiteralText("Arguments: version").formatted(Formatting.BLUE), false);
                    return Command.SINGLE_SUCCESS;
                })
        );
    }

    public int BeaconVersion(CommandContext<ServerCommandSource> ctx) throws CommandSyntaxException {
        ServerCommandSource source = ctx.getSource();
        String version = FabricLoader.getInstance().getModContainer("beacon").get().getMetadata().getVersion().getFriendlyString();
        source.sendFeedback(new LiteralText("This server is running Justsnoopy30's Beacon Server Plugin API v" + version).formatted(Formatting.GREEN), false);
        return Command.SINGLE_SUCCESS;
    }
}
