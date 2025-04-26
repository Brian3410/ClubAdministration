# README: IntegraDev Club Management System  

## Overview  
The **IntegraDev Club Management System** is a Java-based application designed to manage club operations, including members, admins, events, and announcements. It provides a user-friendly interface built with JavaFX and ensures data persistence using SQLite. The system adheres to Object-Oriented Programming (OOP) principles, ensuring modularity, scalability, and maintainability 

## Features  

### User Management  
- Admins and members can log in and manage their profiles.  
- Admins can add, edit, and delete members.  

### Event Management  
- Admins can create, edit, and delete events.  
- Members can view upcoming events.  

### Announcements  
- Admins can post announcements visible to all members.  

### Dashboard  
- Displays club statistics, including total members and upcoming events.  
- Provides quick navigation to key features.  

### Data Persistence  
- All data (members, admins, events, announcements) is stored in an SQLite database.  

### Secure Login  
- Passwords are securely stored and validated.  

## Technologies Used  
- **Java**: Core programming language.  
- **JavaFX**: For building the graphical user interface (GUI).  
- **SQLite**: For data persistence.  
- **Maven**: For dependency management and project build.  

## Project Structure  
``` bash
src/
├── main/
│   ├── java/
│   │   ├── club/
│   │   │   ├── App.java                # Main application class
│   │   │   ├── model/                 # Data models
│   │   │   │   ├── Admin.java          # Represents an admin user
│   │   │   │   ├── Announcement.java   # Represents announcements
│   │   │   │   ├── Club.java           # Represents the club and its operations
│   │   │   │   ├── Event.java          # Represents events in the club
│   │   │   │   ├── Member.java         # Represents a club member
│   │   │   │   └── Person.java         # Base class for Member and Admin
│   │   │   ├── controller/            # Controllers for UI
│   │   │   │   ├── DashboardController.java  # Manages the dashboard view
│   │   │   │   ├── EditEventController.java  # Manages event editing
│   │   │   │   ├── LoginController.java      # Manages login functionality
│   │   │   │   ├── ProfileController.java    # Manages user profiles
│   │   │   │   └── SignUpController.java     # Manages user sign-up
│   │   │   ├── database/              # Database management
│   │   │   │   ├── DataManager.java         # Handles data persistence
│   │   │   │   └── DatabaseManager.java     # Manages SQLite database connection
│   │   └── module-info.java           # Module configuration
│   ├── resources/
│   │   ├── club/
│   │   │   ├── Dashboard.fxml         # FXML for dashboard UI
│   │   │   ├── EditEvent.fxml         # FXML for editing events
│   │   │   ├── Login.fxml             # FXML for login screen
│   │   │   ├── Profile.fxml           # FXML for profile screen
│   │   │   └── SignUp.fxml            # FXML for sign-up screen
│   │   └── META-INF/
│   │       └── MANIFEST.MF            # Manifest file
├── test/                              # Unit tests (if applicable)
└── pom.xml                            # Maven configuration                         
```

### Key Classes  
- **App.java**: Entry point for the application. Manages scene switching and global state.  
- **Club.java**: Represents the club and manages members, admins, events, and announcements.  
- **DataManager.java**: Handles data persistence and retrieval from the SQLite database.  
- **DashboardController.java**: Manages the dashboard view, including statistics, event table, and announcements.  
- **Event.java**: Represents an event with attributes like name, date, time, location, and description.  

### OOP Principles in Use  
- **Encapsulation**:  
    - All fields in model classes (e.g., `Event`, `Member`) are private with public getters and setters.  
    - Validation logic is implemented in setters.  
- **Inheritance**:  
    - `Person` is a base class for `Member` and `Admin`, sharing common attributes like name and email.  
- **Polymorphism**:  
    - The `displayInfo` method in `Person` is overridden in `Member` and `Admin`.  
- **Abstraction**:  
    - Abstract methods in `Person` enforce implementation in subclasses.  
- **Composition**:  
    - `Club` contains lists of `Member`, `Admin`, `Event`, and `Announcement` objects.  

## Setup Instructions  

### Prerequisites  
- Java Development Kit (JDK) 21 or higher.  
- Maven 3.8 or higher.  
- SQLite database.  

### Steps  
1. **Clone the Repository**:  
     ```bash  
     git clone https://github.com/your-repo/integradev-club-management.git  
     cd integradev-club-management  
     ```  

2. **Build the Project**:  
     ```bash  
     mvn clean install  
     ```  

3. **Run the Application**:  
     ```bash  
     mvn javafx:run  
     ```  

4. **Database Setup**:  
     - The application automatically initializes the SQLite database and creates necessary tables on the first run.  

## How to Contribute  
1. Fork the repository.  
2. Create a new branch for your feature or bug fix:  
     ```bash  
     git checkout -b feature/your-feature-name
     ```  
3. Commit your changes:  
     ```bash  
     git commit -m "Add feature description"  
     ```  
4. Push to your branch:  
     ```bash  
     git push origin feature/your-feature-name
     ```  
5. Create a pull request.  

## License  
This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.  
