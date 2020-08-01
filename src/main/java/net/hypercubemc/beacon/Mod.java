package net.hypercubemc.beacon;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.tree.CommandNode;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.server.ServerStartCallback;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.ScoreboardObjective;

import net.hypercubemc.beacon.commands.BeaconCommand;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Formatting;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static net.hypercubemc.beacon.AnsiCodes.*;
import static net.minecraft.server.command.CommandManager.literal;

import com.sun.jna.*;
import com.sun.jna.platform.win32.WinDef.*;
import com.sun.jna.platform.win32.WinNT.HANDLE;

public class Mod implements ModInitializer {
	public void setupAnsiWindows() {
		if (System.getProperty("os.name").startsWith("Windows")) {
			// Set output mode to handle virtual terminal sequences
			Function GetStdHandleFunc = Function.getFunction("kernel32", "GetStdHandle");
			DWORD STD_OUTPUT_HANDLE = new DWORD(-11);
			HANDLE hOut = (HANDLE) GetStdHandleFunc.invoke(HANDLE.class, new Object[]{STD_OUTPUT_HANDLE});

			DWORDByReference p_dwMode = new DWORDByReference(new DWORD(0));
			Function GetConsoleModeFunc = Function.getFunction("kernel32", "GetConsoleMode");
			GetConsoleModeFunc.invoke(BOOL.class, new Object[]{hOut, p_dwMode});

			int ENABLE_VIRTUAL_TERMINAL_PROCESSING = 4;
			DWORD dwMode = p_dwMode.getValue();
			dwMode.setValue(dwMode.intValue() | ENABLE_VIRTUAL_TERMINAL_PROCESSING);
			Function SetConsoleModeFunc = Function.getFunction("kernel32", "SetConsoleMode");
			SetConsoleModeFunc.invoke(BOOL.class, new Object[]{hOut, dwMode});
		}
	}

	public void registerCommands() {
		CommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> {
			BeaconCommand beaconCommand = new beaconCommand(this);
			beaconCommand.register(dispatcher);
		});
	}

        public void setupTriggerCommandAliases() {
		Logger log = LogManager.getLogger("beacon");
		ServerStartCallback.EVENT.register(server -> {
//			for (String usage : server.getCommandManager().getDispatcher().getAllUsage(server.getCommandManager().getDispatcher().getRoot().getChild("trigger"), server.getCommandSource(), false)) {
//				log.info(colorGreen + usage + formatReset);
//			}
//			server.getCommandManager().getDispatcher().getSmartUsage(server.getCommandManager().getDispatcher().getRoot().getChild("trigger"), server.getCommandSource()).forEach((k, v) -> {
//				log.info(colorBlue + k + ": " + v + formatReset);
//			});

			Scoreboard scoreboard = server.getScoreboard();
			for (ScoreboardObjective objective: scoreboard.getObjectives()) {
				if (objective.getCriterion().getName().equals("trigger")) {
//					log.info("Scoreboard objective: " + objective.getName());
//					log.info("Scoreboard objective is enabled for snoopy: " + scoreboard.playerHasObjective("Justsnoopy30", objective));
					String commandName = objective.getName();
					server.getCommandManager().getDispatcher().register(literal(commandName)
							.then(literal("add")
									.executes(ctx -> {
										return server.getCommandManager().execute(ctx.getSource(), "trigger " + commandName + " add");
									})
									.then(CommandManager.argument("value", IntegerArgumentType.integer())
											.executes((ctx) -> {
												int integerArg = IntegerArgumentType.getInteger(ctx, "value");
												return server.getCommandManager().execute(ctx.getSource(), "trigger " + commandName + " add " + integerArg);
											})
									)
							)
							.then(literal("set")
									.executes(ctx -> {
										return server.getCommandManager().execute(ctx.getSource(), "trigger " + commandName + " set");
									})
									.then(CommandManager.argument("value", IntegerArgumentType.integer())
											.executes((ctx) -> {
												int integerArg = IntegerArgumentType.getInteger(ctx, "value");
												return server.getCommandManager().execute(ctx.getSource(), "trigger " + commandName + " set " + integerArg);
											})
									)
							)
							.executes(ctx -> {
								return server.getCommandManager().execute(ctx.getSource(), "trigger " + commandName);
							})
					);
				}
			}
		});
        }

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
                
                Logger log = LogManager.getLogger("beacon");
		String version = FabricLoader.getInstance().getModContainer("beacon").get().getMetadata().getVersion().getFriendlyString();

                try {
		    setupAnsiWindows();
		    registerCommands();
		    setupTriggerCommandAliases();
                } catch (Exception error) {
                    log.error(colorRed + "[Beacon] Failed to load Beacon v" + version + ", see the error below for details.");
                    error.printStackTrace();
                }
		log.info(colorBlue + "[Beacon] Loaded Beacon v" + version + "successfully!");
	}
}
