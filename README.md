# Restaurant Management System

## Overview
The Restaurant Management System is a JavaFX-based desktop application designed to streamline restaurant operations. The system provides a user-friendly interface for administrators to log in and access a dashboard for managing restaurant activities. The application includes a login form with database authentication, a draggable window, and a modern UI with potential for visual effects to enhance user experience.

## Features
- **Login Interface**: A secure login form for administrators with fields for username and password, validated against a database.
- **Database Integration**: Connects to a database to authenticate admin credentials using SQL queries.
- **Draggable Window**: The application window is borderless (transparent stage) and can be dragged by clicking and dragging anywhere on the interface.
- **Error Handling**: Displays alerts for empty fields or incorrect username/password combinations.
- **Dashboard Access**: Upon successful login, the system redirects to a dashboard (via `dashboard.fxml`), which likely includes features for managing restaurant operations (e.g., menu, orders, staff).
- **Visual Effects** (Proposed):
  - **Hover Effects**: Buttons (e.g., Login, Close) scale and change color on hover.
  - **Fade-In Animation**: The login form fades in when the application starts.
  - **Button Pulse**: The Login button pulses subtly to draw attention.
  - **Background Image Transition**: The restaurant image (`restau`) fades in when the username field is interacted with.

## Technologies Used
- **JavaFX**: For building the desktop application’s UI and handling user interactions.
- **JDBC**: For database connectivity to authenticate admin credentials.
- **SQL**: For querying the database (e.g., `admin` table with `username` and `password` columns).
- **FXML**: For defining the UI layout (`FXMLDocument.fxml` for login, `dashboard.fxml` for the dashboard).
- **CSS** (Optional): Can be used to style the JavaFX components for a modern look.
- **Maven/Gradle** (Assumed): For managing dependencies, if applicable.

## File Structure
- `src/main/java/restaurantmanagement/FXMLDocumentController.java`: Controller for the login interface, handling login logic, database queries, and window dragging.
- `src/main/resources/restaurantmanagement/FXMLDocument.fxml`: FXML file defining the login form’s UI (with fields, buttons, and an image).
- `src/main/resources/restaurantmanagement/dashboard.fxml`: FXML file for the dashboard interface (not provided but referenced).
- `src/main/java/restaurantmanagement/Database.java`: Utility class for establishing database connections (assumed based on `Database.connectDb()`).
- `src/main/resources/images/`: Directory for the restaurant image (`restau` ImageView).

## Setup Instructions
1. **Clone or Download**: Clone the repository or download the project files.
2. **Set Up Database**:
   - Create a database with an `admin` table containing `username` and `password` columns.
   - Update the `Database.java` class (assumed) with your database URL, username, and password.
3. **Install Dependencies**:
   - Ensure JavaFX SDK is installed and configured in your IDE (e.g., IntelliJ, Eclipse).
   - Add JavaFX dependencies to your project (via Maven/Gradle or manually).
4. **Run the Application**:
   - Open the project in an IDE.
   - Run the main class (assumed to load `FXMLDocument.fxml`).
   - The login window will appear, allowing admin login.
5. **Customize**:
   - Modify `FXMLDocument.fxml` to adjust the UI layout.
   - Update `FXMLDocumentController.java` to add new features or effects.
   - Style the application using a CSS file (e.g., `styles.css`) applied to the FXML.

## Dependencies
- **JavaFX**: Version compatible with your JDK (e.g., JavaFX 17).
- **JDBC Driver**: For the database (e.g., MySQL Connector/J if using MySQL).
- **Database**: A relational database (e.g., MySQL, PostgreSQL) with an `admin` table.

## Visual Effects (Proposed Implementation)
To enhance the UI, the following effects can be added:
- **CSS for Hover Effects** (in `styles.css`):
  ```css
  .button:hover {
      -fx-scale-x: 1.05;
      -fx-scale-y: 1.05;
      -fx-background-color: #4CAF50;
      -fx-transition: all 0.3s ease;
  }
  ```
- **Fade-In Animation** (in `FXMLDocumentController.java`):
  ```java
  @Override
  public void initialize(URL url, ResourceBundle rb) {
      FadeTransition fade = new FadeTransition(Duration.millis(1000), main_form);
      fade.setFromValue(0.0);
      fade.setToValue(1.0);
      fade.play();
      username.setOnAction(event -> {
          loginBtn.fire();
          restau.setVisible(true);
      });
  }
  ```
- **Button Pulse Animation** (in CSS):
  ```css
  #loginBtn {
      -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 10, 0, 0, 0);
      -fx-animation: pulse 2s infinite;
  }
  @keyframes pulse {
      0% { -fx-scale-x: 1; -fx-scale-y: 1; }
      50% { -fx-scale-x: 1.1; -fx-scale-y: 1.1; }
      100% { -fx-scale-x: 1; -fx-scale-y: 1; }
  }
  ```
- **JavaScript for Image Fade-In** (in `initialize` method):
  ```java
  restau.setOpacity(0);
  username.setOnAction(event -> {
      loginBtn.fire();
      FadeTransition fadeImage = new FadeTransition(Duration.millis(500), restau);
      fadeImage.setFromValue(0.0);
      fadeImage.setToValue(1.0);
      fadeImage.play();
      restau.setVisible(true);
  });
  ```

## Notes
- The provided code is a login interface; additional features (e.g., dashboard functionality) are assumed to exist in `dashboard.fxml`.
- The `Database` class is not provided but assumed to handle database connections.
- The restaurant image (`restau`) is referenced but not specified; ensure it exists in the resources folder.
- The application uses a transparent stage, which may require additional CSS for a polished look.
- Error handling is robust for login but could
