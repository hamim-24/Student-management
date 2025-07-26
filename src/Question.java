import java.util.List;

public class Question {

    private String examName;
    private String questionCode;
    private String year;
    private String department;

    List<SingleQuestion> singleQuestions;

    public Question(List<SingleQuestion> singleQuestions, String questionCode, String year, String department, String examName) {
        this.questionCode = questionCode;
        this.singleQuestions = singleQuestions;
        this.year = year;
        this.department = department;
        this.examName = examName;
    }

    public String getYear() {
        return year;
    }

    public String getDepartment() {
        return department;
    }

    public String getExamName() {
        return examName;
    }

    public String getQuestionCode() {
        return questionCode;
    }

    public List<SingleQuestion> getSingleQuestions() {
        return singleQuestions;
    }
}
