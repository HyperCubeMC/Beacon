package net.hypercubemc.beacon;

public interface BeaconPluginInitializer {
    String pluginName;
    String pluginVersion;

    public default String getPluginName() {
        return pluginName;
    }
    
    public default String getPluginVersion() {
        return pluginVersion();
    }

    public void onEnable();
    public void onDisable();
}
