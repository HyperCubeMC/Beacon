package net.hypercubemc.beacon.api.events;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class BeaconEventManager {
    static List<Method> eventHandlerMethods = new ArrayList<>();

    public static void registerListener(BeaconEventListener listener) {
        Method[] allMethods = listener.getClass().getMethods();
        for (Method method : allMethods) {
            if (method.isAnnotationPresent(BeaconEventHandler.class)) {
                eventHandlerMethods.add(method);
            }
        }
    }

    static void fire(final List<Method> methods, final Object... arguments) throws InvocationTargetException, IllegalAccessException {
        for (final Method method : methods) {
            method.invoke(null, arguments);
        }
    }
}
