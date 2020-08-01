package net.hypercubemc.beacon;

import net.hypercubemc.beacon.BeaconPluginInitializer;
import net.hypercubemc.beacon.BeaconPluginInstance;

public class BeaconPluginManager {
    // TODO: Check if onEnable and onDisable methods exist, and the plugin name and version getter methods exist on the passed plugin
    public registerPlugin(BeaconPluginInitializer beaconPluginInitializer) {
        BeaconPluginInstance beaconPluginInstance = new BeaconPluginInstance(beaconPluginInitializer.getPluginName(), beaconPluginInitializer.getPluginVersion());
        beaconPluginInitializer.onEnable(beaconPluginInstance);
    }
}
