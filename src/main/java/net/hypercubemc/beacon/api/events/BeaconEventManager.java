package net.hypercubemc.beacon.api.events;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class BeaconEventManager {
    static List<Method> eventHandlerMethods = new ArrayList<Method>();

    public static void registerListeners(BeaconEventListener listener) {
        Method[] allMethods = listener.getClass().getMethods();
        for (Method method : allMethods) {
            if (method.isAnnotationPresent(BeaconEventHandler.class)) {
                eventHandlerMethods.add(method);
            }
        }
    }

    public static void callEvent(BeaconEvent event) {
        new Thread() {
            public void run() {
                call(event);
            }
        }.start();
    }

    private static void call(BeaconEvent event) {
        for (Method eventHandler : eventHandlerMethods) {
            Class<?>[] parameterTypes = eventHandler.getParameterTypes();
//            if (parameterTypes[0].equals(BeaconJoinEvent.class)) {
                try {
                    eventHandler.invoke(eventHandler.getClass().newInstance(), event.getClass().getConstructor());
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
//            }
        }
    }
}
