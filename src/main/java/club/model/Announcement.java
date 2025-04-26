package club.model;

import java.time.LocalDateTime;

/**
 * Represents an announcement in the club.
 * Contains an ID, a message, and the date and time the announcement was created.
 */
public class Announcement {
    private int id;
    private String message;
    private LocalDateTime dateTime;

    /**
     * Constructs an Announcement object with the specified ID, message, and date-time.
     *
     * @param id       The unique ID of the announcement.
     * @param message  The announcement message.
     * @param dateTime The date and time of the announcement.
     * @throws IllegalArgumentException If the message is null or empty.
     */
    public Announcement(int id, String message, LocalDateTime dateTime) {
        setId(id);
        setMessage(message);
        setDateTime(dateTime);
    }

    /**
     * Gets the ID of the announcement.
     *
     * @return The ID of the announcement.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the ID of the announcement.
     *
     * @param id The new ID of the announcement.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the announcement message.
     *
     * @return The announcement message.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the announcement message.
     *
     * @param message The new announcement message.
     * @throws IllegalArgumentException If the message is null or empty.
     */
    public void setMessage(String message) {
        if (message == null || message.trim().isEmpty()) {
            throw new IllegalArgumentException("Message cannot be null or empty.");
        }
        this.message = message;
    }

    /**
     * Gets the date and time of the announcement.
     *
     * @return The date and time of the announcement.
     */
    public LocalDateTime getDateTime() {
        return dateTime;
    }

    /**
     * Sets the date and time of the announcement.
     *
     * @param dateTime The new date and time of the announcement.
     */
    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    /**
     * Returns a string representation of the announcement.
     *
     * @return A formatted string representation of the announcement.
     */
    @Override
    public String toString() {
        return dateTime + ": " + message;
    }
}