package club.model;

import java.time.LocalDate;

/**
 * Represents a historical record of a member's status changes.
 * Implements the Value Object pattern to track membership state.
 */
public class MembershipRecord {
    private final Member member;
    private final LocalDate startDate;
    private final LocalDate expiryDate;
    private final MembershipStatus status;
    private final String notes;
    
    /**
     * Constructs a membership record with the given properties.
     * 
     * @param member The associated member
     * @param startDate The start date of this membership state
     * @param expiryDate The expiration date of this membership
     * @param status The membership status
     */
    public MembershipRecord(Member member, LocalDate startDate, LocalDate expiryDate, MembershipStatus status) {
        this(member, startDate, expiryDate, status, null);
    }
    
    /**
     * Constructs a membership record with the given properties and notes.
     * 
     * @param member The associated member
     * @param startDate The start date of this membership state
     * @param expiryDate The expiration date of this membership
     * @param status The membership status
     * @param notes Additional notes about this status change
     */
    public MembershipRecord(Member member, LocalDate startDate, LocalDate expiryDate, MembershipStatus status, String notes) {
        if (member == null) {
            throw new IllegalArgumentException("Member cannot be null");
        }
        if (startDate == null) {
            throw new IllegalArgumentException("Start date cannot be null");
        }
        if (expiryDate == null) {
            throw new IllegalArgumentException("Expiry date cannot be null");
        }
        if (status == null) {
            throw new IllegalArgumentException("Status cannot be null");
        }
        if (expiryDate.isBefore(startDate)) {
            throw new IllegalArgumentException("Expiry date cannot be before start date");
        }
        
        this.member = member;
        this.startDate = startDate;
        this.expiryDate = expiryDate;
        this.status = status;
        this.notes = notes;
    }
    
    public Member getMember() {
        return member;
    }
    
    public LocalDate getStartDate() {
        return startDate;
    }
    
    public LocalDate getExpiryDate() {
        return expiryDate;
    }
    
    public MembershipStatus getStatus() {
        return status;
    }
    
    public String getNotes() {
        return notes;
    }
    
    /**
     * Checks if the membership is currently valid.
     * 
     * @return true if the membership is valid
     */
    public boolean isValid() {
        LocalDate today = LocalDate.now();
        return !today.isBefore(startDate) && !today.isAfter(expiryDate) && status == MembershipStatus.ACTIVE;
    }
    
    @Override
    public String toString() {
        return String.format("Membership[%s, %s, %s to %s, %s]", 
                member.getName(),
                status,
                startDate,
                expiryDate,
                notes != null ? "Note: " + notes : "");
    }
}