package net.hypercubemc.beacon.api.chat;

import net.hypercubemc.beacon.BeaconPluginInstance;

/**
 * Contains useful methods for managing the chat
 */
public class BeaconChatManager {
    BeaconPluginInstance plugin;
    public BeaconChatManager(BeaconPluginInstance plugin) {
        this.plugin = plugin;
    }
    private BeaconChatState chatState = BeaconChatState.UNMUTED;

    /**
     * Used to get the current chat state, which is a BeaconChatState that can be UNMUTED, MUTED, or MUTED_WITH_OP_BYPASS
     * @return BeaconChatState that can be UNMUTED, MUTED, or MUTED_WITH_OP_BYPASS
     */
    public BeaconChatState getChatState() {
        return this.chatState;
    }

    /**
     * Used to set the chat state, which is a BeaconChatState that can be UNMUTED, MUTED, or MUTED_WITH_OP_BYPASS
     * @param chatState - A BeaconChatState
     */
    public void setChatState(BeaconChatState chatState) {
        this.chatState = chatState;
    }
}
