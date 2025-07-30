package model;

import java.util.List;

public class Question {

    private String session;
    private final String examName;
    private final String questionCode;
    private final String year;
    private final String department;

    List<SingleQuestion> singleQuestions;

    public Question(List<SingleQuestion> singleQuestions, String questionCode, String year, String department, String examName, String session) {

        this.questionCode = questionCode;
        this.singleQuestions = singleQuestions;
        this.year = year;
        this.department = department;
        this.examName = examName;
        this.session = session;
    }

    public String getSession() {
        return session;
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
