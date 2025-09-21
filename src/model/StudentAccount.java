package model;

import launcher.Main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudentAccount extends Account {

    private String year;
    private int roll;
    private double cg;
    private String session;
    private Map<String, Boolean> EXAM_DONE;
    private Map<String, Result> results;
    private List<Course> courses;
    private String promotion;

    public StudentAccount(String id, String password, String email, String firstName, String lastName, String gender, String dob, String year, int roll, String department, String status, double cg, String session) {

        super(id, password, email, firstName, lastName, gender, dob, department, status);
        EXAM_DONE = new HashMap<>();
        results = new HashMap<>();
        courses = new ArrayList<>();
        this.year = year;
        this.roll = roll;
        this.session = session;
        this.promotion = "";
        int totalExam = 1;
        double totalCG = cg;
        for (Result r : Main.getResultList()) {
            String idTemp = r.getId();
            String departmentTemp = r.getDepartment();
            String sessionTemp = r.getSession();
            if (idTemp.equals(this.getID()) && sessionTemp.equals(this.getSession()) && departmentTemp.equals(this.getDepartment())) {
                totalCG += r.getCg();
                totalExam++;
            }
        }
        this.cg = totalCG / totalExam;
    }

    public void setPromotion(String promotion) {
        this.promotion = promotion;
    }

    public String getPromotion() {
        return promotion;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public String getSession() {
        return session;
    }

    public String getYear() {
        return year;
    }

    public int getRoll() {
        return roll;
    }

    public double getCg() {
        return cg;
    }

    public void setCg(double cg) {
        this.cg = cg;
    }

    public void setEXAM_DONE(String code, boolean EXAM_DONE) {
        this.EXAM_DONE.put(code, EXAM_DONE);
    }

    public Map<String, Boolean> getEXAM_DONE() {
        return this.EXAM_DONE;
    }

    public void setResults(String code, Result resultObject) {
        this.results.put(code, resultObject);
    }


    @Override
    public String toString() {
        return super.toString() + "\nYear: " + year + "\nSession: " + session + "\nRoll: " + roll + "\nGPA: " + cg + "\nCourses:\n" + showCourses();
    }

    public String showCourses() {
        StringBuilder courses = new StringBuilder();
        for (Course course : getCourses()) {
            courses.append(" - ").append(course.showCourses()).append("\n");
        }
        return courses.toString();
    }
    public void setYear(String year) {
        this.year = year;
    }
}

