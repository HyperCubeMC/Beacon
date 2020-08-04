package net.hypercubemc.beacon.api.events;

/**
 * This is how you add an event listener class
 * <br>
 * First, implement this class
 * <br>
 * Then, start adding event handlers by annotating methods with {@link net.hypercubemc.beacon.api.events.BeaconEventHandler @BeaconEventHandler}
 * <br>
 * Specify the event in the value of the annotation, with .class on the end
 * <br>
 * Once your event listener class is completed, in your main class or elsewhere, use {@link net.hypercubemc.beacon.api.events.BeaconEventManager#registerListener BeaconEventManager.registerPlugin(YourBeaconEventListenerClass)} to register your event listener class and your event handlers
 */
public interface BeaconEventListener {

}
