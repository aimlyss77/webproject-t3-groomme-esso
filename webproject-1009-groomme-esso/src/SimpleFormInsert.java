
/**
 * @file SimpleFormInsert.java
 */
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/SimpleFormInsert")
public class SimpleFormInsert extends HttpServlet {
   private static final long serialVersionUID = 1L;

   public SimpleFormInsert() {
      super();
   }

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      String userName = request.getParameter("userName");
      String name = request.getParameter("name");
      String phone = request.getParameter("phone");
      String email = request.getParameter("email");
      String address = request.getParameter("address");
      String date = request.getParameter("date");
      String time = request.getParameter("time");
      

      Connection connection = null;
      String insertSql = " INSERT INTO myTable (id, USER, NAME, PHONE, EMAIL, ADDRESS, DATE, TIME) values (default, ?, ?, ?,?,?,?,?)";

      try {
         DBConnection.getDBConnection();
         connection = DBConnection.connection;
         PreparedStatement preparedStmt = connection.prepareStatement(insertSql);
         preparedStmt.setString(1, userName);
         preparedStmt.setString(2, name);
         preparedStmt.setString(3, phone);
         preparedStmt.setString(4, email);
         preparedStmt.setString(5, address);
         preparedStmt.setString(6, date);
         preparedStmt.setString(7, time);
         preparedStmt.execute();
         connection.close();
      } catch (Exception e) {
         e.printStackTrace();
      }

      // Set response content type
      response.setContentType("text/html");
      PrintWriter out = response.getWriter();
      String title = "Insert Data to DB table";
      String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + "transitional//en\">\n";
      out.println(docType + //
            "<html>\n" + //
            "<head><title>" + title + "</title></head>\n" + //
            "<body bgcolor=\"#f0f0f0\">\n" + //
            "<h2 align=\"center\">" + title + "</h2>\n" + //
            "<ul>\n" + //

            "  <li><b>User Name</b>: " + userName + "\n" + //
            "  <li><b>Customer Name</b>: " + name + "\n" + //
            "  <li><b>Customer Phone</b>: " + phone + "\n" + //
            "  <li><b>Customer Email</b>: " + email + "\n" + //
            "  <li><b>Customer Address</b>: " + address + "\n" + //
            "  <li><b>Appointment Date</b>: " + date + "\n" + //
            "  <li><b>Appointment Time</b>: " + time + "\n" + //

            "</ul>\n");

      out.println("<a href=/webproject/simpleFormSearch.html>Search Data</a> <br>");
      out.println("</body></html>");
   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      doGet(request, response);
   }

}
