package net.hypercubemc.beacon.api.events;

/**
 * This is how you add an event listener class
 * First, implement this class
 * Then, start adding event handlers by annotating methods with @BeaconEventHandle
 * Specify the event in the value of the annotation
 * When finished, in your main class or elsewhere, use {@link net.hypercubemc.beacon.api.events.BeaconEventManager#registerListener BeaconEventManager.registerPlugin(YourBeaconEventListener)} to register your event listener class and your event handlers
*/
public interface BeaconEventListener {

}
