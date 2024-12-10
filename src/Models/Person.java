/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

/**
 *
 * @author omar-so
 */
abstract class Person {

    private String Email;
    private String Usename;
    private String Password;

    public Person(String Email, String Usename, String Password) {
        this.Email = Email;
        this.Usename = Usename;
        this.Password = Password;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public void setUsename(String Usename) {
        this.Usename = Usename;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }

    public String getUsename() {
        return Usename;
    }

    public String getPassword() {
        return Password;
    }
        
}
