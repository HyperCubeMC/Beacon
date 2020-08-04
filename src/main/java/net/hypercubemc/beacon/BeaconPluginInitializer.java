package net.hypercubemc.beacon;

/**
 * Implement this to make your plugin initializer class
 * Then use BeaconPluginManager.registerPlugin(YourBeaconPluginInitializer) in your mod class or elsewhere to register it 
 */
public interface BeaconPluginInitializer {
    String getPluginName();
    String getPluginVersion();

    void onEnable(BeaconPluginInstance beaconPluginInstance);
    void onDisable(BeaconPluginInstance beaconPluginInstance);
}
