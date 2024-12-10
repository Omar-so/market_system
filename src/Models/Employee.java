/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

/**
 *
 * @author omar-so
 */
public class Employee extends Person{
    
    private String salary;
    private String Id;
    public Employee(String Id, String Usename, String salary) {
        super(" ", Usename, " ");
        this.salary = salary;
        this.Id = Id;
    }

    public String getSalary() {
        return salary;
    }

    public String getId() {
        return Id;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public void setId(String Id) {
        this.Id = Id;
    }
    
    
    
}
