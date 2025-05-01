package club.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Manages club membership operations including registration, renewals, and status changes.
 * Demonstrates separation of concerns by extracting membership logic from the Club class.
 */
public class MembershipManager {
    private List<MembershipRecord> membershipHistory;
    
    public MembershipManager() {
        this.membershipHistory = new ArrayList<>();
    }
    
    /**
     * Registers a new member with the club and creates their membership record.
     * 
     * @param member The new member to register
     * @return The generated membership ID
     */
    public String registerNewMember(Member member) {
        if (member == null) {
            throw new IllegalArgumentException("Member cannot be null");
        }
        
        // Generate unique membership ID
        String membershipId = generateMembershipId();
        member.setMembershipId(membershipId);
        member.setActive(true);
        
        // Create membership record
        MembershipRecord record = new MembershipRecord(
                member, 
                LocalDate.now(),
                LocalDate.now().plusYears(1),
                MembershipStatus.ACTIVE
        );
        
        membershipHistory.add(record);
        return membershipId;
    }
    
    /**
     * Renews membership for an existing member.
     * 
     * @param member The member to renew
     * @return The updated membership expiration date
     */
    public LocalDate renewMembership(Member member) {
        if (member == null) {
            throw new IllegalArgumentException("Member cannot be null");
        }
        
        // Find latest membership record
        MembershipRecord latestRecord = findLatestMembershipRecord(member);
        
        if (latestRecord == null) {
            throw new IllegalStateException("No membership record found for this member");
        }
        
        // Create renewal record
        LocalDate startDate = LocalDate.now();
        LocalDate expiryDate = startDate.plusYears(1);
        
        MembershipRecord renewalRecord = new MembershipRecord(
                member,
                startDate,
                expiryDate,
                MembershipStatus.ACTIVE
        );
        
        membershipHistory.add(renewalRecord);
        member.setActive(true);
        
        return expiryDate;
    }
    
    /**
     * Suspends a member's active status.
     * 
     * @param member The member to suspend
     * @param reason The reason for suspension
     */
    public void suspendMembership(Member member, String reason) {
        if (member == null) {
            throw new IllegalArgumentException("Member cannot be null");
        }
        
        MembershipRecord latestRecord = findLatestMembershipRecord(member);
        
        if (latestRecord != null) {
            // Create suspension record
            MembershipRecord suspensionRecord = new MembershipRecord(
                    member,
                    LocalDate.now(),
                    latestRecord.getExpiryDate(),
                    MembershipStatus.SUSPENDED,
                    reason
            );
            
            membershipHistory.add(suspensionRecord);
            member.setActive(false);
        }
    }
    
    /**
     * Gets the membership history for a specific member.
     * 
     * @param member The member to get history for
     * @return List of membership records
     */
    public List<MembershipRecord> getMembershipHistory(Member member) {
        List<MembershipRecord> history = new ArrayList<>();
        
        for (MembershipRecord record : membershipHistory) {
            if (record.getMember().equals(member)) {
                history.add(record);
            }
        }
        
        return history;
    }
    
    /**
     * Find the latest membership record for a member.
     * 
     * @param member The member to search for
     * @return The latest membership record or null if none found
     */
    private MembershipRecord findLatestMembershipRecord(Member member) {
        MembershipRecord latest = null;
        
        for (MembershipRecord record : membershipHistory) {
            if (record.getMember().equals(member)) {
                if (latest == null || record.getStartDate().isAfter(latest.getStartDate())) {
                    latest = record;
                }
            }
        }
        
        return latest;
    }
    
    /**
     * Generates a guaranteed unique membership ID.
     * 
     * @return A new unique membership ID
     */
    private String generateMembershipId() {
        String candidateId;
        boolean isUnique;
        
        do {
            // Generate a candidate ID
            candidateId = "MEM-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
            
            // Check if it's already in use
            isUnique = true;
            for (MembershipRecord record : membershipHistory) {
                if (record.getMember().getMembershipId().equals(candidateId)) {
                    isUnique = false;
                    break;
                }
            }
        } while (!isUnique);
        
        return candidateId;
    }
    
    /**
     * Gets the full membership history.
     * 
     * @return The full list of membership records
     */
    public List<MembershipRecord> getMembershipHistory() {
        return new ArrayList<>(membershipHistory);
    }
}