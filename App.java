
// Main.java
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

/**
 * Main class to run the Employee Database Application.
 * This class provides a simple console-based menu for CRUD operations.
 */
public class App {

  private static final String DB_URL = "jdbc:sqlite:employee.db"; // SQLite database file

  public static void main(String[] args) {
    // Initialize the database and DAO
    try (Connection connection = DatabaseManager.getConnection(DB_URL)) {
      if (connection != null) {
        System.out.println("Database connection established successfully.");
        DatabaseManager.createTable(connection); // Ensure the table exists
        EmployeeDAO employeeDAO = new EmployeeDAO(connection);
        runMenu(employeeDAO);
      } else {
        System.err.println("Failed to establish database connection. Exiting.");
      }
    } catch (SQLException e) {
      System.err.println("Database error: " + e.getMessage());
      e.printStackTrace();
    } catch (Exception e) {
      System.err.println("An unexpected error occurred: " + e.getMessage());
      e.printStackTrace();
    }
  }

  /**
   * Runs the interactive menu for the employee application.
   * 
   * @param employeeDAO The EmployeeDAO instance to perform operations.
   */
  private static void runMenu(EmployeeDAO employeeDAO) {
    Scanner scanner = new Scanner(System.in);
    int choice;

    do {
      System.out.println("\n--- Employee Database Menu ---");
      System.out.println("1. Add Employee");
      System.out.println("2. View All Employees");
      System.out.println("3. Update Employee Email");
      System.out.println("4. Delete Employee");
      System.out.println("5. Exit");
      System.out.print("Enter your choice: ");

      try {
        choice = Integer.parseInt(scanner.nextLine());

        switch (choice) {
          case 1:
            addEmployee(scanner, employeeDAO);
            break;
          case 2:
            viewAllEmployees(employeeDAO);
            break;
          case 3:
            updateEmployeeEmail(scanner, employeeDAO);
            break;
          case 4:
            deleteEmployee(scanner, employeeDAO);
            break;
          case 5:
            System.out.println("Exiting application. Goodbye!");
            break;
          default:
            System.out.println("Invalid choice. Please try again.");
        }
      } catch (NumberFormatException e) {
        System.out.println("Invalid input. Please enter a number.");
        choice = 0; // Set to 0 to continue loop
      } catch (Exception e) {
        System.err.println("An error occurred: " + e.getMessage());
        e.printStackTrace();
        choice = 0;
      }
    } while (choice != 5);

    scanner.close();
  }

  /**
   * Handles adding a new employee.
   * 
   * @param scanner     Scanner for user input.
   * @param employeeDAO EmployeeDAO instance.
   */
  private static void addEmployee(Scanner scanner, EmployeeDAO employeeDAO) {
    System.out.print("Enter employee name: ");
    String name = scanner.nextLine();
    System.out.print("Enter employee email: ");
    String email = scanner.nextLine();
    System.out.print("Enter employee department: ");
    String department = scanner.nextLine();

    Employee employee = new Employee(0, name, email, department); // ID will be auto-generated
    employeeDAO.addEmployee(employee);
    System.out.println("Employee added successfully!");
  }

  /**
   * Handles viewing all employees.
   * 
   * @param employeeDAO EmployeeDAO instance.
   */
  private static void viewAllEmployees(EmployeeDAO employeeDAO) {
    List<Employee> employees = employeeDAO.getAllEmployees();
    if (employees.isEmpty()) {
      System.out.println("No employees found.");
    } else {
      System.out.println("\n--- All Employees ---");
      employees.forEach(System.out::println);
    }
  }

  /**
   * Handles updating an employee's email.
   * 
   * @param scanner     Scanner for user input.
   * @param employeeDAO EmployeeDAO instance.
   */
  private static void updateEmployeeEmail(Scanner scanner, EmployeeDAO employeeDAO) {
    System.out.print("Enter employee ID to update: ");
    try {
      int id = Integer.parseInt(scanner.nextLine());
      System.out.print("Enter new email for employee ID " + id + ": ");
      String newEmail = scanner.nextLine();

      employeeDAO.updateEmployeeEmail(id, newEmail);
      System.out.println("Employee email updated successfully (if ID existed).");
    } catch (NumberFormatException e) {
      System.out.println("Invalid ID. Please enter a number.");
    }
  }

  /**
   * Handles deleting an employee.
   * 
   * @param scanner     Scanner for user input.
   * @param employeeDAO EmployeeDAO instance.
   */
  private static void deleteEmployee(Scanner scanner, EmployeeDAO employeeDAO) {
    System.out.print("Enter employee ID to delete: ");
    try {
      int id = Integer.parseInt(scanner.nextLine());
      employeeDAO.deleteEmployee(id);
      System.out.println("Employee deleted successfully (if ID existed).");
    } catch (NumberFormatException e) {
      System.out.println("Invalid ID. Please enter a number.");
    }
  }
}
