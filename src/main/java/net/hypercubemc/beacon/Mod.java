package net.hypercubemc.beacon;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.fabricmc.loader.api.Version;
import net.fabricmc.loader.api.entrypoint.EntrypointContainer;
import net.hypercubemc.beacon.api.chat.BeaconChatManagerEventListener;
import net.hypercubemc.beacon.api.events.BeaconEventManager;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.ScoreboardObjective;

import net.hypercubemc.beacon.commands.BeaconCommand;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.yaml.YAMLConfigurationLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static net.minecraft.server.command.CommandManager.literal;

import com.sun.jna.*;
import com.sun.jna.platform.win32.WinDef.*;
import com.sun.jna.platform.win32.WinNT.HANDLE;
import org.yaml.snakeyaml.DumperOptions;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public class Mod implements ModInitializer {
	private static final Logger log = LogManager.getLogger("Beacon");

	private static MinecraftServer minecraftServer;

	private static ConfigurationNode configRootNode;

	final static Version version = FabricLoader.getInstance().getModContainer("beacon").get().getMetadata().getVersion();

	private static BeaconPluginInstance beaconInternalPlugin = new BeaconPluginInstance("Beacon", version, BeaconPluginState.ENABLED);

	public void setupAnsiOnWindows() {
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
		CommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> {
			MinecraftServer server = getMinecraftServer();
			registerTriggerCommandAliases(server);
		});
		ServerLifecycleEvents.END_DATA_PACK_RELOAD.register((server, serverResourceManager, success) -> {
			registerTriggerCommandAliases(server);
		});
	}

	public void registerTriggerCommandAliases(MinecraftServer server) {
		Scoreboard scoreboard = server.getScoreboard();
		for (ScoreboardObjective objective: scoreboard.getObjectives()) {
			if (objective.getCriterion().getName().equals("trigger")) {
				String commandName = objective.getName();
				server.getCommandManager().getDispatcher().register(literal(commandName)
						.then(literal("add")
								.executes(ctx -> server.getCommandManager().execute(ctx.getSource(), "trigger " + commandName + " add"))
								.then(CommandManager.argument("value", IntegerArgumentType.integer())
										.executes((ctx) -> {
											int integerArg = IntegerArgumentType.getInteger(ctx, "value");
											return server.getCommandManager().execute(ctx.getSource(), "trigger " + commandName + " add " + integerArg);
										})
								)
						)
						.then(literal("set")
								.executes(ctx -> server.getCommandManager().execute(ctx.getSource(), "trigger " + commandName + " set"))
								.then(CommandManager.argument("value", IntegerArgumentType.integer())
										.executes((ctx) -> {
											int integerArg = IntegerArgumentType.getInteger(ctx, "value");
											return server.getCommandManager().execute(ctx.getSource(), "trigger " + commandName + " set " + integerArg);
										})
								)
						)
						.executes(ctx -> server.getCommandManager().execute(ctx.getSource(), "trigger " + commandName))
				);
			}
		}
	}

	public void setupBetterLogging() {
		System.setProperty("log4j.configurationFile", "resources/log4j2.xml");
	}

	public void setupEventListeners() {
		new BeaconEventManager(beaconInternalPlugin).registerListener(new BeaconChatManagerEventListener(beaconInternalPlugin));
	}

	public void setupConfig() {
		final Path configDir = FabricLoader.getInstance().getConfigDir();
		final File configFile = configDir.resolve("beacon.yml").toFile();

		YAMLConfigurationLoader configLoader = YAMLConfigurationLoader.builder()
				.setFile(configFile)
				.setFlowStyle(DumperOptions.FlowStyle.BLOCK)
				.build();

		boolean isFirstLoad;
		if (!configFile.exists()) {
			isFirstLoad = true;
			try {
				configFile.createNewFile();
			} catch (IOException error) {
				log.error("Failed to create Beacon config file, see the error below for details.");
				error.printStackTrace();
			}
		} else {
			isFirstLoad = false;
		}

		try {
			configRootNode = configLoader.load();
		} catch (IOException error) {
			log.error("Failed to load Beacon config file, see the error below for details.");
			error.printStackTrace();
			log.warn("Falling back to in-memory config with defaults...");
			configRootNode = configLoader.createEmptyNode();
		}

		if (isFirstLoad) {
			configRootNode.getNode("op-beacon-dev-on-join").setValue(true);
			configRootNode.getNode("spawn-protection-op-bypass-level").setValue(1);
			configRootNode.getNode("add-trigger-command-aliases").setValue(true);
			configRootNode.getNode("enhanced-anti-cheat").setValue(true);
			try {
				if (!configRootNode.isVirtual()) {
					configLoader.save(configRootNode);
				}
			} catch (IOException error) {
				log.error("Failed to save Beacon config file, see the error below for details.");
				error.printStackTrace();
			}
		}
	}

	public static ConfigurationNode getConfig() {
		return configRootNode;
	}

	public static MinecraftServer getMinecraftServer() {
		return minecraftServer;
	}

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		try {
		    setupAnsiOnWindows();
			setupBetterLogging();
			setupConfig();
		    registerCommands();
			ServerLifecycleEvents.SERVER_STARTED.register(server -> {
				minecraftServer = server;
				if (configRootNode.getNode("add-trigger-command-aliases").getBoolean()) {
					setupTriggerCommandAliases();
				}
				setupEventListeners();
				log.info("Completely loaded Beacon v" + version.getFriendlyString() + " successfully!");
			});
			log.info("Initial load of Beacon v" + version.getFriendlyString() + " completed.");
			// Setup Beacon plugin loader
			for (EntrypointContainer<BeaconPluginInitializer> entrypointContainer : FabricLoader.getInstance().getEntrypointContainers("beacon:init", BeaconPluginInitializer.class)) {
				BeaconPluginInitializer pluginInitializer = entrypointContainer.getEntrypoint();
				ModContainer modContainer = entrypointContainer.getProvider();
				String pluginName = modContainer.getMetadata().getName();
				Version pluginVersion = modContainer.getMetadata().getVersion();
				BeaconPluginManager.registerPlugin(pluginInitializer, pluginName, pluginVersion);
			}
			ServerLifecycleEvents.SERVER_STOPPING.register(server -> {
				log.info("Disabling Beacon v" + version.getFriendlyString() + "...");
				log.info("Disabling plugins has not been implemented yet!");
				log.info("Disabled Beacon v" + version.getFriendlyString() + "!");
			});
		} catch (Exception error) {
			log.error("Failed to load Beacon v" + version.getFriendlyString() + ", see the error below for details. PLUGINS WILL NOT BE LOADED.");
			error.printStackTrace();
		}
	}
}
