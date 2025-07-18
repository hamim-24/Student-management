
public class StudentAccount extends Account {

    private final String classNo;
    private final int roll;

    public StudentAccount(String id, String password, String email, String firstName, String lastName, String gender, String dob, String classno,  int roll) {
        super(id, password, email, firstName, lastName, gender, dob);
        this.classNo = classno;
        this.roll = roll;
    }

    public String getClassNo() {
        return classNo;
    }
    public int getRoll() {
        return roll;
    }

    @Override
    public String toString() {
        return super.toString() + "\nStatus: Student" + "\nClass: " + classNo + "\nRoll: " + roll;
    }
}

