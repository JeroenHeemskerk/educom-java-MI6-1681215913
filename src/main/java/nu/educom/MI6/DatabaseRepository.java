package nu.educom.MI6;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
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

      // create a connection to the database
      Class.forName("com.mysql.jdbc.Driver");
      conn = DriverManager.getConnection(url, pros);
    } catch (IOException e) {
      System.out.println(e.getMessage());
    } catch (ClassNotFoundException e) {
      throw new RuntimeException(e);
    }
    return conn;
  }

  public static void selectAll() {
    String sql = "SELECT `service_number`, `secret_code`, `active` FROM agents";

    try (Connection conn = connectWithDatabase();
         Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery(sql)) {

      // loop through the result set
      while (rs.next()) {
        System.out.println(rs.getString("service_number") + "\t" +
          rs.getString("secret_code") + "\t" + rs.getBoolean("active") + "\t");


      }
    } catch (SQLException ex) {
      System.out.println(ex.getMessage());
    }
  }

  public static boolean authenticateAgent(String serviceNumber, String secret) throws SQLException {
    String sql = "SELECT * FROM agents WHERE `service_number`=? AND `secret_code`=?";

    try {
      Connection conn = connectWithDatabase();
      PreparedStatement preparedStmt = conn.prepareStatement(sql);
      preparedStmt.setString(1, serviceNumber);
      preparedStmt.setString(2, secret);
      ResultSet rs = preparedStmt.executeQuery();
      boolean exists = rs.next();
      if(exists) {
        return true;
      }

      rs.close();
      preparedStmt.close();

    } catch (SQLException ex) {
      System.out.println(ex.getMessage());
    }

    return false;
  }

  public static List<LoginAttempt> getLastLoginAttempts(String serviceNumber) {
    String sql = "SELECT * FROM login_attempts WHERE `service_number`= ?";
    List<LoginAttempt> loginAttempts;
    try {
      Connection conn = connectWithDatabase();
      PreparedStatement preparedStmt = conn.prepareStatement(sql);
      preparedStmt.setString(1, serviceNumber);
      ResultSet rs = preparedStmt.executeQuery();

      while (rs.next()) {
        System.out.println(rs.getString("service_number") + "\t" +
          rs.getString("attempt_id"));


      }

    } catch (SQLException ex) {
      System.out.println(ex.getMessage());
    }
    return null;
  }

  public static void setLoginAttempt(String serviceNumber, boolean succesful) {
    String sql = "INSERT INTO login_attempts(`service_number`, login_time, successful_attempt) VALUES (?, ?, ?)";

    try {
      Connection conn = connectWithDatabase();
      PreparedStatement preparedStmt = conn.prepareStatement(sql);
      preparedStmt.setString(1, serviceNumber);
      preparedStmt.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
      preparedStmt.setBoolean(3, succesful);
      int rs = preparedStmt.executeUpdate();

      //rs.close();
      preparedStmt.close();

    } catch (SQLException ex) {
      System.out.println(ex.getMessage());
    }

  }
}






