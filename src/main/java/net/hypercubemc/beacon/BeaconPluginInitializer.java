package net.hypercubemc.beacon;

/**
 * Implement this to make your plugin initializer class
 * Then specify your implemented class as your plugin initializer by adding it as an entrypoint for "beacon:init" in your fabric.mod.json
 */
public interface BeaconPluginInitializer {
    void onEnable(BeaconPluginInstance beaconPluginInstance);
    void onDisable(BeaconPluginInstance beaconPluginInstance);
}
