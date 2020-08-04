package net.hypercubemc.beacon.api.events;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This is an annotation to signify a method as being a beacon event handler method
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface BeaconEventHandler {
    /**
     * Define the class of the event that the event handler is for
     * Example: BeaconJoinEvent.class
     * @return The class of the event that the event handler is for
     */
    Class<? extends BeaconEvent> value();

    /**
     * Define the point at which the event handler is called, PRE or POST main method code
     * <p>
     * It can be:
     * <ol>
     * <li>PRE</li>
     * <li>POST</li>
     * </ol>
     * </p>
     * @deprecated Not implemented yet
     * @return The point at which the event handler is called, PRE or POST main method code
     */
    @Deprecated
    BeaconEventFireStage fireStage() default BeaconEventFireStage.POST;

    /**
     * Controls whether the event handler ignores a cancelled event.
     * <p>
     * If ignoreCancelledEvent is true and the event is cancelled, the method is
     * not called. Otherwise, if false, the method is called even if the event gets cancelled.
     * </p>
     * @deprecated Not implemented yet
     * @return Whether or not cancelled events should be ignored
     */
    @Deprecated
    boolean ignoreCancelledEvent() default false;
}