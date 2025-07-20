import java.io.Serializable;

public class Question implements Serializable {

    private String questionName;
    private String questionCode;
    private String question;
    private String[] options;
    private String answer;

    public Question(String question, String questionName, String questionCode, String[] options, String answer) {
        this.questionName = questionName;
        this.questionCode = questionCode;
        this.question = question;
        this.options = options;
        this.answer = answer;
    }
    
    public String getQuestionName() {
        return questionName;
    }
    
    public String getQuestionCode() {
        return questionCode;
    }
    
    public String getQuestion() {
        return question;
    }
    
    public String[] getOptions() {
        return options;
    }
    
    public String getAnswer() {
        return answer;
    }
}
