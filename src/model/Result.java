package model;

import launcher.Main;

import java.util.List;
import java.util.Map;

public class Result {

    private final double mark;
    private final int correct;
    private final int incorrect;
    private final String questionCode;
    private final String resultInfo;

    StudentAccount studentAccount;
    Question question;

    public Result(StudentAccount account, double mark, int correct, int incorrect, String questionCode, String resultInfo) {

        this.studentAccount = account;
        this.mark = mark;
        this.correct = correct;
        this.incorrect = incorrect;
        this.questionCode = questionCode;
        this.resultInfo = resultInfo;
        this.question = Main.getQuestionMap().get(questionCode);
    }

    public String getResultInfo() {
        return resultInfo;
    }

    public String getYear() {
        return studentAccount.getYear();
    }

    public String getDepartment() {
        return studentAccount.getDepartment();
    }

    public double getCg() {
        return studentAccount.getCg();
    }

    public String getId() {
        return studentAccount.getID();
    }
    public int getRoll() {
        return studentAccount.getRoll();
    }
    public String getSession() {
        return studentAccount.getSession();
    }

    public String getExamCode() {
        return questionCode;
    }
    public double getMark() {
        return mark;
    }
    public int getCorrect() {
        return correct;
    }
    public int getIncorrect() {
        return incorrect;
    }
    public String getQuestionCode() {
        return questionCode;
    }
    public StudentAccount getStudentAccount() {
        return studentAccount;
    }

}
