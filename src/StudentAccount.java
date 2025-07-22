public class StudentAccount extends Account {

    private String year;
    private int roll;
    private double cg;
    private final String status;
    private boolean EXAM_DONE = false;
    private String resultInfo = "";

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
    public void setCg(double cg) {
        this.cg = cg;
    }
    public void setEXAM_DONE(boolean EXAM_DONE) {
        this.EXAM_DONE = EXAM_DONE;
    }
    public boolean getEXAM_DONE() {
        return this.EXAM_DONE;
    }
    public String getResultInfo() {
        return resultInfo;
    }
    public void setResultInfo(String resultInfo) {
        this.resultInfo = resultInfo;
    }

    @Override
    public String toString() {
        return super.toString() + "\nYear: " + year + "\nRoll: " + roll + "\nGPA: " + cg;
    }
}

