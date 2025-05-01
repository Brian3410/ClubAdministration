package club.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a club with members, admins, events, and announcements.
 * Coordinates the various management systems.
 */
public class Club implements EventObserver {

    private String clubName;
    private List<Member> members;
    private List<Admin> admins;
    private List<Announcement> announcements;
    
    // New components using improved OO design
    private MembershipManager membershipManager;
    private EventManager eventManager;

    /**
     * Constructs a Club object with the specified name.
     * Initializes empty lists and managers.
     *
     * @param clubName The name of the club.
     */
    public Club(String clubName) {
        setClubName(clubName);
        setMembers(new ArrayList<>());
        setAdmins(new ArrayList<>());
        setAnnouncements(new ArrayList<>());
        
        // Initialize managers
        this.membershipManager = new MembershipManager();
        this.eventManager = new EventManager();
        
        // Register as observer for events
        this.eventManager.addObserver(this);
    }

    // --- Club Name ---

    /**
     * Gets the name of the club.
     *
     * @return The club name.
     */
    public String getClubName() {
        return clubName;
    }

    /**
     * Sets the name of the club.
     *
     * @param clubName The new club name.
     */
    public void setClubName(String clubName) {
        if (clubName == null || clubName.trim().isEmpty()) {
            throw new IllegalArgumentException("Club name cannot be null or empty.");
        }
        this.clubName = clubName;
    }

    // --- Members ---

    /**
     * Gets the list of members in the club.
     *
     * @return The list of members.
     */
    public List<Member> getMembers() {
        return members;
    }

    /**
     * Sets the list of members in the club.
     *
     * @param members The new list of members.
     */
    public void setMembers(List<Member> members) {
        if (members == null) {
            throw new IllegalArgumentException("Members list cannot be null.");
        }
        this.members = members;
    }

    /**
     * Updates an existing event in the club using the event manager.
     *
     * @param event The updated event
     * @throws IllegalArgumentException If the event is null or not found
     */
    public void updateEvent(Event event) {
        if (event == null) {
            throw new IllegalArgumentException("Event cannot be null.");
        }
        eventManager.updateEvent(event);
    }

    /**
     * Removes an announcement from the club.
     * 
     * @param announcement The announcement to remove
     * @return true if the announcement was found and removed, false otherwise
     * @throws IllegalArgumentException if the announcement is null
     */
    public boolean removeAnnouncement(Announcement announcement) {
        if (announcement == null) {
            throw new IllegalArgumentException("Announcement cannot be null.");
        }
        return this.announcements.remove(announcement);
    }

    /**
     * Registers a new member to the club using the membership manager.
     *
     * @param newMember The member to add.
     * @return The assigned membership ID
     * @throws IllegalArgumentException If the member is null.
     */
    public String registerNewMember(Member newMember) {
        if (newMember == null) {
            throw new IllegalArgumentException("Member cannot be null.");
        }
        
        // Add to members list
        this.members.add(newMember);
        
        // Register with membership manager
        return membershipManager.registerNewMember(newMember);
    }

    /**
     * Validates a member's login credentials.
     *
     * @param email    The member's email.
     * @param password The member's password.
     * @return The member if credentials are valid and the account is active, otherwise null.
     */
    public Member validateMemberLogin(String email, String password) {
        for (Member member : members) {
            if (member.getEmail().equals(email) && member.getPassword().equals(password)) {
                return member.isActive() ? member : null;
            }
        }
        return null;
    }

    // --- Admins ---

    /**
     * Gets the list of admins in the club.
     *
     * @return The list of admins.
     */
    public List<Admin> getAdmins() {
        if (admins == null) {
            admins = new ArrayList<>(); // Ensure admins is never null
        }
        return admins;
    }

    /**
     * Sets the list of admins in the club.
     *
     * @param admins The new list of admins.
     */
    public void setAdmins(List<Admin> admins) {
        if (admins == null) {
            throw new IllegalArgumentException("Admins list cannot be null.");
        }
        this.admins = admins;
    }

    /**
     * Validates an admin's login credentials.
     *
     * @param email    The admin's email.
     * @param password The admin's password.
     * @return The admin if credentials are valid, otherwise null.
     */
    public Admin validateAdminLogin(String email, String password) {
        for (Admin admin : admins) {
            if (admin.getEmail().equals(email) && admin.getPassword().equals(password)) {
                return admin;
            }
        }
        return null;
    }

    // --- Events ---

    /**
     * Gets the list of events in the club.
     *
     * @return The list of events.
     */
    public List<Event> getEvents() {
        return eventManager.getAllEvents();
    }

    /**
     * Adds a new event to the club using the event manager.
     *
     * @param event The event to add.
     * @throws IllegalArgumentException If the event is null.
     */
    public void addEvent(Event event) {
        eventManager.addEvent(event);
    }
    
    /**
     * Registers a member for an event.
     * 
     * @param event The event to register for
     * @param member The member to register
     */
    public void registerForEvent(Event event, Member member) {
        eventManager.registerMemberForEvent(event, member);
    }
    
    /**
     * Gets the membership manager.
     * 
     * @return The membership manager
     */
    public MembershipManager getMembershipManager() {
        return membershipManager;
    }
    
    /**
     * Gets the event manager.
     * 
     * @return The event manager
     */
    public EventManager getEventManager() {
        return eventManager;
    }

    // --- Announcements ---

    /**
     * Gets the list of announcements in the club.
     *
     * @return The list of announcements.
     */
    public List<Announcement> getAnnouncements() {
        return announcements;
    }

    /**
     * Sets the list of announcements in the club.
     *
     * @param announcements The new list of announcements.
     */
    public void setAnnouncements(List<Announcement> announcements) {
        if (announcements == null) {
            throw new IllegalArgumentException("Announcements list cannot be null.");
        }
        this.announcements = announcements;
    }

    /**
     * Adds a new announcement to the club.
     *
     * @param announcement The announcement to add.
     * @throws IllegalArgumentException If the announcement is null.
     */
    public void addAnnouncement(Announcement announcement) {
        if (announcement == null) {
            throw new IllegalArgumentException("Announcement cannot be null.");
        }
        this.announcements.add(announcement);
    }
    
    /**
     * Implementation of the EventObserver interface.
     * Creates announcements automatically when events are created or cancelled.
     */
    @Override
    public void onEventUpdate(Event event, EventAction action) {
        String message = "";
        
        switch (action) {
            case CREATED:
                message = "New event created: " + event.getName() + " on " + 
                          event.getDate() + " at " + event.getTime();
                break;
            case CANCELLED:
                message = "Event cancelled: " + event.getName() + " that was scheduled for " +
                          event.getDate() + " at " + event.getTime();
                break;
            case UPDATED:
                message = "Event updated: " + event.getName() + " on " + 
                          event.getDate() + " at " + event.getTime();
                break;
        }
        
        if (!message.isEmpty()) {
            // Auto-create announcement for event changes
            Announcement announcement = new Announcement(
                announcements.size() + 1,
                message,
                LocalDateTime.now()
            );
            addAnnouncement(announcement);
        }
    }
}