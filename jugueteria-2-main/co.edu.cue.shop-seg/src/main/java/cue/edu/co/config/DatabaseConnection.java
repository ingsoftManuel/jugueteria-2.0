package cue.edu.co.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
  private static String url = "jdbc:mysql://localhost:3306/toys";
  private static String user = "root";
  private static String password = "";
  private static Connection connection;

  private DatabaseConnection() {
  }
  public static Connection getConnection() {
    if(connection == null){
      try {
        connection = DriverManager.getConnection(url, user, password);
        return connection;
      } catch (SQLException e) {
        throw new RuntimeException("Error connecting to the database", e);
      }
    }
    return connection;
  }
}
