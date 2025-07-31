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
        // Validate constructor parameters
        if (courseId == null || courseId.trim().isEmpty()) {
            throw new IllegalArgumentException("Course ID cannot be null or empty");
        }
        if (courseName == null || courseName.trim().isEmpty()) {
            throw new IllegalArgumentException("Course name cannot be null or empty");
        }
        if (credits <= 0) {
            throw new IllegalArgumentException("Credits must be greater than 0");
        }
        if (maxStudents <= 0) {
            throw new IllegalArgumentException("Maximum students must be greater than 0");
        }
        
        this.courseId = courseId.trim();
        this.courseName = courseName.trim();
        this.credits = credits;
        this.enrolledStudentIds = new HashSet<>();
        this.maxStudents = maxStudents;
        this.currentStudents = 0;
    }

    public int getCurrentStudents() {
        return currentStudents;
    }

    public void setMaxStudents(int maxStudents) {
        if (maxStudents <= 0) {
            throw new IllegalArgumentException("Maximum students must be greater than 0");
        }
        if (maxStudents < currentStudents) {
            throw new IllegalArgumentException("Cannot set max students less than current enrolled students");
        }
        this.maxStudents = maxStudents;
    }

    public int getMaxStudents() {
        return maxStudents;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        if (courseId == null || courseId.trim().isEmpty()) {
            throw new IllegalArgumentException("Course ID cannot be null or empty");
        }
        this.courseId = courseId.trim();
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        if (courseName == null || courseName.trim().isEmpty()) {
            throw new IllegalArgumentException("Course name cannot be null or empty");
        }
        this.courseName = courseName.trim();
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        if (credits <= 0) {
            throw new IllegalArgumentException("Credits must be greater than 0");
        }
        this.credits = credits;
    }

    public Set<String> getEnrolledStudentIds() {
        return new HashSet<>(enrolledStudentIds); // Return a copy to prevent external modification
    }

    public void enrollStudent(String studentId) {
        if (studentId == null || studentId.trim().isEmpty()) {
            throw new IllegalArgumentException("Student ID cannot be null or empty");
        }
        if (currentStudents >= maxStudents) {
            throw new IllegalStateException("Course is full. Cannot enroll more students.");
        }
        if (enrolledStudentIds.contains(studentId)) {
            throw new IllegalStateException("Student is already enrolled in this course.");
        }
        
        enrolledStudentIds.add(studentId.trim());
        currentStudents++;
    }

    public void removeStudent(String studentId) {
        if (studentId == null || studentId.trim().isEmpty()) {
            throw new IllegalArgumentException("Student ID cannot be null or empty");
        }
        if (!enrolledStudentIds.contains(studentId)) {
            throw new IllegalStateException("Student is not enrolled in this course.");
        }
        
        enrolledStudentIds.remove(studentId.trim());
        currentStudents--;
    }
    
    @Override
    public String toString() {
        return String.format("Course{id='%s', name='%s', credits=%d, currentStudents=%d, maxStudents=%d}", 
                           courseId, courseName, credits, currentStudents, maxStudents);
    }
} 