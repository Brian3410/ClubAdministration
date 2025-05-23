# Club Administration System  

## Overview  
The **Club Administration System** is a Java-based MVC (Model-View-Controller) application designed to manage club operations, including members, admins, events, and announcements. It provides a user-friendly interface built with JavaFX and ensures data persistence using SQLite. The system adheres to Object-Oriented Programming (OOP) principles, ensuring modularity, scalability, and maintainability 

## Features  

### User Management  
- Admins and members can log in and manage their profiles.
- Admins can edit the status and general information of members.

### Event Management
- Admins can create and edit events.  
- Members can view upcoming events.  

### Announcements  
- Admins can post and delete announcements visible to all members.  

### Dashboard  
- Displays club statistics, including total members and upcoming events.  
- Provides quick navigation to key features for admins.

### Data Persistence  
- All data (members, admins, events, announcements) is stored in an SQLite database.  

### Secure Remember Me  
- Passwords are securely encrypted and stored in properties source files.

## Setup Instructions

### Prerequisites
- Java Development Kit (JDK) 21 or higher.
- Maven 3.8 or higher.

### Steps
1. **Clone the Repository**:
     ```bash  
     git clone https://github.com/Brian3410/ClubAdministration.git
     ```  

2. **Build the Project**:
     ```bash  
     mvn clean install  
     ```  

3. **Run the Application**:
     ```bash  
     mvn javafx:run  
     ```
   Alternatively, you can run the MainApp class directly from your IDE.
   The executable jar file from the 'out/artifacts/integradev_jar' directory can also be run using the command:
    ```bash  
    java -jar out/artifacts/integradev_jar/integradev.jar
    ```

4. **Database Setup**:
    - The application automatically initializes the SQLite database and creates necessary tables on the first run.

## Technologies Used  
- **Java**: Core programming language.  
- **JavaFX**: For building the graphical user interface (GUI).  
- **SQLite**: For data persistence.  
- **Maven**: For dependency management and project build.  

## Project Structure  
``` 
src/
├── main/
│   ├── java/
│   │   ├── club/
│   │   │   ├── MainApp.java          # Main application entry point
│   │   │   ├── App.java              # Application class
│   │   │   ├── model/                 # Data models
│   │   │   │   ├── Admin.java          # Represents an admin user
│   │   │   │   ├── Announcement.java   # Represents announcements
│   │   │   │   ├── Club.java           # Represents the club and its operations
│   │   │   │   ├── Event.java          # Represents events in the club
│   │   │   │   ├── EventAction.java    # Enum for event actions
│   │   │   │   ├── EventManager.java     # Manages event-related operations
│   │   │   │   ├── EventObserver.java    # Observer for event changes
│   │   │   │   ├── Member.java         # Represents a club member
│   │   │   │   ├── MembershipManager.java  # Manages member-related operations
│   │   │   │   ├── MembershipRecord.java  # Represents membership records
│   │   │   │   ├── MembershipStatus.java  # Enum for membership status
│   │   │   │   └── Person.java         # Base class for Member and Admin
│   │   │   ├── controller/            # Controllers for UI
│   │   │   │   ├── AddEventController.java  # Manages event creation
│   │   │   │   ├── BaseController.java        # Base controller for shared functionality
│   │   │   │   ├── DashboardController.java  # Manages the dashboard view
│   │   │   │   ├── EditEventController.java  # Manages event editing
│   │   │   │   ├── EditMemberController.java  # Manages member editing
│   │   │   │   ├── EventListController.java  # Manages event list view
│   │   │   │   ├── LoginController.java      # Manages login functionality
│   │   │   │   ├── MemberListController.java  # Manages member list view
│   │   │   │   ├── ProfileController.java    # Manages user profiles
│   │   │   │   └── SignUpController.java     # Manages user sign-up
│   │   │   ├── database/              # Database management
│   │   │   │   ├── DataManager.java         # Handles data persistence
│   │   │   │   └── DatabaseManager.java     # Manages SQLite database connection
│   │   └── module-info.java           # Module configuration
│   ├── resources/
│   │   ├── club/
│   │   │   ├── AddEvent.fxml         # FXML for adding events
│   │   │   ├── Dashboard.fxml         # FXML for dashboard UI
│   │   │   ├── EditEvent.fxml         # FXML for editing events
│   │   │   ├── EditMember.fxml       # FXML for editing member details
│   │   │   ├── EventList.fxml         # FXML for event list view
│   │   │   ├── Login.fxml             # FXML for login screen
│   │   │   ├── MemberList.fxml       # FXML for member list view
│   │   │   ├── Profile.fxml           # FXML for profile screen
│   │   │   └── SignUp.fxml            # FXML for sign-up screen
│   │   └── META-INF/
│   │       └── MANIFEST.MF            # Manifest file                    
```

## Documentation
### JavaDoc
- JavaDoc is generated for all classes and methods. You can find the documentation in the `docs/JavaDoc` directory.

### UML Class Diagram
- UML Class Diagrams are generated for 'controller', 'database' and 'model' packages. You can find the diagrams in the `docs/UML` directory.

#### Whole System UML Class Diagram
<div align="center">
  <img src="https://github.com/Brian3410/ClubAdministration/blob/main/docs/UML/WholeUML.png" alt="Whole UML Diagram" width="70%">
</div>

#### Model Layer UML Class Diagram
<div align="center">
  <img src="https://github.com/Brian3410/ClubAdministration/blob/main/docs/UML/ModelUML.png" alt="Model UML Diagram" width="70%">
</div>

#### Controller Layer UML Class Diagram
<div align="center">
  <img src="https://github.com/Brian3410/ClubAdministration/blob/main/docs/UML/ControllerUML.png" alt="Controller UML Diagram" width="70%">
</div>

#### Database Layer UML Class Diagram
<div align="center">
  <img src="https://github.com/Brian3410/ClubAdministration/blob/main/docs/UML/DatabaseUML.png" alt="Database UML Diagram" width="70%">
</div>

### Key Classes  
- **MainApp.java**: Entry point for the application. 
- **App.java**: Initializes the JavaFX application and sets up the primary stage. Manages scene switching and global state.
- **Club.java**: Represents the club and manages members, admins, events, and announcements.  
- **DataManager.java**: Handles data persistence and retrieval from the SQLite database.  
- **DashboardController.java**: Manages the dashboard view, including statistics, event table, and announcements.  
- **Event.java**: Represents an event with attributes like name, date, time, location, and description.  

### OOP Principles in Use  
- **Encapsulation**:  
    - All fields in model classes (e.g., `Event`, `Member`) are private with public getters and setters.  For example, the `Announcement`, private fields (`id`, `message`, `dateTime`) are hidden from external access.
    - Access to these fields is controlled through public getter and setter methods.
    - Validation logic is implemented in setters. For example, the 'setMessage' method in the `Announcement` class checks if the message is not empty before setting it.
- **Inheritance**:  
    - `Person` is a base class for `Member` and `Admin`, sharing common attributes like name and email.  
    - Specialized fields and behaviors are added in the child classes
- **Polymorphism**:  
    - The `displayInfo` method in `Person` is overridden in `Member` and `Admin`.  
    - The `EventObserver` interface allows different classes to implement the `onEventUpdate` method, enabling dynamic behavior when events occur.
- **Abstraction**:  
    - Abstract methods in `Person` enforce implementation in subclasses (a blueprint for its subclasses).
    - The `EventObserver` interface defines a contract without implementation details for event handling, allowing different classes to implement their own versions of the `onEventUpdate` method.
- **Composition**:  
    - `Club` contains `Member`, `Admin`, `Event`, and `Announcement` objects, representing a "has-a" relationship.

### Design Patterns Used
Several design patterns are implemented:
- **Observer Pattern**: Used for event notifications through `EventObserver` interface, `EventManager` class, and `EventAction` enum
- **Separation of Concerns**: Responsibilities are divided among specialized classes (`MembershipManager`, `EventManager`)

### MVC Architecture
- **Model**: Represents the data and business logic (e.g., `Member`, `Admin`, `Event`, `Announcement` classes).
- **View**: The user interface components (e.g., FXML files).
- **Controller**: Handles user input and updates the model and view (e.g., `LoginController`, `DashboardController`).

## License  
This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.  
