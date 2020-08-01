package net.hypercubemc.beacon;

import net.hypercubemc.beacon.BeaconPluginState;

public class BeaconPluginInstance {
    private String pluginName;
    private String pluginVersion;
    private BeaconPluginState pluginState;

    public BeaconPluginInstance(String pluginName, String pluginVersion, BeaconPluginState pluginState) {
        this.pluginName = pluginName;
        this.pluginVersion = pluginVersion;
        this.pluginState = pluginState;
    }

    public String getPluginName() {
        return pluginName;
    }

    public String getPluginVersion() {
        return pluginVersion;
    }

    public BeaconPluginState getPluginState() {
        return pluginState;
    }

    void setPluginState(BeaconPluginState pluginState) {
        this.pluginState = pluginState;
    }
}
