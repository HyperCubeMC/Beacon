package net.hypercubemc.beacon.api.events;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class BeaconEventManager {
    static List<Method> allEventHandlerMethods = new ArrayList<>();
    static List<Method> joinEventHandlerMethods = new ArrayList<>();
    static List<Method> leaveEventHandlerMethods = new ArrayList<>();

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
                joinEventHandlerMethods.add(eventHandler);
            } else if (eventHandlerAnnotation.value() == BeaconLeaveEvent.class) {
                leaveEventHandlerMethods.add(eventHandler);
            }
        }
    }

    static void fire(final List<Method> methods, final Object... arguments) throws InvocationTargetException, IllegalAccessException {
        for (final Method method : methods) {
            method.invoke(null, arguments);
        }
    }
}
