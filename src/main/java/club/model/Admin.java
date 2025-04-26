package club.model;

/**
 * Represents an admin user in the club.
 * Extends the {@link Person} class and includes admin-specific attributes.
 */
public class Admin extends Person {

    private String adminId;
    private String password;

    /**
     * Constructs an Admin object with the specified details.
     *
     * @param name     The name of the admin.
     * @param email    The email of the admin.
     * @param adminId  The unique admin ID.
     * @param password The password for the admin.
     */
    public Admin(String name, String email, String adminId, String password) {
        super(name, email);
        setAdminId(adminId);
        setPassword(password);
    }

    /**
     * Gets the admin ID.
     *
     * @return The admin ID.
     */
    public String getAdminId() {
        return adminId;
    }

    /**
     * Sets the admin ID.
     *
     * @param adminId The new admin ID.
     */
    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    /**
     * Gets the admin's password.
     *
     * @return The admin's password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the admin's password.
     *
     * @param password The new password.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Displays the admin's information.
     * Overrides the {@link Person#displayInfo()} method.
     */
    @Override
    public void displayInfo() {
        System.out.println("Admin: " + getName() + " (" + getEmail() + "), Admin ID: " + adminId);
    }
}