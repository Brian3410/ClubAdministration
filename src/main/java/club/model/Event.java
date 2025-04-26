package club.model;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Represents an event in the club.
 * Contains an ID, name, date, time, location, and description.
 */
public class Event {
    private int id;
    private String name;
    private LocalDate date;
    private LocalTime time;
    private String location;
    private String description;

    /**
     * Constructs an Event object with the specified details.
     *
     * @param id          The unique ID of the event.
     * @param name        The name of the event.
     * @param date        The date of the event.
     * @param time        The time of the event.
     * @param location    The location of the event.
     * @param description The description of the event.
     */
    public Event(int id, String name, LocalDate date, LocalTime time, String location, String description) {
        setId(id);
        setName(name);
        setDate(date);
        setTime(time);
        setLocation(location);
        setDescription(description);
    }

    /**
     * Gets the ID of the event.
     *
     * @return The ID of the event.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the ID of the event.
     *
     * @param id The new ID of the event.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the name of the event.
     *
     * @return The name of the event.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the event.
     *
     * @param name The new name of the event.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the date of the event.
     *
     * @return The date of the event.
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Sets the date of the event.
     *
     * @param date The new date of the event.
     */
    public void setDate(LocalDate date) {
        this.date = date;
    }

    /**
     * Gets the time of the event.
     *
     * @return The time of the event.
     */
    public LocalTime getTime() {
        return time;
    }

    /**
     * Sets the time of the event.
     *
     * @param time The new time of the event.
     */
    public void setTime(LocalTime time) {
        this.time = time;
    }

    /**
     * Gets the location of the event.
     *
     * @return The location of the event.
     */
    public String getLocation() {
        return location;
    }

    /**
     * Sets the location of the event.
     *
     * @param location The new location of the event.
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Gets the description of the event.
     *
     * @return The description of the event.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the event.
     *
     * @param description The new description of the event.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns a string representation of the event.
     *
     * @return A formatted string representation of the event.
     */
    @Override
    public String toString() {
        return name + " on " + date + " at " + time + " (" + location + ")";
    }
}