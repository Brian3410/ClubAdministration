package club.model;

/**
 * Represents the possible states of a club membership.
 * Using enums demonstrates type safety for membership status values.
 */
public enum MembershipStatus {
    ACTIVE("Active membership with full privileges"),
    SUSPENDED("Temporarily suspended membership"),
    EXPIRED("Membership has expired"),
    CANCELLED("Membership was cancelled");
    
    private final String description;
    
    MembershipStatus(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
}