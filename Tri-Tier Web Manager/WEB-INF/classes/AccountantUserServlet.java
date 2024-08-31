/* Name: Mitchel Mertens
Title: Tri-Tier Web Manager
Date: December 5, 2023
*/

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Properties;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import javax.sql.DataSource;

import com.mysql.cj.jdbc.MysqlDataSource;

@jakarta.servlet.annotation.WebServlet("/AccountantUserServlet")
public class AccountantUserServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private DataSource dataSource;

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        HttpSession session = request.getSession();

        try {
            // Load database properties from the configuration file
            Properties properties = new Properties();
            try (FileInputStream filein = new FileInputStream(
                    "C:/Program Files/Apache Software Foundation/Tomcat 10.1_Tomcat10114/webapps/Project-4/WEB-INF/lib/accountant.properties")) {
                properties.load(filein);
            }

            // Create a MySQL data source and set connection properties
            MysqlDataSource mysqlDataSource = new MysqlDataSource();
            mysqlDataSource.setUrl(properties.getProperty("MYSQL_DB_URL"));
            mysqlDataSource.setUser(properties.getProperty("MYSQL_DB_USERNAME"));
            mysqlDataSource.setPassword(properties.getProperty("MYSQL_DB_PASSWORD"));

            // Use the data source to obtain a connection
            dataSource = mysqlDataSource;

            // Get the user's selection ready to process
            String cmdParam = request.getParameter("cmd");

            // Check if cmdParam is not null and a valid integer
            if (cmdParam == null || !cmdParam.matches("\\d+")) {
                session.setAttribute("message", "Invalid or missing command value!");
                response.sendRedirect("accountantHome.jsp");
                return;  // Exit the method to avoid further processing
            }

            int cmdValue = Integer.parseInt(cmdParam);

            // Construct the appropriate RPC command based on cmdValue
            String command = null;
            switch (cmdValue) {
                case 1:
                    command = "{call Get_The_Maximum_Status_Of_All_Suppliers()}";
                    break;
                case 2:
                    command = "{call Get_The_Sum_Of_All_Parts_Weights()}";
                    break;
                case 3:
                    command = "{call Get_The_Total_Number_Of_Shipments()}";
                    break;
                case 4:
                    command = "{call Get_The_Name_Of_The_Job_With_The_Most_Workers()}";
                    break;
                case 5:
                    command = "{call List_The_Name_And_Status_Of_All_Suppliers()}";
                    break;
                default:
                    session.setAttribute("message", "Invalid command value!");
                    response.sendRedirect("accountantHome.jsp");
                    return;  // Exit the method for invalid command values
            }

            // Prepare and execute the CallableStatement
            try (Connection connection = dataSource.getConnection();
                 CallableStatement statement = connection.prepareCall(command)) {

                boolean hasResultSet = statement.execute();

                if (hasResultSet) {
                    ResultSet resultSet = statement.getResultSet();

                    // Use ResultSetToHTMLFormatter to convert ResultSet to HTML rows
                    String htmlRows = ResultSetToHTMLFormatter.getHtmlRows(resultSet);

                    // Set the formatted HTML rows as an attribute in the session
                    session.setAttribute("htmlRows", htmlRows);
                } else {
                    int updateCount = statement.getUpdateCount();
                    // Handle cases where the stored procedure returns an update count
                    // For example, set a message attribute in the session
                    session.setAttribute("message", "Procedure executed successfully. Update count: " + updateCount);
                }

                session.setAttribute("cmdValue", cmdValue);
            } catch (Exception e) { // Catch more general Exception
                session.setAttribute("message", "Error executing the SQL statement: " + e.getMessage());
                throw new ServletException("Error executing the SQL statement: " + e.getMessage(), e);
            }

        } catch (IOException e) {
            session.setAttribute("message", "Error loading properties file: " + e.getMessage());
            throw new ServletException("Error loading properties file: " + e.getMessage(), e);
        } finally {
            // Forward to the JSP page
            RequestDispatcher dispatcher = request.getRequestDispatcher("accountantHome.jsp");
            dispatcher.forward(request, response);
        }
    }
}
