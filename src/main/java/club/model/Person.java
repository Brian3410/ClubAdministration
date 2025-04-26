package club.model;

/**
 * Represents a generic person in the club.
 * Serves as a base class for specific types of users such as {@link Member} and {@link Admin}.
 */
public abstract class Person {

    private String name;
    private String email;

    /**
     * Constructs a Person object with the specified name and email.
     *
     * @param name  The name of the person.
     * @param email The email of the person.
     */
    public Person(String name, String email) {
        setName(name);
        setEmail(email);
    }

    /**
     * Gets the name of the person.
     *
     * @return The name of the person.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the person.
     *
     * @param name The new name of the person.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the email of the person.
     *
     * @return The email of the person.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email of the person.
     *
     * @param email The new email of the person.
     */
    public void setEmail(String email) {
        if (!email.contains("@")) {
            throw new IllegalArgumentException("Invalid email format.");
        }
        this.email = email;
    }

    /**
     * Displays the person's information.
     * This method must be implemented by subclasses.
     */
    public abstract void displayInfo();
}