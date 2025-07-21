import java.util.List;

public class Question {

    private String examName;
    private String questionCode;

    List<SingleQuestion> singleQuestions;

    public Question(List<SingleQuestion> singleQuestions, String examName, String questionCode) {
        this.examName = examName;
        this.questionCode = questionCode;
        this.singleQuestions = singleQuestions;
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
