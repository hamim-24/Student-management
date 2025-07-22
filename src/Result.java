import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Result {

    private String questionCode;
    private String questionName;
    private List<String> name;
    private List<String> ID;
    private List<Integer> roll;
    private List<Double> cgpa;

    Map<String, Account> accounts;
    Question question;

    public Result(String questionCode) {

        this.questionCode = questionCode;
        question = Main.getQuestionMap().get(questionCode);
        this.questionName = question.getExamName();

        name = new ArrayList<>();
        ID = new ArrayList<>();
        roll = new ArrayList<>();
        cgpa = new ArrayList<>();

        accounts = Main.getAccounts();
        for (Account account : accounts.values()) {
            if (account instanceof StudentAccount) {
                StudentAccount studentAccount = (StudentAccount) account;
                name.add(studentAccount.getFirstName() + " " + studentAccount.getLastName());
                ID.add(studentAccount.getID());
                roll.add(studentAccount.getRoll());
                cgpa.add(studentAccount.getCg());
            }
        }
    }
    public List<String> getID() {
        return ID;
    }
    public List<Integer> getRoll() {
        return roll;
    }
    public List<Double> getCgpa() {
        return cgpa;
    }
    public List<String> getName() {
        return name;
    }
    public String getQuestionCode() {
        return questionCode;
    }
    public String getQuestionName() {
        return questionName;
    }
}
