package club.database;

import club.App;
import club.model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Manages data persistence and retrieval for the club application.
 * Handles saving and loading data for members, admins, events, and announcements.
 */
public class DataManager {

    private final DatabaseManager dbManager;

    /**
     * Constructor for the DataManager.
     * Initializes the database manager and ensures the membership ID table is set up.
     */
    public DataManager() {
        this.dbManager = new DatabaseManager();
    }

    /**
     * Generates the next membership ID by incrementing the highest ID in the members table.
     *
     * @return The next membership ID as a 5-digit string.
     */
    public String generateNextMembershipId() {
        String query = "SELECT MAX(id) AS maxId FROM members";
        try (Connection conn = dbManager.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                int nextId = rs.getInt("maxId") + 1;
                return String.format("%05d", nextId); // Ensure the ID is always 5 digits
            }
        } catch (SQLException e) {
            System.err.println("Error generating next membership ID: " + e.getMessage());
        }
        return "00001"; // Default to "00001" if no members exist
    }

    /**
     * Saves all club data (members, admins, events, announcements) to the database.
     *
     * @param club The club object containing the data to save.
     */
    public void saveClubData(Club club) {
        try (Connection conn = dbManager.getConnection()) {
            saveClubName(conn, club);
            saveMembers(conn, club);
            saveAdmins(conn, club);
            saveAnnouncements(conn, club);
            saveEvents(conn, club);

            // Reload the in-memory Club object to reflect the latest database state
            Club updatedClub = loadClubData();
            App.setClub(updatedClub); // Update the global Club instance
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Saves the club name to the database.
     *
     * @param conn The database connection.
     * @param club The club object containing the name.
     * @throws SQLException If a database error occurs.
     */
    private void saveClubName(Connection conn, Club club) throws SQLException {
        String insertClub = "INSERT OR REPLACE INTO club (id, name) VALUES (1, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(insertClub)) {
            stmt.setString(1, club.getClubName());
            stmt.executeUpdate();
        }
    }

    /**
     * Saves all members to the database.
     *
     * @param conn The database connection.
     * @param club The club object containing the members.
     * @throws SQLException If a database error occurs.
     */
    private void saveMembers(Connection conn, Club club) throws SQLException {
        String updateQuery = "UPDATE members SET name = ?, email = ?, phone = ?, active = ?, password = ? WHERE membershipId = ?";
        String insertQuery = "INSERT INTO members (name, email, phone, active, password, membershipId) VALUES (?, ?, ?, ?, ?, ?)";
        String deleteQuery = "DELETE FROM members WHERE membershipId NOT IN (?)";

        try (PreparedStatement updateStmt = conn.prepareStatement(updateQuery);
             PreparedStatement insertStmt = conn.prepareStatement(insertQuery)) {
            for (Member member : club.getMembers()) {
                // Try to update the record
                updateStmt.setString(1, member.getName());
                updateStmt.setString(2, member.getEmail());
                updateStmt.setString(3, member.getPhone());
                updateStmt.setBoolean(4, member.isActive());
                updateStmt.setString(5, member.getPassword());
                updateStmt.setString(6, member.getMembershipId());
                int rowsAffected = updateStmt.executeUpdate();

                // If no rows were updated, insert the record
                if (rowsAffected == 0) {
                    insertStmt.setString(1, member.getName());
                    insertStmt.setString(2, member.getEmail());
                    insertStmt.setString(3, member.getPhone());
                    insertStmt.setBoolean(4, member.isActive());
                    insertStmt.setString(5, member.getPassword());
                    insertStmt.setString(6, member.getMembershipId());
                    insertStmt.executeUpdate();
                }
            }
        }

        // Delete records not in the current list
        deleteObsoleteRecords(conn, deleteQuery, club.getMembers().stream()
                .map(Member::getMembershipId)
                .toList());
    }

    /**
     * Saves all admins to the database.
     *
     * @param conn The database connection.
     * @param club The club object containing the admins.
     * @throws SQLException If a database error occurs.
     */
    private void saveAdmins(Connection conn, Club club) throws SQLException {
        String updateQuery = "UPDATE admins SET name = ?, email = ?, password = ? WHERE adminId = ?";
        String insertQuery = "INSERT INTO admins (name, email, adminId, password) VALUES (?, ?, ?, ?)";
        String deleteQuery = "DELETE FROM admins WHERE adminId NOT IN (?)";

        try (PreparedStatement updateStmt = conn.prepareStatement(updateQuery);
             PreparedStatement insertStmt = conn.prepareStatement(insertQuery)) {
            for (Admin admin : club.getAdmins()) {
                // Try to update the record
                updateStmt.setString(1, admin.getName());
                updateStmt.setString(2, admin.getEmail());
                updateStmt.setString(3, admin.getPassword());
                updateStmt.setString(4, admin.getAdminId());
                int rowsAffected = updateStmt.executeUpdate();

                // If no rows were updated, insert the record
                if (rowsAffected == 0) {
                    insertStmt.setString(1, admin.getName());
                    insertStmt.setString(2, admin.getEmail());
                    insertStmt.setString(3, admin.getAdminId());
                    insertStmt.setString(4, admin.getPassword());
                    insertStmt.executeUpdate();
                }
            }
        }

        // Delete records not in the current list
        deleteObsoleteRecords(conn, deleteQuery, club.getAdmins().stream()
                .map(Admin::getAdminId)
                .toList());
    }

    /**
     * Saves all announcements to the database.
     *
     * @param conn The database connection.
     * @param club The club object containing the announcements.
     * @throws SQLException If a database error occurs.
     */
    private void saveAnnouncements(Connection conn, Club club) throws SQLException {
        String updateQuery = "UPDATE announcements SET message = ?, dateTime = ? WHERE id = ?";
        String insertQuery = "INSERT INTO announcements (id, message, dateTime) VALUES (?, ?, ?)";
        String deleteQuery = "DELETE FROM announcements WHERE id NOT IN (?)";

        try (PreparedStatement updateStmt = conn.prepareStatement(updateQuery);
             PreparedStatement insertStmt = conn.prepareStatement(insertQuery)) {
            for (Announcement announcement : club.getAnnouncements()) {
                updateStmt.setString(1, announcement.getMessage());
                updateStmt.setString(2, announcement.getDateTime().toString());
                updateStmt.setInt(3, announcement.getId());
                int rowsAffected = updateStmt.executeUpdate();

                if (rowsAffected == 0) {
                    insertStmt.setInt(1, announcement.getId());
                    insertStmt.setString(2, announcement.getMessage());
                    insertStmt.setString(3, announcement.getDateTime().toString());
                    insertStmt.executeUpdate();
                }
            }
        }

        deleteObsoleteRecords(conn, deleteQuery, club.getAnnouncements().stream()
                .map(Announcement::getId)
                .toList());
    }

    /**
     * Saves all events to the database.
     *
     * @param conn The database connection.
     * @param club The club object containing the events.
     * @throws SQLException If a database error occurs.
     */
    private void saveEvents(Connection conn, Club club) throws SQLException {
        String updateQuery = "UPDATE events SET name = ?, date = ?, time = ?, location = ?, description = ? WHERE id = ?";
        String insertQuery = "INSERT INTO events (id, name, date, time, location, description) VALUES (?, ?, ?, ?, ?, ?)";
        String deleteQuery = "DELETE FROM events WHERE id NOT IN (?)";

        try (PreparedStatement updateStmt = conn.prepareStatement(updateQuery);
             PreparedStatement insertStmt = conn.prepareStatement(insertQuery)) {
            for (Event event : club.getEvents()) {
                updateStmt.setString(1, event.getName());
                updateStmt.setString(2, event.getDate().toString());
                updateStmt.setString(3, event.getTime().toString());
                updateStmt.setString(4, event.getLocation());
                updateStmt.setString(5, event.getDescription());
                updateStmt.setInt(6, event.getId());
                int rowsAffected = updateStmt.executeUpdate();

                if (rowsAffected == 0) {
                    insertStmt.setInt(1, event.getId());
                    insertStmt.setString(2, event.getName());
                    insertStmt.setString(3, event.getDate().toString());
                    insertStmt.setString(4, event.getTime().toString());
                    insertStmt.setString(5, event.getLocation());
                    insertStmt.setString(6, event.getDescription());
                    insertStmt.executeUpdate();
                }
            }
        }

        deleteObsoleteRecords(conn, deleteQuery, club.getEvents().stream()
                .map(Event::getId)
                .toList());
    }

    /**
     * Deletes records from the database that are not in the provided list of IDs.
     *
     * @param conn The database connection.
     * @param deleteQuery The delete query to execute.
     * @param ids The list of IDs to retain.
     * @throws SQLException If a database error occurs.
     */
    private void deleteObsoleteRecords(Connection conn, String deleteQuery, List<?> ids) throws SQLException {
        if (ids.isEmpty()) {
            // Special handling for empty lists - delete all records of this type
            String tableName = extractTableName(deleteQuery);
            if (tableName != null) {
                String deleteAllQuery = "DELETE FROM " + tableName;
                try (PreparedStatement deleteAllStmt = conn.prepareStatement(deleteAllQuery)) {
                    deleteAllStmt.executeUpdate();
                }
            }
            return;
        }
    
        String placeholders = String.join(",", ids.stream().map(id -> "?").toList());
        deleteQuery = deleteQuery.replace("(?)", "(" + placeholders + ")");
    
        try (PreparedStatement deleteStmt = conn.prepareStatement(deleteQuery)) {
            for (int i = 0; i < ids.size(); i++) {
                deleteStmt.setObject(i + 1, ids.get(i));
            }
            deleteStmt.executeUpdate();
        }
    }

    /**
     * Extracts the table name from a delete query.
     * 
     * @param deleteQuery The delete query to parse.
     * @return The table name or null if not found.
     */
    private String extractTableName(String deleteQuery) {
        // Simple parser to extract table name from "DELETE FROM table WHERE..."
        if (deleteQuery == null || !deleteQuery.toUpperCase().contains("FROM")) {
            return null;
        }
        
        String[] parts = deleteQuery.split("\\s+");
        for (int i = 0; i < parts.length - 1; i++) {
            if ("FROM".equalsIgnoreCase(parts[i])) {
                return parts[i+1];
            }
        }
        return null;
    }
    
    /**
     * Loads all club data (members, admins, events, announcements) from the database.
     *
     * @return The populated {@link Club} object.
     */
    public Club loadClubData() {
        String clubName = App.getClubName(); // Retrieve the club name from App
        Club club = new Club(clubName);
        try (Connection conn = dbManager.getConnection()) {
            loadClubName(conn, club);
            loadMembers(conn, club);
            loadAdmins(conn, club);
            loadEvents(conn, club);
            loadAnnouncements(conn, club);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return club;
    }

    /**
     * Loads the club name from the database.
     *
     * @param conn The database connection.
     * @param club The club object to populate.
     * @throws SQLException If a database error occurs.
     */
    private void loadClubName(Connection conn, Club club) throws SQLException {
        String selectClub = "SELECT name FROM club WHERE id = 1";
        try (PreparedStatement stmt = conn.prepareStatement(selectClub);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                club.setClubName(rs.getString("name"));
            }
        }
    }

    /**
     * Loads all members from the database.
     *
     * @param conn The database connection.
     * @param club The club object to populate.
     * @throws SQLException If a database error occurs.
     */
    private void loadMembers(Connection conn, Club club) throws SQLException {
        String selectMembers = "SELECT name, email, phone, active, password, membershipId FROM members";
        try (PreparedStatement stmt = conn.prepareStatement(selectMembers);
             ResultSet rs = stmt.executeQuery()) {
            List<Member> members = new ArrayList<>();
            while (rs.next()) {
                Member member = new Member(
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getBoolean("active"),
                        rs.getString("password")
                );
                member.setMembershipId(rs.getString("membershipId"));
                members.add(member);
            }
            club.setMembers(members);
        }
    }

    /**
     * Loads all admins from the database.
     *
     * @param conn The database connection.
     * @param club The club object to populate.
     * @throws SQLException If a database error occurs.
     */
    private void loadAdmins(Connection conn, Club club) throws SQLException {
        String selectAdmins = "SELECT name, email, adminId, password FROM admins";
        try (PreparedStatement stmt = conn.prepareStatement(selectAdmins);
             ResultSet rs = stmt.executeQuery()) {
            List<Admin> admins = new ArrayList<>();
            while (rs.next()) {
                admins.add(new Admin(
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("adminId"),
                        rs.getString("password")
                ));
            }
            club.setAdmins(admins);
        }
    }

    /**
     * Loads all events from the database.
     *
     * @param conn The database connection.
     * @param club The club object to populate.
     * @throws SQLException If a database error occurs.
     */
    private void loadEvents(Connection conn, Club club) throws SQLException {
        String selectEvents = "SELECT id, name, date, time, location, description FROM events";
        try (PreparedStatement stmt = conn.prepareStatement(selectEvents);
            ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                club.addEvent(new Event(
                        rs.getInt("id"), // Load the event ID
                        rs.getString("name"),
                        LocalDate.parse(rs.getString("date")),
                        LocalTime.parse(rs.getString("time")),
                        rs.getString("location"),
                        rs.getString("description")
                ));
            }
        }
    }

    /**
     * Loads all announcements from the database.
     *
     * @param conn The database connection.
     * @param club The club object to populate.
     * @throws SQLException If a database error occurs.
     */
    private void loadAnnouncements(Connection conn, Club club) throws SQLException {
        String selectAnnouncements = "SELECT id, message, dateTime FROM announcements";
        try (PreparedStatement stmt = conn.prepareStatement(selectAnnouncements);
            ResultSet rs = stmt.executeQuery()) {
            List<Announcement> announcements = new ArrayList<>();
            while (rs.next()) {
                announcements.add(new Announcement(
                        rs.getInt("id"), // Load the announcement ID
                        rs.getString("message"),
                        LocalDateTime.parse(rs.getString("dateTime"))
                ));
            }
            club.setAnnouncements(announcements);
        }
    }

    /**
     * Generates the next unique event ID.
     *
     * @return The next event ID.
     */
    public int generateNextEventId() {
        String query = "SELECT MAX(id) AS maxId FROM events";
        try (Connection conn = dbManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("maxId") + 1;
            }
        } catch (SQLException e) {
            System.err.println("Error generating next event ID: " + e.getMessage());
        }
        return 1; // Default to 1 if no events exist
    }

    public int generateNextAnnouncementId() {
        String query = "SELECT MAX(id) AS maxId FROM announcements";
        try (Connection conn = dbManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("maxId") + 1;
            }
        } catch (SQLException e) {
            System.err.println("Error generating next announcement ID: " + e.getMessage());
        }
        return 1; // Default to 1 if no announcements exist
    }
}