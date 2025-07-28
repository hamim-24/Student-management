package model;

import java.util.Date;

public class Account {

    private final String ID;
    private final String password;
    private final String email;
    private final String firstName;
    private final String lastName;
    private final String gender;
    private final String dob;
    private final Date date;
    private final String department;
    private final String status;

    public Account(String ID, String password, String email, String firstName, String lastName, String gender, String dob, String department, String status) {
        this.ID = ID;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.dob = dob;
        this.department = department;
        this.status = status;
        this.date = new Date();
    }

    public String getID() {
        return ID;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getGender() {
        return gender;
    }

    public String getDob() {
        return dob;
    }

    public String getDepartment() {
        return department;
    }

    public String getStatus() {
        return status;
    }

    public String toString() {
        return String.format("""
                        ID: %s
                        Name: %s %s
                        Email: %s
                        Date of Birth: %s
                        Gender: %s
                        Status: %s
                        Department : %s
                        Date: %s""",
                ID, firstName, lastName, email, dob, gender, status, department, date);
    }
}
