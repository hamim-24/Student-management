import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Result {

    private String questionCode;
    private String questionName;
    private int totalQuestions;
    private List<String> names;
    private List<String> IDs;
    private List<Integer> rolls;
    private List<Double> marks;
    private List<Double> cgpas;
    private List<Integer> correct;
    private List<Integer> incorrect;

    Map<String, Account> accounts;
    Question question;

    public Result(String questionCode) {

        this.questionCode = questionCode;
        question = Main.getQuestionMap().get(questionCode);
        this.questionName = question.getExamName();
        this.totalQuestions = question.getSingleQuestions().size();
        accounts = Main.getAccounts();

        names = new ArrayList<>();
        IDs = new ArrayList<>();
        rolls = new ArrayList<>();
        cgpas = new ArrayList<>();
        marks = new ArrayList<>();
        incorrect = new ArrayList<>();
        correct = new ArrayList<>();

        accounts = Main.getAccounts();
        for (Account account : accounts.values()) {
            if (account instanceof StudentAccount) {
                StudentAccount studentAccount = (StudentAccount) account;
                if (studentAccount.getDepartment().equals(question.getDepartment()) && studentAccount.getYear().equals(question.getYear())) {
                    names.add(studentAccount.getFirstName() + " " + studentAccount.getLastName());
                    IDs.add(studentAccount.getID());
                    rolls.add(studentAccount.getRoll());
                    cgpas.add(studentAccount.getCg());
                    marks.add(studentAccount.getMark());
                    correct.add(studentAccount.getCorrect());
                    incorrect.add(studentAccount.getIncorrect());
                }
            }
        }
    }
    public List<String> getIDs() {
        return IDs;
    }
    public List<Integer> getRolls() {
        return rolls;
    }
    public List<Double> getCgpas() {
        return cgpas;
    }
    public List<String> getNames() {
        return names;
    }
    public int getTotalQuestions() {
        return totalQuestions;
    }
    public String getQuestionCode() {
        return questionCode;
    }
    public String getQuestionName() {
        return questionName;
    }
    public List<Double> getMarks() {
        return marks;
    }
}
