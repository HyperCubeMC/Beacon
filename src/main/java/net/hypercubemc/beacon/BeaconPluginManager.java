package net.hypercubemc.beacon;

import net.fabricmc.loader.api.Version;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BeaconPluginManager {
    static void registerPlugin(BeaconPluginInitializer pluginInitializer, String pluginName, Version pluginVersion) {
        BeaconPluginInstance beaconPluginInstance = new BeaconPluginInstance(pluginName, pluginVersion, BeaconPluginState.ENABLING);
        try {
            Logger log = LogManager.getLogger(pluginName);
            log.info("Enabling " + pluginName + " v" + pluginVersion.getFriendlyString());
            pluginInitializer.onEnable(beaconPluginInstance);
            beaconPluginInstance.setPluginState(BeaconPluginState.ENABLED);
            log.info("Enabled " + pluginName + " v" + pluginVersion.getFriendlyString() + " successfully!");
        } catch (Exception error) {
            Logger log = LogManager.getLogger(pluginName);
            log.error("An error occurred while attempting to enable " + pluginName + " v" + pluginVersion.getFriendlyString() + ", see the error below for details.");
            error.printStackTrace();
            beaconPluginInstance.setPluginState(BeaconPluginState.DISABLING);
            // TODO: Possibly handle errors here too
            pluginInitializer.onDisable(beaconPluginInstance);
            beaconPluginInstance.setPluginState(BeaconPluginState.DISABLED);
        }
    }
}
