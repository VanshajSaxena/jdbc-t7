
// Employee.java
/**
 * Represents an Employee entity.
 */
public class Employee {
  private int id;
  private String name;
  private String email;
  private String department;

  public Employee(int id, String name, String email, String department) {
    this.id = id;
    this.name = name;
    this.email = email;
    this.department = department;
  }

  // Getters
  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getEmail() {
    return email;
  }

  public String getDepartment() {
    return department;
  }

  // Setters (for updating specific fields if needed, though DAO handles updates)
  public void setId(int id) {
    this.id = id;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setDepartment(String department) {
    this.department = department;
  }

  @Override
  public String toString() {
    return "Employee [ID=" + id + ", Name=" + name + ", Email=" + email + ", Department=" + department + "]";
  }
}
