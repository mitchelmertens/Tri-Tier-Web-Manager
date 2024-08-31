/* Name: Mitchel Mertens
Title: Tri-Tier Web Manager
Date: December 5, 2023
*/

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

import com.mysql.cj.jdbc.MysqlDataSource;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;

@SuppressWarnings("serial")
public class RootUserServlet extends HttpServlet {
    private Connection connection;
    private Statement statement;

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String sqlStatement = request.getParameter("sqlStatement");
        String message = "";

        String copyTable = "drop table if exists beforeshipments;";
        String copyTable2 = "create table beforeshipments like shipments;";
        String copyTable3 = "insert into beforeshipments select * from shipments;";

        String after = "update suppliers "
                + "set status = status + 5 "
                + "where suppliers.snum in "
                + "(select distinct snum from shipments "
                + "where shipments.quantity >= 100 "
                + "and "
                + "not exists (select * from beforeShipments "
                + "where shipments.snum = beforeshipments.snum "
                + "and shipments.pnum = beforeshipments.pnum "
                + "and shipments.jnum = beforeshipments.jnum "
                + "and beforeshipments.quantity >= 100 "
                + ")"
                + ");";
        String drop = "drop table beforeShipments;";

        try {
            connectToDatabase();
            statement = connection.createStatement();

            if (sqlStatement.toLowerCase().startsWith("select")) {
                ResultSet resultSet = statement.executeQuery(sqlStatement);
                message = getHtmlRows(resultSet);
            } else {
                statement.executeUpdate(copyTable);
                statement.executeUpdate(copyTable2);
                statement.executeUpdate(copyTable3);

                int rows;
                if (sqlStatement.toLowerCase().startsWith("insert into shipments")
                        || sqlStatement.toLowerCase().startsWith("update shipments")
                        || sqlStatement.toLowerCase().startsWith("replace into shipments values")
                        || sqlStatement.toLowerCase().startsWith("replace into shipments set")) {
                    rows = statement.executeUpdate(sqlStatement);
                    int rows2 = statement.executeUpdate(after);
                    message = "<tr bgcolor=#46FF00><td style=\"text-align:center\"><font color=green><b>The statement executed successfully.<br>"
                    	    + rows + " row(s) affected.<br><br> Business Logic Detected! - Updating Supplier Status.<br>Business Logic updated " + rows2 + " supplier status marks.</b></font></td></tr>";
                } else {
                    rows = statement.executeUpdate(sqlStatement);
                    message = "<tr bgcolor=#46FF00><td style=\"text-align:center\"><font color=green><b>The statement executed successfully.<br>"
                    	    + rows + " row(s) affected.<br><br> Business Logic Not Triggered!</b></font></td></tr>";
                }

                statement.executeUpdate(drop);
            }

            statement.close();
            connection.close();

        } catch (SQLException e) {
        	message = "<tr bgcolor=#ff0000><td style=\"text-align:center\"><font color=red><b>Error executing the SQL statement:</b><br>" + e.getMessage() + "</font></td></tr>";
        }

        HttpSession session = request.getSession();
        session.setAttribute("message", message);
        session.setAttribute("sqlStatement", sqlStatement);
        response.sendRedirect("rootHome.jsp");
    }

    private String getHtmlRows(ResultSet results) throws SQLException {
        StringBuilder htmlRows = new StringBuilder();
        ResultSetMetaData metaData = results.getMetaData();
        int columnCount = metaData.getColumnCount();

        htmlRows.append("<table border='1' style='border-collapse: collapse;'>");
        htmlRows.append("<tr style='background-color:red; color:white;'>");
        for (int i = 1; i <= columnCount; i++) {
            htmlRows.append("<th style='border: 1px solid black;'>").append(metaData.getColumnName(i)).append("</th>");
        }
        htmlRows.append("</tr>");

        int count = 0;
        while (results.next()) {
            if (count % 2 == 0) {
                htmlRows.append("<tr bgcolor=\"gray\">");
            } else {
                htmlRows.append("<tr bgcolor=\"white\">");
            }
            for (int i = 1; i <= columnCount; i++) {
                htmlRows.append("<td style='color:black; border: 1px solid black;'>").append(results.getString(i)).append("</td>");
            }
            htmlRows.append("</tr>");
            count++;
        }
        htmlRows.append("</table>");

        return htmlRows.toString();
    }


    public void connectToDatabase() {
        Properties properties = new Properties();
        FileInputStream filein = null;
        MysqlDataSource dataSource = null;

        try {
            filein = new FileInputStream("C:/Program Files/Apache Software Foundation/Tomcat 10.1_Tomcat10114/webapps/Project-4/WEB-INF/lib/root.properties");
            properties.load(filein);

            dataSource = new MysqlDataSource();
            dataSource.setUrl(properties.getProperty("MYSQL_DB_URL"));
            dataSource.setUser(properties.getProperty("MYSQL_DB_USERNAME"));
            dataSource.setPassword(properties.getProperty("MYSQL_DB_PASSWORD"));

            connection = dataSource.getConnection();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }
}
