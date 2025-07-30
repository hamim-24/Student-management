package model;

import java.util.List;

public class Question {

    private String session;
    private final String examName;
    private final String questionCode;
    private final String year;
    private final String department;
    private final String courseId;

    List<SingleQuestion> singleQuestions;

    public Question(List<SingleQuestion> singleQuestions, String questionCode, String year, String department, String examName, String session, String courseId) {

        this.questionCode = questionCode;
        this.singleQuestions = singleQuestions;
        this.year = year;
        this.department = department;
        this.examName = examName;
        this.session = session;
        this.courseId = courseId;
    }

    public String getCourseId() {
        return courseId;
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
