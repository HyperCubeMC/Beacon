package net.hypercubemc.beacon;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.ScoreboardObjective;

import net.hypercubemc.beacon.commands.BeaconCommand;
import net.minecraft.server.command.CommandManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static net.hypercubemc.beacon.util.AnsiCodes.*;
import static net.minecraft.server.command.CommandManager.literal;

import com.sun.jna.*;
import com.sun.jna.platform.win32.WinDef.*;
import com.sun.jna.platform.win32.WinNT.HANDLE;

public class Mod implements ModInitializer {
	private static final Logger log = LogManager.getLogger("Beacon");

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
			BeaconCommand beaconCommand = new BeaconCommand(this);
			beaconCommand.register(dispatcher);
		});
	}

	public void setupTriggerCommandAliases() {
		ServerLifecycleEvents.SERVER_STARTED.register(server -> {
			Scoreboard scoreboard = server.getScoreboard();
			for (ScoreboardObjective objective: scoreboard.getObjectives()) {
				if (objective.getCriterion().getName().equals("trigger")) {
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

	public void setupBetterLogging() {
		System.setProperty("log4j.configurationFile", "resources/log4j2.xml");
	}

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		String version = FabricLoader.getInstance().getModContainer("beacon").get().getMetadata().getVersion().getFriendlyString();

		try {
		    setupAnsiWindows();
		    registerCommands();
		    setupTriggerCommandAliases();
		    setupBetterLogging();
			log.info("Loaded Beacon v" + version + " successfully!");
		} catch (Exception error) {
			log.error("Failed to load Beacon v" + version + ", see the error below for details.");
			error.printStackTrace();
		}
	}
}
