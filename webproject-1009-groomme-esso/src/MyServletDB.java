import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/MyServletDB")
public class MyServletDB extends HttpServlet {
   private static final long serialVersionUID = 1L;
   static String url = "jdbc:mysql://ec2-52-15-153-166.us-east-2.compute.amazonaws.com:3306/MyGroommeDB";
   static String user = "newmysqlremoteuser";
   static String password = "mypassword";
   static Connection connection = null;

   public MyServletDB() {
      super();
   }

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      response.setContentType("text/html;charset=UTF-8");
      response.getWriter().println("-------- MySQL JDBC Connection Testing ------------<br>");
      try {
         Class.forName("com.mysql.cj.jdbc.Driver");//("com.mysql.jdbc.Driver");
      } catch (ClassNotFoundException e) {
         System.out.println("Where is your MySQL JDBC Driver?");
         e.printStackTrace();
         return;
      }
      response.getWriter().println("MySQL JDBC Driver Registered!<br>");
      connection = null;
      try {
         connection = DriverManager.getConnection(url, user, password);
      } catch (SQLException e) {
         System.out.println("Connection Failed! Check output console");
         e.printStackTrace();
         return;
      }
      if (connection != null) {
         response.getWriter().println("You made it, take control your database now!<br>");
      } else {
         System.out.println("Failed to make connection!");
      }
      try {
         String selectSQL = "SELECT * FROM MyGroommeTable";// WHERE MYUSER LIKE ?";
//         String theUserName = "user%";
         response.getWriter().println(selectSQL + "<br>");
         response.getWriter().println("------------------------------------------<br>");
         PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
//         preparedStatement.setString(1, theUserName);
         ResultSet rs = preparedStatement.executeQuery();
         while (rs.next()) {
            String id = rs.getString("ID");
            String username = rs.getString("USER");
            String name = rs.getString("NAME");
            String phone = rs.getString("PHONE");
            String email = rs.getString("EMAIL");
            String address = rs.getString("ADDRESS");
            String date = rs.getString("DATE");
            String time = rs.getString("TIME");
          
            response.getWriter().append("CUSTOMER ID: " + id + ", ");
            response.getWriter().append("CUSTOMER REFERENCE NUMBER: " + username + ", ");
            response.getWriter().append("CUSTOMER NAME: " + name + ", ");
            response.getWriter().append("CUSTOMER PHONE: " + phone + ", ");
            response.getWriter().append("CUSTOMER EMAIL: " + email + "<br>");
            response.getWriter().append("CUSTOMER ADDRESS: " + address + ", ");
            response.getWriter().append("CUSTOMER APPOINTMENT DATE: " + date + ", ");
            response.getWriter().append("CUSTOMER APPOINTMENT TIME: " + time + ", ");
         }
      } catch (SQLException e) {
         e.printStackTrace();
      }
   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      doGet(request, response);
   }
}