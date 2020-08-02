package net.hypercubemc.beacon;

public interface BeaconPluginInitializer {
    private String pluginName;
    private String pluginVersion;

    public default String getPluginName() {
        return pluginName;
    }
    
    public default String getPluginVersion() {
        return pluginVersion();
    }

    public void onEnable(BeaconPluginInstance beaconPluginInstance);
    public void onDisable(BeaconPluginInstance beaconPluginInstance);
}
