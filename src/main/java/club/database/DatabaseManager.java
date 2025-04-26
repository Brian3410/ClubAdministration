package club.database;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Manages the database connection and initialization.
 * Ensures the database structure is created and accessible.
 */
public class DatabaseManager {

    public static final String SAVES_FOLDER = "saves"; // Centralized folder path
    private static final String DB_URL = "jdbc:sqlite:" + SAVES_FOLDER + "/club.db";

    /**
     * Constructor for the DatabaseManager.
     * Ensures the database directory exists and initializes the database structure.
     */
    public DatabaseManager() {
        ensureDatabaseDirectoryExists();
        initializeDatabase();
    }

    /**
     * Ensures the database directory exists.
     * Creates the directory if it does not already exist.
     */
    private void ensureDatabaseDirectoryExists() {
        File dbDirectory = new File(SAVES_FOLDER);
        if (!dbDirectory.exists()) {
            if (dbDirectory.mkdirs()) {
                System.out.println("Database directory created: " + SAVES_FOLDER);
            } else {
                System.err.println("Failed to create database directory: " + SAVES_FOLDER);
            }
        }
    }

    /**
     * Initializes the database structure.
     * Creates the necessary tables if they do not already exist.
     */
    private void initializeDatabase() {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            if (conn != null) {
                createTables(conn);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates the necessary tables in the database.
     *
     * @param conn The database connection.
     * @throws SQLException If an error occurs while creating the tables.
     */
    private void createTables(Connection conn) throws SQLException {
        String createClubTable = "CREATE TABLE IF NOT EXISTS club ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "name TEXT NOT NULL"
                + ");";

        String createMembersTable = "CREATE TABLE IF NOT EXISTS members ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "name TEXT NOT NULL, "
                + "email TEXT NOT NULL UNIQUE, "
                + "phone TEXT, "
                + "active INTEGER, "
                + "password TEXT, "
                + "membershipId TEXT NOT NULL UNIQUE"
                + ");";

        String createAdminsTable = "CREATE TABLE IF NOT EXISTS admins ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "name TEXT NOT NULL, "
                + "email TEXT NOT NULL UNIQUE, "
                + "adminId TEXT, "
                + "password TEXT"
                + ");";

        String createAnnouncementsTable = "CREATE TABLE IF NOT EXISTS announcements ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "message TEXT NOT NULL, "
                + "dateTime TEXT NOT NULL"
                + ");";

        String createEventsTable = "CREATE TABLE IF NOT EXISTS events ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "name TEXT NOT NULL, "
                + "date TEXT NOT NULL, "
                + "time TEXT NOT NULL, "
                + "location TEXT NOT NULL, "
                + "description TEXT"
                + ");";

        try (PreparedStatement stmt1 = conn.prepareStatement(createClubTable);
             PreparedStatement stmt2 = conn.prepareStatement(createMembersTable);
             PreparedStatement stmt3 = conn.prepareStatement(createAdminsTable);
             PreparedStatement stmt4 = conn.prepareStatement(createAnnouncementsTable);
             PreparedStatement stmt5 = conn.prepareStatement(createEventsTable)) {
            stmt1.execute();
            stmt2.execute();
            stmt3.execute();
            stmt4.execute();
            stmt5.execute();
        }
    }

    /**
     * Retrieves a connection to the database.
     *
     * @return A {@link Connection} object for interacting with the database.
     * @throws SQLException If a database access error occurs.
     */
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }
}