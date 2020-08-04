package net.hypercubemc.beacon.api.events;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static net.hypercubemc.beacon.util.AnsiCodes.colorBrightRed;

public class BeaconEventManager {
    static List<Method> allEventHandlerMethods = new ArrayList<>();
    static List<Method> playerJoinEventHandlerMethods = new ArrayList<>();
    static List<Method> playerLeaveEventHandlerMethods = new ArrayList<>();

    public static void registerListener(BeaconEventListener listener) {
        Method[] allMethods = listener.getClass().getMethods();
        for (Method method : allMethods) {
            if (method.isAnnotationPresent(BeaconEventHandler.class)) {
                allEventHandlerMethods.add(method);
            }
        }
        for (Method eventHandler : allEventHandlerMethods) {
            BeaconEventHandler eventHandlerAnnotation = eventHandler.getAnnotation(BeaconEventHandler.class);
            if (eventHandlerAnnotation.value() == BeaconJoinEvent.class) {
                playerJoinEventHandlerMethods.add(eventHandler);
            } else if (eventHandlerAnnotation.value() == BeaconLeaveEvent.class) {
                playerLeaveEventHandlerMethods.add(eventHandler);
            }
        }
    }

    static void fire(final List<Method> methods, final Object... arguments) {
        try {
            for (final Method method : methods) {
                method.invoke(null, arguments);
            }
        } catch (InvocationTargetException | IllegalAccessException error) {
            Logger log = LogManager.getLogger("beacon");
            log.error(colorBrightRed + "[Beacon] An error occurred while attempting to fire the event " + StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE).getCallerClass().getSimpleName() + ", see the error below for details.");
            error.printStackTrace();
        }
    }
}
