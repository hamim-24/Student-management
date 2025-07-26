
public class TeacherAccount extends Account {

    private final String status;

    public TeacherAccount(String id, String password, String email, String firstName, String lastName, String gender, String dob, String department, String status) {

        super(id, password, email, firstName, lastName, gender, dob, department, status);
        this.status = status;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
