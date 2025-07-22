public class StudentAccount extends Account {

    private String year;
    private int roll;
    private double cg = 0.0;
    private final String status;
    private boolean EXAM_DONE = false;
    private String resultInfo = "";
    private double mark = 0.0;
    private int correct = 0;
    private int incorrect = 0;

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
    public double getMark() {
        return mark;
    }
    public void setMark(int mark) {
        this.mark = mark;
    }
    public int getCorrect() {
        return correct;
    }
    public void setCorrect(int correct) {
        this.incorrect = correct;
    }
    public int getIncorrect() {
        return incorrect;
    }
    public void setIncorrect(int incorrect) {
        this.incorrect = incorrect;
    }

    @Override
    public String toString() {
        return super.toString() + "\nYear: " + year + "\nRoll: " + roll + "\nGPA: " + cg;
    }
}

