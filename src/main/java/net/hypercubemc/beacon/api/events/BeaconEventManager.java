package net.hypercubemc.beacon.api.events;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * This is the general manager for Beacon events
 */
public class BeaconEventManager {
    private static final Logger log = LogManager.getLogger("Beacon");

    static List<Method> allEventHandlerMethods = new ArrayList<>();
    static List<Method> playerJoinEventHandlerMethods = new ArrayList<>();
    static List<Method> playerLeaveEventHandlerMethods = new ArrayList<>();
    static List<Method> playerDeathEventHandlerMethods = new ArrayList<>();

    /**
     * Registers a BeaconEventListener
     * <p>
     * Use this to register your event listener class after implementing
     * BeaconEventListener and adding event handlers
     * </p>
     * @param listener - Your event listener class implementing BeaconEventListener
    */
    public static void registerListener(BeaconEventListener listener) {
        Method[] allMethods = listener.getClass().getMethods();
        for (Method method : allMethods) {
            if (method.isAnnotationPresent(BeaconEventHandler.class)) {
                allEventHandlerMethods.add(method);
            }
        }
        for (Method eventHandler : allEventHandlerMethods) {
            BeaconEventHandler eventHandlerAnnotation = eventHandler.getAnnotation(BeaconEventHandler.class);
            if (eventHandlerAnnotation.value() == BeaconPlayerJoinEvent.class) {
                playerJoinEventHandlerMethods.add(eventHandler);
            } else if (eventHandlerAnnotation.value() == BeaconPlayerLeaveEvent.class) {
                playerLeaveEventHandlerMethods.add(eventHandler);
            } else if (eventHandlerAnnotation.value() == BeaconPlayerDeathEvent.class) {
                playerDeathEventHandlerMethods.add(eventHandler);
            }
        }
    }

    static void fire(final List<Method> methods, final Object... arguments) {
        try {
            for (final Method method : methods) {
                method.invoke(null, arguments);
            }
        } catch (InvocationTargetException | IllegalAccessException error) {
            log.error("An error occurred while attempting to fire the event " + StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE).getCallerClass().getSimpleName() + ", see the error below for details.");
            error.printStackTrace();
        }
    }
}
