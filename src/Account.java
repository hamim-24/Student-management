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
        return String.format("""
                        ID: %s
                        Name: %s %s
                        Email: %s
                        Date of Birth: %s
                        Gender: %s
                        Date: %s""",
                ID, firstName, lastName, email, dob, gender, date.toString());
    }
}
