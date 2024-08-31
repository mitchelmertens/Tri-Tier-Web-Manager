# Tri-Tier Web Manager

Tri-Tier Web Manager is a robust web-based application designed to facilitate interaction with a MySQL database through multiple user roles, each with distinct functionalities and permissions. This application utilizes a three-tier architecture comprising a front-end web interface, a middle-tier of Java servlets, and a back-end MySQL database. The architecture ensures separation of concerns, enhances security, and provides a structured approach to database management and operations.

## Features

Tri-Tier Web Manager supports four distinct user roles, each with a specific set of capabilities:

1. **Root-Level User Interface:**
   - Allows direct execution of SQL commands, including `SELECT`, `INSERT`, `UPDATE`, `REPLACE`, and `DELETE`.
   - Includes three control buttons:
     - **Execute Command:** Runs the SQL command in the input window.
     - **Reset Form:** Clears the input window.
     - **Clear Results:** Erases displayed data.

2. **Client-Level User Interface:**
   - Similar to the Root-Level Interface but without the ability to execute updates due to limited privileges.
   - Primarily used for querying the database.

3. **Data Entry-Level User Interface:**
   - Designed for naïve data-entry users who interact with the database through form-based templates.
   - Utilizes the `PreparedStatement` interface for secure execution of SQL commands.
   - Provides two buttons for each form:
     - **Enter Data:** Submits the form data to be processed by the back-end.
     - **Clear Data and Results:** Clears the form and any displayed results.

4. **Accountant-Level User Interface:**
   - Provides access to predefined reports generated from the database.
   - Utilizes the `CallableStatement` interface to execute remote procedure calls (RPC) on the database server.
   - Allows selection from a list of reports to retrieve specific data.

## System Architecture

The application is built on a three-tier architecture:

1. **Front-End:** HTML and JavaScript web interfaces that run on any standard web browser. This tier collects user input and displays results.

2. **Middle-Tier:** Java servlets hosted on an Apache Tomcat web server. This tier handles application logic, including processing user input, managing business logic, and communicating with the database.

3. **Back-End:** A MySQL database that stores all the necessary data. The database is structured and maintained through SQL scripts and supports different user roles with various privilege levels.

## Business Logic

- The business logic for the application is managed in the middle-tier servlets. For instance, the application increments the status of suppliers by 5 whenever they are involved in an insertion or update of a shipment record with a quantity of 100 or more.
- This logic is implemented to ensure that business rules are consistently applied, enhancing the integrity and reliability of the data.

## Database Connectivity

- Database connections are managed through properties files, ensuring that different user roles connect using the appropriate credentials and settings.
- Four properties files are used:
  - **Root-Level Users:** Full access to execute all commands.
  - **Client-Level Users:** Restricted to querying capabilities.
  - **Data Entry-Level Users:** Limited to inserting and updating data via prepared statements.
  - **Accountant-Level Users:** Limited to executing stored procedures for report generation.

## Technologies Used

- **Java Servlets:** Manage server-side processing and business logic.
- **JDBC (Java Database Connectivity):** Interfaces with the MySQL database for executing SQL commands.
- **MySQL Database:** Stores application data, supporting multiple user roles with defined access privileges.
- **Apache Tomcat:** Serves as the application server for deploying and running the servlets.
- **HTML/CSS/JavaScript:** Provides the structure, style, and interactivity of the web-based user interfaces.

## Setup and Installation

1. **Database Setup:**
   - Install MySQL and create the database.
   - Ensure the database is populated with initial data to match the application's requirements.

2. **Application Server Setup:**
   - Install Apache Tomcat and deploy the Java servlets to the server.
   - Configure the server to connect to the MySQL database using the appropriate JDBC drivers.

3. **Properties Files Configuration:**
   - Create and configure the properties files for each user role, specifying the database connection details and user credentials.

4. **Run the Application:**
   - Start the Tomcat server and access the application through a web browser.
   - Use the root, client, data entry, and accountant user interfaces to interact with the database.

## Usage

- Use the **root-level interface** for full database management capabilities.
- Utilize the **client-level interface** for querying the database without making changes.
- Employ the **data entry-level interface** for safe and straightforward data entry using forms.
- Access the **accountant-level interface** for generating reports based on predefined stored procedures.

## Error Handling

- The application includes error handling mechanisms to catch and display MySQL-side errors to the user.
- Errors are presented in a user-friendly manner, ensuring that users understand any issues that arise during operation.

## Conclusion

Tri-Tier Web Manager is a comprehensive tool for managing and interacting with a MySQL database through a structured, role-based approach. By separating user interactions into distinct roles and managing business logic through servlets, this application ensures secure, efficient, and user-friendly database management.