package net.hypercubemc.beacon.api.events;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import net.hypercubemc.beacon.api.events.BeaconEventPriority;

/**
 * This is an annotation to signify a method as being a beacon event handler method
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface BeaconEventHandler {

    /**
     * Define the priority of the event.
     * <p>
     * First priority to the last priority executed:
     * <ol>
     * <li>LOW
     * <li>NORMAL
     * <li>IMPORTANT
     * </ol>
     *
     * @return The priority of the event
     */
    BeaconEventPriority priority() default BeaconEventPriority.NORMAL;

    /**
     * Controls whether the event handler ignores a cancelled event.
     * <p>
     * If ignoreCancelledEvent is true and the event is cancelled, the method is
     * not called. Otherwise, if false, the method is called even if the event gets cancelled.
     *
     * @return Whether or not cancelled events should be ignored
     */
    boolean ignoreCancelledEvent() default false;
}