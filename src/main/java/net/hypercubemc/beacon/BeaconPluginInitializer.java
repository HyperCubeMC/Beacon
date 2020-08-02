package net.hypercubemc.beacon;

public interface BeaconPluginInitializer {
    String getPluginName();
    String getPluginVersion();

    void onEnable(BeaconPluginInstance beaconPluginInstance);
    void onDisable(BeaconPluginInstance beaconPluginInstance);
}
