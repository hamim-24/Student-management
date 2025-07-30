package model;

import java.util.HashSet;
import java.util.Set;

public class Course {
    private String courseId;
    private String courseName;
    private int credits;
    private Set<String> enrolledStudentIds;
    private int maxStudents;
    private int currentStudents;

    public Course(String courseId, String courseName, int credits, int maxStudents) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.credits = credits;
        this.enrolledStudentIds = new HashSet<>();
        this.maxStudents = maxStudents;
        this.currentStudents = 0;
    }

    public int getCurrentStudents() {
        return currentStudents;
    }

    public void setMaxStudents(int maxStudents) {
        this.maxStudents = maxStudents;
    }

    public int getMaxStudents() {
        return  maxStudents;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public Set<String> getEnrolledStudentIds() {
        return enrolledStudentIds;
    }

    public void enrollStudent(String studentId) {
        enrolledStudentIds.add(studentId);
        currentStudents++;
    }

    public void removeStudent(String studentId) {
        enrolledStudentIds.remove(studentId);
        currentStudents--;
    }
} 