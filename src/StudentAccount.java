
public class StudentAccount extends Account {

    private String classNo;
    private int roll;
    private double cg;
    private final String status;

    public StudentAccount(String id, String password, String email, String firstName, String lastName, String gender, String dob, String classno,  int roll, String department, String status) {
        super(id, password, email, firstName, lastName, gender, dob, department, status);
        this.classNo = classno;
        this.roll = roll;
        this.cg = 0.0;
        this.status = status;
    }

    public String getClassNo() {
        return classNo;
    }
    public int getRoll() {
        return roll;
    }
    public double getCg() {
        return cg;
    }

    @Override
    public String toString() {
        return super.toString() + "\nClass: " + classNo + "\nRoll: " + roll + "\nGPA: " + cg;
    }
}

