
// EmployeeDAO.java
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object (DAO) for Employee operations.
 * Handles all CRUD (Create, Read, Update, Delete) operations with the database.
 */
public class EmployeeDAO {
  private Connection connection;

  public EmployeeDAO(Connection connection) {
    this.connection = connection;
  }

  /**
   * Adds a new employee to the database.
   * Uses PreparedStatement to prevent SQL injection.
   * 
   * @param employee The Employee object to add.
   */
  public void addEmployee(Employee employee) {
    String sql = "INSERT INTO employees (name, email, department) VALUES (?, ?, ?)";
    try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
      pstmt.setString(1, employee.getName());
      pstmt.setString(2, employee.getEmail());
      pstmt.setString(3, employee.getDepartment());
      pstmt.executeUpdate();
      System.out.println("Employee '" + employee.getName() + "' added successfully.");
    } catch (SQLException e) {
      System.err.println("Error adding employee: " + e.getMessage());
      // Specific error handling for unique constraint violation (email)
      if (e.getMessage().contains("UNIQUE constraint failed: employees.email")) {
        System.err.println("Error: An employee with this email already exists.");
      }
    }
  }

  /**
   * Retrieves all employees from the database.
   * 
   * @return A list of Employee objects.
   */
  public List<Employee> getAllEmployees() {
    List<Employee> employees = new ArrayList<>();
    String sql = "SELECT id, name, email, department FROM employees";
    try (PreparedStatement pstmt = connection.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery()) { // executeQuery for SELECT statements

      while (rs.next()) { // Iterate through the result set
        int id = rs.getInt("id");
        String name = rs.getString("name");
        String email = rs.getString("email");
        String department = rs.getString("department");
        employees.add(new Employee(id, name, email, department));
      }
    } catch (SQLException e) {
      System.err.println("Error retrieving employees: " + e.getMessage());
    }
    return employees;
  }

  /**
   * Updates an employee's email by their ID.
   * Uses PreparedStatement for safe updates.
   * 
   * @param id       The ID of the employee to update.
   * @param newEmail The new email address.
   */
  public void updateEmployeeEmail(int id, String newEmail) {
    String sql = "UPDATE employees SET email = ? WHERE id = ?";
    try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
      pstmt.setString(1, newEmail);
      pstmt.setInt(2, id);
      int rowsAffected = pstmt.executeUpdate(); // executeUpdate for INSERT, UPDATE, DELETE
      if (rowsAffected > 0) {
        System.out.println("Employee with ID " + id + " updated successfully.");
      } else {
        System.out.println("No employee found with ID " + id + " to update.");
      }
    } catch (SQLException e) {
      System.err.println("Error updating employee: " + e.getMessage());
      if (e.getMessage().contains("UNIQUE constraint failed: employees.email")) {
        System.err.println("Error: The new email already exists for another employee.");
      }
    }
  }

  /**
   * Deletes an employee from the database by their ID.
   * Uses PreparedStatement for safe deletion.
   * 
   * @param id The ID of the employee to delete.
   */
  public void deleteEmployee(int id) {
    String sql = "DELETE FROM employees WHERE id = ?";
    try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
      pstmt.setInt(1, id);
      int rowsAffected = pstmt.executeUpdate();
      if (rowsAffected > 0) {
        System.out.println("Employee with ID " + id + " deleted successfully.");
      } else {
        System.out.println("No employee found with ID " + id + " to delete.");
      }
    } catch (SQLException e) {
      System.err.println("Error deleting employee: " + e.getMessage());
    }
  }
}
