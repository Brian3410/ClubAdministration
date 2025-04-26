package club.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a club with members, admins, events, and announcements.
 * Provides methods to manage and validate club data.
 */
public class Club {

    private String clubName;
    private List<Member> members;
    private List<Admin> admins;
    private List<Event> events;
    private List<Announcement> announcements;

    /**
     * Constructs a Club object with the specified name.
     * Initializes empty lists for members, admins, events, and announcements.
     *
     * @param clubName The name of the club.
     */
    public Club(String clubName) {
        setClubName(clubName);
        setMembers(new ArrayList<>());
        setAdmins(new ArrayList<>());
        setEvents(new ArrayList<>());
        setAnnouncements(new ArrayList<>());
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
     * Adds a new member to the club.
     *
     * @param newMember The member to add.
     * @throws IllegalArgumentException If the member is null.
     */
    public void addMember(Member newMember) {
        if (newMember == null) {
            throw new IllegalArgumentException("Member cannot be null.");
        }
        this.members.add(newMember);
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
        return events;
    }

    /**
     * Sets the list of events in the club.
     *
     * @param events The new list of events.
     */
    public void setEvents(List<Event> events) {
        if (events == null) {
            throw new IllegalArgumentException("Events list cannot be null.");
        }
        this.events = events;
    }

    /**
     * Adds a new event to the club.
     *
     * @param event The event to add.
     * @throws IllegalArgumentException If the event is null.
     */
    public void addEvent(Event event) {
        if (event == null) {
            throw new IllegalArgumentException("Event cannot be null.");
        }
        this.events.add(event);
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
}