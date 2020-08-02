package net.hypercubemc.beacon;

import net.hypercubemc.beacon.BeaconPluginInitializer;
import net.hypercubemc.beacon.BeaconPluginInstance;
import net.hypercubemc.beacon.BeaconPluginState;
import static net.hypercubemc.beacon.AnsiCodes.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BeaconPluginManager {
    // TODO: Check if onEnable and onDisable methods exist, and the plugin name and version getter methods exist on the passed plugin
    public void registerPlugin(BeaconPluginInitializer beaconPluginInitializer) {
        String pluginName = beaconPluginInitializer.getPluginName();
        String pluginVersion = beaconPluginInitializer.getPluginVersion();
        BeaconPluginInstance beaconPluginInstance = new BeaconPluginInstance(pluginName, pluginVersion, BeaconPluginState.ENABLING);
        try {
            beaconPluginInitializer.onEnable(beaconPluginInstance);
        } catch (Exception error) {
            Logger log = LogManager.getLogger(pluginName);
            log.error(colorRed + "[" + pluginName + "]" + "An error occured while attempting to enable " + pluginName + " v" + pluginVersion + ", see the error below for details.");
            error.printStackTrace();
            beaconPluginInstance.setPluginState(BeaconPluginState.DISABLING);
            // TODO: Handle errors here too
            beaconPluginInitializer.onDisable(beaconPluginInstance);
            beaconPluginInstance.setPluginState(BeaconPluginState.DISABLED);
        }
    }
}
