package club.model;

/**
 * Represents a member of the club.
 * Extends the {@link Person} class and includes member-specific attributes.
 */
public class Member extends Person {

    private String membershipId;
    private boolean active;
    private String password;
    private String phone;

    /**
     * Constructs a Member object with the specified details.
     *
     * @param name     The name of the member.
     * @param email    The email of the member.
     * @param phone    The phone number of the member.
     * @param active   The active status of the member.
     * @param password The password for the member.
     */
    public Member(String name, String email, String phone, boolean active, String password) {
        super(name, email);
        setPhone(phone);
        setActive(active);
        setPassword(password);
    }

    /**
     * Gets the phone number of the member.
     *
     * @return The phone number of the member.
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Sets the phone number of the member.
     *
     * @param phone The new phone number of the member.
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Gets the membership ID of the member.
     *
     * @return The membership ID of the member.
     */
    public String getMembershipId() {
        return membershipId;
    }

    /**
     * Sets the membership ID of the member.
     *
     * @param membershipId The new membership ID of the member.
     */
    public void setMembershipId(String membershipId) {
        this.membershipId = membershipId;
    }

    /**
     * Checks if the member is active.
     *
     * @return true if the member is active, false otherwise.
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Sets the active status of the member.
     *
     * @param active The new active status of the member.
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * Gets the password of the member.
     *
     * @return The password of the member.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password of the member.
     *
     * @param password The new password of the member.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Displays the member's information.
     * Overrides the {@link Person#displayInfo()} method.
     */
    @Override
    public void displayInfo() {
        System.out.println("Member: " + getName() + " (" + getEmail() + "), Phone: " + phone + ", Active: " + active);
    }
}