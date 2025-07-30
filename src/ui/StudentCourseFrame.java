package ui;

import model.StudentAccount;

import javax.swing.*;
import java.awt.*;
import launcher.Main;
import model.Course;

import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StudentCourseFrame extends JFrame {
    private StudentAccount account;
    private Map<String, Course> courseMap;
    private DefaultListModel<String> allCoursesModel;
    private DefaultListModel<String> enrolledCoursesModel;
    private List<Course> enrolledCourses;
    private JList<String> allCoursesList;
    private JList<String> enrolledCoursesList;
    private JButton enrollButton;
    private JButton dropButton;

    public StudentCourseFrame(StudentAccount account) {
        // Validate account parameter
        if (account == null) {
            JOptionPane.showMessageDialog(null, "Invalid account information", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        this.enrolledCourses = new ArrayList<>();
        this.account = account;
        this.courseMap = Main.getCourseMap();
        
        // Validate courseMap
        if (this.courseMap == null) {
            JOptionPane.showMessageDialog(null, "Course data not available", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        setTitle("Course Management");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(700, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel(new GridLayout(1, 2, 20, 0));
        allCoursesModel = new DefaultListModel<>();
        enrolledCoursesModel = new DefaultListModel<>();
        allCoursesList = new JList<>(allCoursesModel);
        enrolledCoursesList = new JList<>(enrolledCoursesModel);
        
        try {
            refreshCourseLists();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error loading courses: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

        JPanel allCoursesPanel = new JPanel(new BorderLayout());
        allCoursesPanel.setBorder(BorderFactory.createTitledBorder("All Courses"));
        allCoursesPanel.add(new JScrollPane(allCoursesList), BorderLayout.CENTER);
        enrollButton = new JButton("Enroll");
        allCoursesPanel.add(enrollButton, BorderLayout.SOUTH);

        JPanel enrolledPanel = new JPanel(new BorderLayout());
        enrolledPanel.setBorder(BorderFactory.createTitledBorder("My Courses"));
        enrolledPanel.add(new JScrollPane(enrolledCoursesList), BorderLayout.CENTER);
        dropButton = new JButton("Drop");
        enrolledPanel.add(dropButton, BorderLayout.SOUTH);

        mainPanel.add(allCoursesPanel);
        mainPanel.add(enrolledPanel);
        add(mainPanel, BorderLayout.CENTER);

        JButton doneButton = new JButton("Done");
        add(doneButton, BorderLayout.SOUTH);
        doneButton.addActionListener(e -> {
            try {
                dispose();
                if (account.getCourses() != null) {
                    account.getCourses().clear();
                    account.getCourses().addAll(enrolledCourses);
                }
                new StudentPanel(account);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error saving course changes: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Enroll button action
        enrollButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String selected = allCoursesList.getSelectedValue();
                    if (selected != null) {
                        String courseId = selected.split(" - ")[0];
                        Course course = courseMap.get(courseId);
                        if (course != null && !course.getEnrolledStudentIds().contains(account.getID())) {
                            // Check if course is full
                            if (course.getCurrentStudents() >= course.getMaxStudents()) {
                                JOptionPane.showMessageDialog(StudentCourseFrame.this, 
                                    "Course is full. Cannot enroll.", "Error", JOptionPane.ERROR_MESSAGE);
                                return;
                            }
                            course.enrollStudent(account.getID());
                            enrolledCourses.add(course);
                            refreshCourseLists();
                            JOptionPane.showMessageDialog(StudentCourseFrame.this, 
                                "Successfully enrolled in " + course.getCourseName(), "Success", JOptionPane.INFORMATION_MESSAGE);
                        } else if (course != null && course.getEnrolledStudentIds().contains(account.getID())) {
                            JOptionPane.showMessageDialog(StudentCourseFrame.this, 
                                "You are already enrolled in this course.", "Info", JOptionPane.INFORMATION_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(StudentCourseFrame.this, 
                            "Please select a course to enroll.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(StudentCourseFrame.this, 
                        "Error enrolling in course: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Drop button action
        dropButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String selected = enrolledCoursesList.getSelectedValue();
                    if (selected != null) {
                        String courseId = selected.split(" - ")[0];
                        Course course = courseMap.get(courseId);
                        if (course != null && course.getEnrolledStudentIds().contains(account.getID())) {
                            course.removeStudent(account.getID());
                            enrolledCourses.removeIf(c -> c.getCourseId().equals(courseId));
                            refreshCourseLists();
                            JOptionPane.showMessageDialog(StudentCourseFrame.this, 
                                "Successfully dropped " + course.getCourseName(), "Success", JOptionPane.INFORMATION_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(StudentCourseFrame.this, 
                            "Please select a course to drop.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(StudentCourseFrame.this, 
                        "Error dropping course: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        setVisible(true);
    }

    private void refreshCourseLists() {
        try {
            allCoursesModel.clear();
            enrolledCoursesModel.clear();
            
            if (courseMap == null || account == null) {
                return;
            }
            
            Set<String> myCourses = courseMap.values().stream()
                    .filter(c -> c != null && c.getEnrolledStudentIds() != null && c.getEnrolledStudentIds().contains(account.getID()))
                    .map(Course::getCourseId)
                    .collect(Collectors.toSet());
                    
            for (Course c : courseMap.values()) {
                if (c != null) {
                    String display = c.getCourseId() + " - " + c.getCourseName() + " (" + c.getCredits() + " credits) - " + 
                                   c.getCurrentStudents() + "/" + c.getMaxStudents() + " students";
                    allCoursesModel.addElement(display);
                    if (myCourses.contains(c.getCourseId())) {
                        enrolledCoursesModel.addElement(display);
                    }
                }
            }
        } catch (Exception ex) {
            System.err.println("Error refreshing course lists: " + ex.getMessage());
        }
    }
} 