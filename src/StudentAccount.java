
public class StudentAccount extends Account {

    public StudentAccount(String id, String password, String email, String firstName, String lastName,  String gender, String dob) {
        super(id, password, email, firstName, lastName, gender, dob);
    }

    @Override
    public String toString() {
        return super.toString() + "\nStatus: Student";
    }
}

