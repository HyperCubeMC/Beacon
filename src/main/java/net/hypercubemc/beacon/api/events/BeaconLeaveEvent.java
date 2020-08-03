package net.hypercubemc.beacon.api.events;

import net.minecraft.network.ClientConnection;
import net.minecraft.server.network.ServerPlayerEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static net.hypercubemc.beacon.util.AnsiCodes.colorBrightRed;

public class BeaconLeaveEvent extends BeaconEvent {
    public static void fire(ServerPlayerEntity playerEntity, CallbackInfo callbackInfo) {
        final List<Method> eventHandlerMethods = BeaconEventManager.eventHandlerMethods;
        List<Method> targetEventHandlerMethods = new ArrayList<>();
        for (Method eventHandler : eventHandlerMethods) {
            Class<?>[] parameterTypes = eventHandler.getParameterTypes();
            if (parameterTypes[0].equals(BeaconJoinEvent.class)) {
                targetEventHandlerMethods.add(eventHandler);
            }
        }
        try {
            BeaconEventManager.fire(targetEventHandlerMethods, playerEntity, callbackInfo);
        } catch (InvocationTargetException | IllegalAccessException error) {
            Logger log = LogManager.getLogger("beacon");
            log.error(colorBrightRed + "[Beacon] An error occurred while attempting to fire the event BeaconLeaveEvent, see the error below for details.");
            error.printStackTrace();
        }
    }
}