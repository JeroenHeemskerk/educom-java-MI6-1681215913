package nu.educom.MI6;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


public class DatabaseRepository {
  /**
   * Get database connection
   *
   * @return a Connection object
   * @throws SQLException
   */

  public static Connection connectWithDatabase() throws SQLException {

    Connection conn = null;

    try (FileInputStream f = new FileInputStream("C:\\Users\\Lydia van Gammeren\\IdeaProjects\\educom-java-MI6\\src\\main\\java\\nu\\educom\\MI6\\db.properties")) {

      // load the properties file
      Properties pros = new Properties();
      pros.load(f);

      // assign db parameters
      String url = pros.getProperty("url");
      String user = pros.getProperty("user");
      String password = pros.getProperty("password");

      // create a connection to the database
      Class.forName("com.mysql.jdbc.Driver");
      conn = DriverManager.getConnection(url, user, password);
    } catch (IOException e) {
      System.out.println(e.getMessage());
    } catch (ClassNotFoundException e) {
      throw new RuntimeException(e);
    }
    return conn;
  }
}


//    try {
//      Class.forName("com.mysql.jdbc.Driver");
//      Connection con = DriverManager.getConnection(
//        "jdbc:mysql://localhost:3306/mi6", "mi6_agents", "agent_test");
//      Statement stmt = con.createStatement();
//      ResultSet rs = stmt.executeQuery("select * from agents WHERE service_number = '002'");
//      while (rs.next())
//        System.out.println(rs.getInt(1) + "  " + rs.getString(2) + "  " + rs.getString(3));
//      con.close();
//    } catch (Exception e) {
//      System.out.println(e);
//    }



