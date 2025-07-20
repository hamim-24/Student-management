import java.io.Serializable;
import java.util.List;

public class Question {

    private String questionName;
    private String questionCode;

    List<SingleQuestion> singleQuestions;

    public Question(List<SingleQuestion> singleQuestions, String questionName, String questionCode) {
        this.questionName = questionName;
        this.questionCode = questionCode;
        this.singleQuestions = singleQuestions;
    }
    
    public String getQuestionName() {
        return questionName;
    }
    
    public String getQuestionCode() {
        return questionCode;
    }

    public List<SingleQuestion> getSingleQuestions() {
        return singleQuestions;
    }
}
