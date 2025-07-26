import java.util.HashMap;
import java.util.Map;

public class StudentAccount extends Account {

    private String year;
    private int roll;
    private double cg = 0.0;
    private final String status;
    private Map<String, Boolean> EXAM_DONE;
    private Map<String, String> resultInfo;
    private double mark = 0.0;
    private int correct = 0;
    private int incorrect = 0;

    public StudentAccount(String id, String password, String email, String firstName, String lastName, String gender, String dob, String year, int roll, String department, String status, double cg) {

        super(id, password, email, firstName, lastName, gender, dob, department, status);
        EXAM_DONE = new HashMap<>();
        resultInfo = new HashMap<>();
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

    public void setEXAM_DONE(String code, boolean EXAM_DONE) {
        this.EXAM_DONE.put(code, EXAM_DONE);
    }

    public Map<String, Boolean> getEXAM_DONE() {
        return this.EXAM_DONE;
    }

    public Map<String, String> getResultInfo() {
        return resultInfo;
    }

    public void setResultInfo(String code, String resultInfo) {
        this.resultInfo.put(code, resultInfo);
    }

    public double getMark() {
        return mark;
    }

    public void setMark(double mark) {
        this.mark = mark;
    }

    public int getCorrect() {
        return correct;
    }

    public void setCorrect(int correct) {
        this.correct = correct;
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

