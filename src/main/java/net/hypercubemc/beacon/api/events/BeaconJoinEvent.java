package net.hypercubemc.beacon.api.events;

import net.minecraft.network.ClientConnection;
import net.minecraft.server.network.ServerPlayerEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import static net.hypercubemc.beacon.util.AnsiCodes.colorBrightRed;

public class BeaconJoinEvent extends BeaconEvent {
    public static void fire(ClientConnection clientConnection, ServerPlayerEntity playerEntity, CallbackInfo callbackInfo) {
        final List<Method> leaveEventHandlerMethods = BeaconEventManager.leaveEventHandlerMethods;
        try {
            BeaconEventManager.fire(leaveEventHandlerMethods, clientConnection, playerEntity, callbackInfo);
        } catch (InvocationTargetException | IllegalAccessException error) {
            Logger log = LogManager.getLogger("beacon");
            log.error(colorBrightRed + "[Beacon] An error occurred while attempting to fire the event BeaconJoinEvent, see the error below for details.");
            error.printStackTrace();
        }
    }
}
