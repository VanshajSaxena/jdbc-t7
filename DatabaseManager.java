
// DatabaseManager.java
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Manages database connection and table creation.
 */
public class DatabaseManager {

  /**
   * Establishes a connection to the SQLite database.
   * 
   * @param dbUrl The URL of the database (e.g., "jdbc:sqlite:employee.db").
   * @return A Connection object if successful, null otherwise.
   */
  public static Connection getConnection(String dbUrl) {
    Connection connection = null;
    try {
      // Load the SQLite JDBC driver (not strictly necessary for modern JDBC, but good
      // practice)
      Class.forName("org.sqlite.JDBC");
      connection = DriverManager.getConnection(dbUrl);
      System.out.println("Connected to database: " + dbUrl);
    } catch (SQLException e) {
      System.err.println("Error connecting to database: " + e.getMessage());
      // In a real application, you might log this error more formally
    } catch (ClassNotFoundException e) {
      System.err.println("SQLite JDBC driver not found. Make sure it's in your classpath.");
    }
    return connection;
  }

  /**
   * Creates the 'employees' table if it doesn't already exist.
   * 
   * @param connection The active database connection.
   */
  public static void createTable(Connection connection) {
    String createTableSQL = "CREATE TABLE IF NOT EXISTS employees (" +
        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
        "name TEXT NOT NULL," +
        "email TEXT UNIQUE NOT NULL," +
        "department TEXT" +
        ");";
    try (Statement statement = connection.createStatement()) {
      statement.execute(createTableSQL);
      System.out.println("Table 'employees' checked/created successfully.");
    } catch (SQLException e) {
      System.err.println("Error creating table: " + e.getMessage());
    }
  }
}
