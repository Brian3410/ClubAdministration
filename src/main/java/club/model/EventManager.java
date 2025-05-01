package club.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Manages club events, registrations, and notifications.
 * Implements the Observer pattern for event notifications.
 */
public class EventManager {
    private List<Event> events;
    private Map<Event, List<Member>> eventRegistrations;
    private List<EventObserver> observers;
    
    public EventManager() {
        this.events = new ArrayList<>();
        this.eventRegistrations = new HashMap<>();
        this.observers = new ArrayList<>();
    }
    
    /**
     * Adds a new event to the system.
     * 
     * @param event The event to add
     */
    public void addEvent(Event event) {
        if (event == null) {
            throw new IllegalArgumentException("Event cannot be null");
        }
        
        events.add(event);
        eventRegistrations.put(event, new ArrayList<>());
        
        // Notify observers about the new event
        notifyObservers(event, EventAction.CREATED);
    }
    
    /**
     * Registers a member for an event.
     * 
     * @param event The event to register for
     * @param member The member registering
     */
    public void registerMemberForEvent(Event event, Member member) {
        if (event == null || member == null) {
            throw new IllegalArgumentException("Event and member cannot be null");
        }
        
        if (!events.contains(event)) {
            throw new IllegalArgumentException("Event not found in system");
        }
        
        List<Member> registrations = eventRegistrations.get(event);
        if (registrations.contains(member)) {
            throw new IllegalStateException("Member already registered for this event");
        }
        
        registrations.add(member);
    }
    
    /**
     * Updates an existing event in the system.
     * 
     * @param updatedEvent The event with updated details
     * @throws IllegalArgumentException If the event is null or not found in the system
     */
    public void updateEvent(Event updatedEvent) {
        if (updatedEvent == null) {
            throw new IllegalArgumentException("Event cannot be null");
        }
        
        int eventIndex = -1;
        for (int i = 0; i < events.size(); i++) {
            if (events.get(i).getId() == updatedEvent.getId()) {
                eventIndex = i;
                break;
            }
        }
        
        if (eventIndex == -1) {
            throw new IllegalArgumentException("Event not found in system");
        }
        
        // Get current registrations for the existing event
        Event existingEvent = events.get(eventIndex);
        List<Member> registrations = eventRegistrations.get(existingEvent);
        
        // Update the event in the events list
        events.set(eventIndex, updatedEvent);
        
        // Update the registration mapping
        eventRegistrations.remove(existingEvent);
        eventRegistrations.put(updatedEvent, registrations);
        
        // Notify observers about the updated event
        notifyObservers(updatedEvent, EventAction.UPDATED);
    }

    /**
     * Cancels a member's registration for an event.
     * 
     * @param event The event to cancel registration for
     * @param member The member canceling registration
     */
    public void cancelRegistration(Event event, Member member) {
        if (event == null || member == null) {
            throw new IllegalArgumentException("Event and member cannot be null");
        }
        
        if (!events.contains(event)) {
            throw new IllegalArgumentException("Event not found in system");
        }
        
        List<Member> registrations = eventRegistrations.get(event);
        registrations.remove(member);
    }
    
    /**
     * Gets events happening on a specific date.
     * 
     * @param date The date to check
     * @return List of events on that date
     */
    public List<Event> getEventsByDate(LocalDate date) {
        List<Event> result = new ArrayList<>();
        
        for (Event event : events) {
            if (event.getDate().equals(date)) {
                result.add(event);
            }
        }
        
        return result;
    }
    
    /**
     * Gets a list of members registered for an event.
     * 
     * @param event The event to check
     * @return List of registered members
     */
    public List<Member> getRegisteredMembers(Event event) {
        if (event == null) {
            throw new IllegalArgumentException("Event cannot be null");
        }
        
        if (!eventRegistrations.containsKey(event)) {
            throw new IllegalArgumentException("Event not found in system");
        }
        
        return new ArrayList<>(eventRegistrations.get(event));
    }
    
    /**
     * Gets the number of registrations for an event.
     * 
     * @param event The event to check
     * @return Number of registrations
     */
    public int getRegistrationCount(Event event) {
        if (event == null) {
            throw new IllegalArgumentException("Event cannot be null");
        }
        
        if (!eventRegistrations.containsKey(event)) {
            return 0;
        }
        
        return eventRegistrations.get(event).size();
    }
    
    /**
     * Adds an observer to be notified of event changes.
     * 
     * @param observer The observer to add
     */
    public void addObserver(EventObserver observer) {
        if (observer == null) {
            throw new IllegalArgumentException("Observer cannot be null");
        }
        
        observers.add(observer);
    }
    
    /**
     * Removes an observer.
     * 
     * @param observer The observer to remove
     */
    public void removeObserver(EventObserver observer) {
        observers.remove(observer);
    }
    
    /**
     * Notifies all observers about an event action.
     * 
     * @param event The event that changed
     * @param action The action that occurred
     */
    private void notifyObservers(Event event, EventAction action) {
        for (EventObserver observer : observers) {
            observer.onEventUpdate(event, action);
        }
    }
    
    /**
     * Gets all events.
     * 
     * @return List of all events
     */
    public List<Event> getAllEvents() {
        return new ArrayList<>(events);
    }
    
    /**
     * Cancels an event and notifies observers.
     * 
     * @param event The event to cancel
     */
    public void cancelEvent(Event event) {
        if (event == null) {
            throw new IllegalArgumentException("Event cannot be null");
        }
        
        if (events.contains(event)) {
            events.remove(event);
            eventRegistrations.remove(event);
            
            // Notify observers about the cancellation
            notifyObservers(event, EventAction.CANCELLED);
        }
    }
}