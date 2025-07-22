import java.util.List;

public class Question {

    private String examName;
    private String questionCode;
    private String year;
    private String department;

    List<SingleQuestion> singleQuestions;

    public Question(List<SingleQuestion> singleQuestions, String examName, String questionCode) {
        this.examName = examName;
        this.questionCode = questionCode;
        this.singleQuestions = singleQuestions;
    }
    public void setYear(String year) {
        this.year = year;
    }
    public String getYear() {
        return year;
    }
    public void setDepartment(String department) {
        this.department = department;
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
