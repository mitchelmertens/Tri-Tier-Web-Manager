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
import java.sql.*;
import java.util.Properties;

@SuppressWarnings("serial")
public class ShipmentInsertServlet extends HttpServlet {
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
        String snum = request.getParameter("snum");
        String pnum = request.getParameter("pnum");
        String jnum = request.getParameter("jnum");
        String quantity = request.getParameter("quantity");

        // Database operations to insert shipment record
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(dbURL, dbUser, dbPassword);

            String insertQuery = "INSERT INTO shipments (snum, pnum, jnum, quantity) VALUES (?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(insertQuery);
            preparedStatement.setString(1, snum);
            preparedStatement.setString(2, pnum);
            preparedStatement.setString(3, jnum);
            preparedStatement.setString(4, quantity);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                // Insert successful
                String successMessage;
                if (Integer.parseInt(quantity) < 100) {
                    successMessage = "<span style='color: white; background-color: blue; font-size: 16pt;'>New shipments record: (" + snum + ", " + pnum + ", " + jnum + ", " + quantity + ") - successfully entered into database. Business logic not triggered.</span>";
                } else {
                    // Business logic is triggered
                    // Additional SQL operations
                    try (Statement statement = connection.createStatement()) {
                        statement.executeUpdate("DROP TABLE IF EXISTS beforeshipments;");
                        statement.executeUpdate("CREATE TABLE beforeshipments LIKE shipments;");
                        statement.executeUpdate("INSERT INTO beforeshipments SELECT * FROM shipments;");

                        // Update 'status' column in 'suppliers' table
                        String after = "UPDATE suppliers "
                                + "SET status = status + 5 "
                                + "WHERE suppliers.snum IN "
                                + "(SELECT DISTINCT snum FROM shipments "
                                + "WHERE shipments.quantity >= 100 "
                                + "AND NOT EXISTS (SELECT * FROM beforeshipments "
                                + "WHERE shipments.snum = beforeshipments.snum "
                                + "AND shipments.pnum = beforeshipments.pnum "
                                + "AND shipments.jnum = beforeshipments.jnum "
                                + "AND beforeshipments.quantity >= 100));";
                        statement.executeUpdate(after);

                        // Drop the temporary table 'beforeshipments'
                        statement.executeUpdate("DROP TABLE IF EXISTS beforeshipments;");
                    } catch (SQLException e) {
                        e.printStackTrace(); // Handle or log the exception appropriately
                    }

                    successMessage = "<span style='color: white; background-color: blue; font-size: 16pt;'>New shipments record: (" + snum + ", " + pnum + ", " + jnum + ", " + quantity + ") - successfully entered into database. Business logic triggered.</span>";
                }
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
