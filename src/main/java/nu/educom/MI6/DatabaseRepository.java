package nu.educom.MI6;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;


public class DatabaseRepository {
  /**
   * Get database connection
   *
   * @return a Connection object
   */

  public Connection connectWithDatabase() throws SQLException {

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

  Agent readAgentByServiceNr(String serviceNumber) {
    String sql = "SELECT * FROM agents WHERE `service_number`=?";

    try {
      Connection conn = connectWithDatabase();
      PreparedStatement preparedStmt = conn.prepareStatement(sql);
      preparedStmt.setString(1, serviceNumber);
      ResultSet rs = preparedStmt.executeQuery();
      boolean exists = rs.next();
      if (exists) {
        int id = rs.getInt("id");
        String serviceNum = rs.getString("service_number");
        String secretCode = rs.getString("secret_code");
        boolean active = rs.getBoolean("active");
        boolean licenseToKill = rs.getBoolean("license_to_kill");
        Date date = rs.getDate("license_valid_until");
        return new Agent(id, serviceNum, secretCode, active, licenseToKill, date);
      }

      rs.close();
      preparedStmt.close();

    } catch (SQLException ex) {
      System.out.println(ex.getMessage());
    }

    return null;
  }

  public Agent authenticateAgent(String serviceNumber, String secret) {
    String sql = "SELECT * FROM agents WHERE `service_number`=? AND `secret_code`=?";

    try {
      Connection conn = connectWithDatabase();
      PreparedStatement preparedStmt = conn.prepareStatement(sql);
      preparedStmt.setString(1, serviceNumber);
      preparedStmt.setString(2, secret);
      ResultSet rs = preparedStmt.executeQuery();
      if (rs.next()) {
        int id = rs.getInt("id");
        String serviceNum = rs.getString("service_number");
        String secretCode = rs.getString("secret_code");
        boolean active = rs.getBoolean("active");
        boolean licenseToKill = rs.getBoolean("license_to_kill");
        Date date = rs.getDate("license_valid_until");
        System.out.println("id: " + id + "serviceNum: " + serviceNum + "secret code: " + secretCode + "active: " + active + "license to kill " + licenseToKill + "license valid until" + date);
        return new Agent(id, serviceNum, secretCode, active, licenseToKill, date);
      }
      rs.close();
      preparedStmt.close();

    } catch (SQLException ex) {
      System.out.println(ex.getMessage());
    }
    return null;


  }

  public List<LoginAttempt> getLastLoginAttempts(String serviceNumber) {
    String sql = "SELECT * FROM login_attempts WHERE `service_number`= ?";
    List<LoginAttempt> loginAttempts = new ArrayList<>();
    try {
      Connection conn = connectWithDatabase();
      PreparedStatement preparedStmt = conn.prepareStatement(sql);
      preparedStmt.setString(1, serviceNumber);
      ResultSet rs = preparedStmt.executeQuery();

      while (rs.next()) {
        loginAttempts.add(new LoginAttempt(rs.getInt("attempt_id"), rs.getInt("agent_id"), rs.getTimestamp("login_time").toLocalDateTime(), rs.getBoolean("successful_attempt")));


      }

    } catch (SQLException ex) {
      System.out.println(ex.getMessage());
    }
    return loginAttempts;
  }

  public void insertLoginAttempt (LoginAttempt attempt) {
    String sql = "INSERT INTO login_attempts(`agent_id`, login_time, successful_attempt) VALUES (?, ?, ?)";

    try {
      Connection conn = connectWithDatabase();
      PreparedStatement preparedStmt = conn.prepareStatement(sql);
      preparedStmt.setInt(1, attempt.getAgentId());
      preparedStmt.setTimestamp(2, Timestamp.valueOf(attempt.getLoginTime()));
      preparedStmt.setBoolean(3, attempt.isSuccessfulAttempt());
      preparedStmt.executeUpdate();

      preparedStmt.close();

    } catch (SQLException ex) {
      System.out.println(ex.getMessage());
    }

  }
}






