package model;

import launcher.Main;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Result {

    private final String questionCode;
    private final String questionName;
    private final int totalQuestions;
    private final String department;
    private final String year;
    private final List<String> names;
    private final List<String> IDs;
    private final List<Integer> rolls;
    private final List<Double> marks;
    private final List<Double> cgpas;
    private final List<Integer> correct;
    private final List<Integer> incorrect;
    private final String session;
    private final String courseId;

    Map<String, Account> accounts;
    Question question;

    public Result(String questionCode, String department, String year, String session, String courseId) {

        this.session = session;
        this.courseId = courseId;
        this.department = department;
        this.year = year;
        this.questionCode = questionCode;
        this.question = Main.getQuestionMap().get(questionCode);
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

        for (Account account : accounts.values()) {
            if (account instanceof StudentAccount) {
                StudentAccount studentAccount = (StudentAccount) account;
                Boolean courseIs = false;
                for (Course c : studentAccount.getCourses()) {
                    if (c.getCourseId().equals(courseId)) {
                        courseIs = true;
                        break;
                    }
                }
                if (courseIs && studentAccount.getDepartment().equals(question.getDepartment()) && studentAccount.getYear().equals(question.getYear()) && studentAccount.getSession().equals(session)) {
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
    public String getSession() {
        return session;
    }

    public String getCourseId() {
        return courseId;
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

    public String getDepartment() {
        return department;
    }

    public String getYear() {
        return year;
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

    public List<Integer> getCorrect() {
        return correct;
    }

    public List<Integer> getIncorrect() {
        return incorrect;
    }
}
