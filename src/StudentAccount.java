
public class StudentAccount extends Account {

    private String year;
    private int roll;
    private double cg;
    private final String status;

    public StudentAccount(String id, String password, String email, String firstName, String lastName, String gender, String dob, String year,  int roll, String department, String status, double cg) {
        super(id, password, email, firstName, lastName, gender, dob, department, status);
        this.year = year;
        this.roll = roll;
        this.cg = cg;
        this.status = status;
    }

    public String getYear() {
        return year;
    }
    public int getRoll() {
        return roll;
    }
    public double getCg() {
        return cg;
    }

    @Override
    public String toString() {
        return super.toString() + "\nYear: " + year + "\nRoll: " + roll + "\nGPA: " + cg;
    }
}

