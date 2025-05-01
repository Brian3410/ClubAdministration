package club.model;

/**
 * Interface for observers of event changes.
 * Part of the Observer pattern implementation.
 */
public interface EventObserver {
    
    /**
     * Called when an event is created, updated, or cancelled.
     * 
     * @param event The event that was changed
     * @param action The action that occurred
     */
    void onEventUpdate(Event event, EventAction action);
}