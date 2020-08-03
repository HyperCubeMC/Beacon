package net.hypercubemc.beacon;

import static net.hypercubemc.beacon.util.AnsiCodes.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BeaconPluginManager {
    // TODO: Make sure onEnable and onDisable methods exist, and the plugin name and version getter methods exist on the passed plugin
    public static void registerPlugin(BeaconPluginInitializer beaconPluginInitializer) {
        String pluginName = beaconPluginInitializer.getPluginName();
        String pluginVersion = beaconPluginInitializer.getPluginVersion();
        BeaconPluginInstance beaconPluginInstance = new BeaconPluginInstance(pluginName, pluginVersion, BeaconPluginState.ENABLING);
        try {
            Logger log = LogManager.getLogger(pluginName);
            log.info(colorBrightBlue + "[" + pluginName + "] " + "Enabling " + pluginName + " v" + pluginVersion);
            beaconPluginInitializer.onEnable(beaconPluginInstance);
            beaconPluginInstance.setPluginState(BeaconPluginState.ENABLED);
            log.info(colorBrightBlue + "[" + pluginName + "] " + "Enabled " + pluginName + " v" + pluginVersion + " successfully!");
        } catch (Exception error) {
            Logger log = LogManager.getLogger(pluginName);
            log.error(colorRed + "[" + pluginName + "] " + "An error occurred while attempting to enable " + pluginName + " v" + pluginVersion + ", see the error below for details.");
            error.printStackTrace();
            beaconPluginInstance.setPluginState(BeaconPluginState.DISABLING);
            // TODO: Possibly handle errors here too
            beaconPluginInitializer.onDisable(beaconPluginInstance);
            beaconPluginInstance.setPluginState(BeaconPluginState.DISABLED);
        }
    }
}
