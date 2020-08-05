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
    static List<Method> prePlayerJoinEventHandlerMethods = new ArrayList<>();
    static List<Method> postPlayerJoinEventHandlerMethods = new ArrayList<>();
    static List<Method> prePlayerLeaveEventHandlerMethods = new ArrayList<>();
    static List<Method> postPlayerLeaveEventHandlerMethods = new ArrayList<>();
    static List<Method> prePlayerDeathEventHandlerMethods = new ArrayList<>();
    static List<Method> postPlayerDeathEventHandlerMethods = new ArrayList<>();
    static List<Method> prePlayerBreakBlockEventHandlerMethods = new ArrayList<>();
    static List<Method> postPlayerBreakBlockEventHandlerMethods = new ArrayList<>();
    static List<Method> prePlayerPlaceBlockEventHandlerMethods = new ArrayList<>();
    static List<Method> postPlayerPlaceBlockEventHandlerMethods = new ArrayList<>();
    static List<Method> prePlayerInteractEntityEventHandlerMethods = new ArrayList<>();
    static List<Method> postPlayerInteractEntityEventHandlerMethods = new ArrayList<>();

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
            if (eventHandlerAnnotation.fireStage() == BeaconEventFireStage.PRE) {
                if (eventHandlerAnnotation.value() == BeaconPlayerJoinEvent.class) {
                    prePlayerJoinEventHandlerMethods.add(eventHandler);
                } else if (eventHandlerAnnotation.value() == BeaconPlayerLeaveEvent.class) {
                    prePlayerLeaveEventHandlerMethods.add(eventHandler);
                } else if (eventHandlerAnnotation.value() == BeaconPlayerDeathEvent.class) {
                    prePlayerDeathEventHandlerMethods.add(eventHandler);
                } else if (eventHandlerAnnotation.value() == BeaconPlayerBreakBlockEvent.class) {
                    prePlayerBreakBlockEventHandlerMethods.add(eventHandler);
                } else if (eventHandlerAnnotation.value() == BeaconPlayerPlaceBlockEvent.class) {
                    prePlayerPlaceBlockEventHandlerMethods.add(eventHandler);
                } else if (eventHandlerAnnotation.value() == BeaconPlayerInteractEntityEvent.class) {
                    prePlayerInteractEntityEventHandlerMethods.add(eventHandler);
                }
            } else if (eventHandlerAnnotation.fireStage() == BeaconEventFireStage.POST) {
                if (eventHandlerAnnotation.value() == BeaconPlayerJoinEvent.class) {
                    postPlayerJoinEventHandlerMethods.add(eventHandler);
                } else if (eventHandlerAnnotation.value() == BeaconPlayerLeaveEvent.class) {
                    postPlayerLeaveEventHandlerMethods.add(eventHandler);
                } else if (eventHandlerAnnotation.value() == BeaconPlayerDeathEvent.class) {
                    postPlayerDeathEventHandlerMethods.add(eventHandler);
                } else if (eventHandlerAnnotation.value() == BeaconPlayerBreakBlockEvent.class) {
                    postPlayerBreakBlockEventHandlerMethods.add(eventHandler);
                } else if (eventHandlerAnnotation.value() == BeaconPlayerPlaceBlockEvent.class) {
                    postPlayerPlaceBlockEventHandlerMethods.add(eventHandler);
                } else if (eventHandlerAnnotation.value() == BeaconPlayerInteractEntityEvent.class) {
                    postPlayerInteractEntityEventHandlerMethods.add(eventHandler);
                }
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
