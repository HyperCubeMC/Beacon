package net.hypercubemc.beacon.api.chat;

/**
 * Contains useful methods for managing the chat
 */
public class BeaconChatManager {
    private static volatile BeaconChatManager beaconChatManager;

    private BeaconChatState chatState = BeaconChatState.UNMUTED;

    private BeaconChatManager() {
        if (beaconChatManager != null){
            throw new RuntimeException("Use getInstance() method to get the single instance of this class.");
        }
    }

    /**
     * Used to get the sole instance of the BeaconChatManager
     * @return ChatManager
     */
    public static BeaconChatManager getInstance() {
        if (beaconChatManager == null) {
            synchronized (BeaconChatManager.class) {
                if (beaconChatManager == null) beaconChatManager = new BeaconChatManager();
            }
        }

        return beaconChatManager;
    }

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
