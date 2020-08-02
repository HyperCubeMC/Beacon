package net.hypercubemc.beacon;

public interface BeaconPluginInitializer {
    public String getPluginName();
    
    public String getPluginVersion();

    public void onEnable(BeaconPluginInstance beaconPluginInstance);
    public void onDisable(BeaconPluginInstance beaconPluginInstance);
}
