import java.util.Date;

public class Account {

    private String ID;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private String gender;
    private String dob;
    private Date date;

    public Account(String id, String password,  String email, String firstName, String lastName,  String gender, String dob) {
        this.ID = id;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.dob = dob;
        this.date = new Date();
    }

    public String getID() {
        return ID;
    }
    public String getPassword() {
        return password;
    }

    public String toString() {
        return String.format("ID: %s\n" +
                "Name: %s %s\n" +
                "Email: %s %s\n" +
                "Date: %s",
                ID, firstName, lastName, email, date.toString());
    }
}
