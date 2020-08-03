package net.hypercubemc.beacon.api.events;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class BeaconEventManager {
    static List<Method> eventHandlerMethods = new ArrayList<Method>();
    
    public static void registerListener(BeaconEventListener listener) {
        Method[] allMethods = listener.getClass().getMethods();
        for (Method method : allMethods) {
            if (method.isAnnotationPresent(BeaconEventHandler.class)) {
                eventHandlerMethods.add(method);
            }
        }
    }

    public static void callEvent(BeaconEvent event) {
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
