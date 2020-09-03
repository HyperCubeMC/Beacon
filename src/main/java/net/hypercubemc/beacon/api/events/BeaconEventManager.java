package net.hypercubemc.beacon.api.events;

import net.hypercubemc.beacon.BeaconPluginInstance;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * This is the general manager for Beacon events
 */
public class BeaconEventManager {
    private static final Logger log = LogManager.getLogger("Beacon");

    final BeaconPluginInstance plugin;

    static List<Method> allEventHandlerMethods = new ArrayList<>();
    static List<MethodHandle> prePlayerJoinEventHandlerMethodHandles = new ArrayList<>();
    static List<MethodHandle> postPlayerJoinEventHandlerMethodHandles = new ArrayList<>();
    static List<MethodHandle> prePlayerLeaveEventHandlerMethodHandles = new ArrayList<>();
    static List<MethodHandle> postPlayerLeaveEventHandlerMethodHandles = new ArrayList<>();
    static List<MethodHandle> prePlayerDeathEventHandlerMethodHandles = new ArrayList<>();
    static List<MethodHandle> postPlayerDeathEventHandlerMethodHandles = new ArrayList<>();
    static List<MethodHandle> prePlayerBreakBlockEventHandlerMethodHandles = new ArrayList<>();
    static List<MethodHandle> postPlayerBreakBlockEventHandlerMethodHandles = new ArrayList<>();
    static List<MethodHandle> prePlayerPlaceBlockEventHandlerMethodHandles = new ArrayList<>();
    static List<MethodHandle> postPlayerPlaceBlockEventHandlerMethodHandles = new ArrayList<>();
    static List<MethodHandle> prePlayerInteractEntityEventHandlerMethodHandles = new ArrayList<>();
    static List<MethodHandle> postPlayerInteractEntityEventHandlerMethodHandles = new ArrayList<>();
    static List<MethodHandle> prePlayerAttackEntityEventHandlerMethodHandles = new ArrayList<>();
    static List<MethodHandle> postPlayerAttackEntityEventHandlerMethodHandles = new ArrayList<>();
    static List<MethodHandle> prePlayerChatEventHandlerMethodHandles = new ArrayList<>();
    static List<MethodHandle> postPlayerChatEventHandlerMethodHandles = new ArrayList<>();
    static List<MethodHandle> prePlayerMoveEventHandlerMethodHandles = new ArrayList<>();
    static List<MethodHandle> postPlayerMoveEventHandlerMethodHandles = new ArrayList<>();

    public BeaconEventManager(BeaconPluginInstance plugin) {
        this.plugin = plugin;
    }

    /**
     * Registers a BeaconEventListener
     * <p>
     * Use this to register your event listener class after implementing
     * BeaconEventListener and adding event handlers
     * </p>
     * @param listener - Your event listener class implementing BeaconEventListener
    */
    public void registerListener(BeaconEventListener listener) {
        try {
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
                        prePlayerJoinEventHandlerMethodHandles.add(MethodHandles.lookup().unreflect(eventHandler));
                    } else if (eventHandlerAnnotation.value() == BeaconPlayerLeaveEvent.class) {
                        prePlayerLeaveEventHandlerMethodHandles.add(MethodHandles.lookup().unreflect(eventHandler));
                    } else if (eventHandlerAnnotation.value() == BeaconPlayerDeathEvent.class) {
                        prePlayerDeathEventHandlerMethodHandles.add(MethodHandles.lookup().unreflect(eventHandler));
                    } else if (eventHandlerAnnotation.value() == BeaconPlayerBreakBlockEvent.class) {
                        prePlayerBreakBlockEventHandlerMethodHandles.add(MethodHandles.lookup().unreflect(eventHandler));
                    } else if (eventHandlerAnnotation.value() == BeaconPlayerPlaceBlockEvent.class) {
                        prePlayerPlaceBlockEventHandlerMethodHandles.add(MethodHandles.lookup().unreflect(eventHandler));
                    } else if (eventHandlerAnnotation.value() == BeaconPlayerInteractEntityEvent.class) {
                        prePlayerInteractEntityEventHandlerMethodHandles.add(MethodHandles.lookup().unreflect(eventHandler));
                    } else if (eventHandlerAnnotation.value() == BeaconPlayerChatEvent.class) {
                        prePlayerChatEventHandlerMethodHandles.add(MethodHandles.lookup().unreflect(eventHandler));
                    } else if (eventHandlerAnnotation.value() == BeaconPlayerMoveEvent.class) {
                        prePlayerMoveEventHandlerMethodHandles.add(MethodHandles.lookup().unreflect(eventHandler));
                    }
                } else if (eventHandlerAnnotation.fireStage() == BeaconEventFireStage.POST) {
                    if (eventHandlerAnnotation.value() == BeaconPlayerJoinEvent.class) {
                        postPlayerJoinEventHandlerMethodHandles.add(MethodHandles.lookup().unreflect(eventHandler));
                    } else if (eventHandlerAnnotation.value() == BeaconPlayerLeaveEvent.class) {
                        postPlayerLeaveEventHandlerMethodHandles.add(MethodHandles.lookup().unreflect(eventHandler));
                    } else if (eventHandlerAnnotation.value() == BeaconPlayerDeathEvent.class) {
                        postPlayerDeathEventHandlerMethodHandles.add(MethodHandles.lookup().unreflect(eventHandler));
                    } else if (eventHandlerAnnotation.value() == BeaconPlayerBreakBlockEvent.class) {
                        postPlayerBreakBlockEventHandlerMethodHandles.add(MethodHandles.lookup().unreflect(eventHandler));
                    } else if (eventHandlerAnnotation.value() == BeaconPlayerPlaceBlockEvent.class) {
                        postPlayerPlaceBlockEventHandlerMethodHandles.add(MethodHandles.lookup().unreflect(eventHandler));
                    } else if (eventHandlerAnnotation.value() == BeaconPlayerInteractEntityEvent.class) {
                        postPlayerInteractEntityEventHandlerMethodHandles.add(MethodHandles.lookup().unreflect(eventHandler));
                    } else if (eventHandlerAnnotation.value() == BeaconPlayerChatEvent.class) {
                        postPlayerChatEventHandlerMethodHandles.add(MethodHandles.lookup().unreflect(eventHandler));
                    } else if (eventHandlerAnnotation.value() == BeaconPlayerMoveEvent.class) {
                        postPlayerMoveEventHandlerMethodHandles.add(MethodHandles.lookup().unreflect(eventHandler));
                    }
                }
            }
        } catch (IllegalAccessException error) {
            log.error("An error occurred while attempting to register events for plugin " + plugin + ", see the error below for details.");
            error.printStackTrace();
        }
    }

    static void fire(final List<MethodHandle> methodHandles, final Object... arguments) {
        try {
            for (final MethodHandle methodHandle : methodHandles) {
                methodHandle.invokeWithArguments(arguments);
            }
        } catch (Throwable error) {
            log.error("An error occurred while attempting to fire the event " + StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE).getCallerClass().getSimpleName() + ", see the error below for details.");
            error.printStackTrace();
        }
    }
}
