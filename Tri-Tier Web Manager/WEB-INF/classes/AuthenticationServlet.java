/* Name: Mitchel Mertens
Title: Tri-Tier Web Manager
Date: December 5, 2023
*/

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.*;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

@SuppressWarnings("serial")
public class AuthenticationServlet extends HttpServlet {

   @Override
   protected void doPost(HttpServletRequest request, HttpServletResponse response)
         throws ServletException, IOException {
      // Retrieve username and password from the request
      String username = request.getParameter("username");
      String password = request.getParameter("password");

      // Perform authentication logic
      boolean isAuthenticated = authenticateUser(username, password);

      if (isAuthenticated) {
         // Redirect to the corresponding home page
         String homePage = getHomePage(username);
         response.sendRedirect(homePage);
      } else {
         // Redirect to an error page in case of authentication failure
         response.sendRedirect("errorpage.html");
      }
   }


   private boolean authenticateUser(String username, String password) {
      try {
         InputStream inputStream = getServletContext().getResourceAsStream("/WEB-INF/lib/credentials.csv");
         BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));

         String line;
         while ((line = reader.readLine()) != null) {
            String[] credentials = line.split(",");
            if (credentials.length == 2 && credentials[0].equals(username) && credentials[1].equals(password)) {
               return true;
            }
         }

      } catch (IOException e) {
         e.printStackTrace();
      }

      return false;
   }

   private String getHomePage(String username) {
      switch (username) {
         case "root":
            return "rootHome.jsp";
         case "client":
            return "clientHome.jsp";
         case "dataentryuser":
            return "dataEntryHome.jsp";
         case "accountant":
            return "accountantHome.jsp";
         default:
            return "errorpage.html";
      }
   }
}
