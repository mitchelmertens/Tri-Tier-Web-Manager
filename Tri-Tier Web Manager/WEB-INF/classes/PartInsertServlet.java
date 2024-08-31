/* Name: Mitchel Mertens
Title: Tri-Tier Web Manager
Date: December 5, 2023
*/

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

@SuppressWarnings("serial")
public class PartInsertServlet extends HttpServlet {
    private String dbURL;
    private String dbUser;
    private String dbPassword;

    @Override
    public void init() throws ServletException {
        // Load database connection details from properties file
        Properties properties = new Properties();
        InputStream input = getServletContext().getResourceAsStream("/WEB-INF/lib/dataentryuser.properties");
        try {
            properties.load(input);
            dbURL = properties.getProperty("MYSQL_DB_URL");
            dbUser = properties.getProperty("MYSQL_DB_USERNAME");
            dbPassword = properties.getProperty("MYSQL_DB_PASSWORD");
        } catch (IOException e) {
            throw new ServletException("Error loading database properties", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve form data
        String pnum = request.getParameter("pnum");
        String pname = request.getParameter("pname");
        String color = request.getParameter("color");
        String weight = request.getParameter("weight");
        String city = request.getParameter("city");

        // Database operations to insert part record
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(dbURL, dbUser, dbPassword);

            String insertQuery = "INSERT INTO parts (pnum, pname, color, weight, city) VALUES (?, ?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(insertQuery);
            preparedStatement.setString(1, pnum);
            preparedStatement.setString(2, pname);
            preparedStatement.setString(3, color);
            preparedStatement.setString(4, weight);
            preparedStatement.setString(5, city);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                // Insert successful
                String successMessage = "<span style='color: white; background-color: blue; font-size: 16pt;'>New part record: (" + pnum + ", " + pname + ", " + color + ", " + weight + ", " + city + ") - successfully entered into the database.</span>";
                request.getSession().setAttribute("message", successMessage);
            } else {
                // Insert failed
                String errorMessage = "<span style='color: white; background-color: red; font-size: 16pt;'>Error executing the SQL statement: " + insertQuery + "</span>";
                request.getSession().setAttribute("message", errorMessage);
            }

            response.sendRedirect("dataEntryHome.jsp"); // Redirect back to dataEntryHome.jsp
        } catch (ClassNotFoundException | SQLException e) {
            throw new ServletException("Database error", e);
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                throw new ServletException("Error closing database resources", e);
            }
        }
    }
}
