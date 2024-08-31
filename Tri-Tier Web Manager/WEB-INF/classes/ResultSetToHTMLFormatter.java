/* Name: Mitchel Mertens
Title: Tri-Tier Web Manager
Date: December 5, 2023
*/

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class ResultSetToHTMLFormatter {

    public static synchronized String getHtmlRows(ResultSet results) throws SQLException {
        StringBuilder htmlRows = new StringBuilder();
        ResultSetMetaData metaData = results.getMetaData();
        int columnCount = metaData.getColumnCount();

        // Set the table header row
        htmlRows.append("<tr bgcolor=\"red\">");
        for (int i = 1; i <= columnCount; i++) {
            htmlRows.append("<th>");
            htmlRows.append(metaData.getColumnName(i));
            htmlRows.append("</th>");
        }
        htmlRows.append("</tr>");

        int count = 0;
        while (results.next()) {
            htmlRows.append(count % 2 == 0 ? "<tr bgcolor=\"gray\">" : "<tr bgcolor=\"white\">");
            for (int i = 1; i <= columnCount; i++) {
                htmlRows.append("<td>");
                // Handle null values
                String value = results.getString(i);
                htmlRows.append(value != null ? value : "NULL");
                htmlRows.append("</td>");
            }
            htmlRows.append("</tr>");
            count++;
        }
        return htmlRows.toString();
    }
}
