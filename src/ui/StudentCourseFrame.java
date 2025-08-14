package ui;

import model.StudentAccount;

import javax.swing.*;
import java.awt.*;
import launcher.Main;
import model.Course;
import util.Utils;

import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

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
    private JLabel studentInfoLabel;
    private JLabel totalCreditsLabel;

    public StudentCourseFrame(StudentAccount account) {
        // Validate account parameter
        if (account == null) {
            JOptionPane.showMessageDialog(null, "Invalid account information", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        this.account = account;
        this.courseMap = Main.getCourseMap();
        
        // Validate courseMap
        if (this.courseMap == null || this.courseMap.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Course data not available", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Initialize enrolledCourses with student's existing courses
        this.enrolledCourses = new ArrayList<>();
        if (account.getCourses() != null) {
            this.enrolledCourses.addAll(account.getCourses());
        }
        
        initializeFrame();
        createComponents();
        addEventListeners();
        refreshCourseLists();
        setVisible(true);
    }

    private void initializeFrame() {

        setTitle("Course Management - ");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(0, 0));
        getContentPane().setBackground(new Color(245, 247, 250));
    }

    private void createComponents() {
        createTitleLabel();
        createInfoPanel();
        createMainPanels();
        createButtonPanel();
    }

    private void createTitleLabel() {

        JLabel titleLabel = new JLabel("Course Management", SwingConstants.CENTER);
        add(Utils.setHeader(titleLabel), BorderLayout.NORTH);
    }

    private void createInfoPanel() {
        JPanel infoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 24, 6));
        infoPanel.setBackground(new Color(255, 255, 255));
        infoPanel.setPreferredSize(new Dimension(900, 50));
        infoPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220)),
                BorderFactory.createEmptyBorder(8, 24, 8, 24)));
        
        studentInfoLabel = new JLabel();
        studentInfoLabel.setFont(new Font("Arial", Font.BOLD, 15));
        studentInfoLabel.setText("Student: " + account.getFirstName() + " " + account.getLastName() + 
                               " | ID: " + account.getID() + " | Department: " + account.getDepartment() + 
                               " | Year: " + account.getYear() + " | Session: " + account.getSession());
        
        totalCreditsLabel = new JLabel();
        totalCreditsLabel.setFont(new Font("Arial", Font.BOLD, 15));
        totalCreditsLabel.setForeground(new Color(52, 152, 219));
        
        infoPanel.add(studentInfoLabel);
        infoPanel.add(totalCreditsLabel);
        
        add(infoPanel, BorderLayout.CENTER);
    }

    private void createMainPanels() {

        JPanel mainPanel = new JPanel(new GridLayout(1, 2, 20, 0));
        mainPanel.setBackground(new Color(245, 247, 250));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 20));
        
        createListModels();
        styleLists();
        createAllCoursesPanel(mainPanel);
        createEnrolledCoursesPanel(mainPanel);
        
        add(mainPanel, BorderLayout.CENTER);
    }

    private void createListModels() {
        allCoursesModel = new DefaultListModel<>();
        enrolledCoursesModel = new DefaultListModel<>();
        
        allCoursesList = new JList<>(allCoursesModel);
        enrolledCoursesList = new JList<>(enrolledCoursesModel);
    }

    private void styleLists() {

        allCoursesList.setFont(new Font("Arial", Font.PLAIN, 14));
        enrolledCoursesList.setFont(new Font("Arial", Font.PLAIN, 14));
        allCoursesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        enrolledCoursesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        allCoursesList.setBackground(new Color(255, 255, 255));
        enrolledCoursesList.setBackground(new Color(255, 255, 255));
    }

    private void createAllCoursesPanel(JPanel mainPanel) {

        JPanel allCoursesPanel = new JPanel(new BorderLayout(10, 10));
        allCoursesPanel.setBackground(new Color(255, 255, 255));
        allCoursesPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(10, 10, 10, 10),
                BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)), 
                    "Available Courses", 0, 0, new Font("Arial", Font.BOLD, 16), new Color(52, 73, 94))
        ));
        
        JScrollPane allCoursesScrollPane = new JScrollPane(allCoursesList);
        allCoursesScrollPane.setPreferredSize(new Dimension(400, 300));
        allCoursesScrollPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        allCoursesPanel.add(allCoursesScrollPane, BorderLayout.CENTER);
        
        enrollButton = new JButton("Enroll in Course");
        Utils.styleButton(enrollButton);
        enrollButton.setPreferredSize(new Dimension(150, 40));
        allCoursesPanel.add(enrollButton, BorderLayout.SOUTH);
        
        mainPanel.add(allCoursesPanel);
    }

    private void createEnrolledCoursesPanel(JPanel mainPanel) {

        JPanel enrolledPanel = new JPanel(new BorderLayout(10, 10));
        enrolledPanel.setBackground(new Color(255, 255, 255));
        enrolledPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(10, 10, 10, 10),
                BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)), 
                    "My Enrolled Courses", 0, 0, new Font("Arial", Font.BOLD, 16), new Color(52, 73, 94))
        ));
        
        JScrollPane enrolledScrollPane = new JScrollPane(enrolledCoursesList);
        enrolledScrollPane.setPreferredSize(new Dimension(400, 300));
        enrolledScrollPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        enrolledPanel.add(enrolledScrollPane, BorderLayout.CENTER);
        
        dropButton = new JButton("Drop Course");
        Utils.styleButton(dropButton);
        dropButton.setPreferredSize(new Dimension(150, 40));
        enrolledPanel.add(dropButton, BorderLayout.SOUTH);
        
        mainPanel.add(enrolledPanel);
    }

    private void createButtonPanel() {

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(new Color(245, 247, 250));
        
        JButton doneButton = new JButton("Save & Return");
        Utils.styleButton(doneButton);
        doneButton.setPreferredSize(new Dimension(180, 45));
        
        buttonPanel.add(doneButton);
        
        add(buttonPanel, BorderLayout.SOUTH);
        
        // Add button action
        doneButton.addActionListener(e -> handleSaveAndReturn());
    }

    private void addEventListeners() {
        enrollButton.addActionListener(e -> handleEnrollAction());
        dropButton.addActionListener(e  -> handleDropAction());
    }

    private void handleEnrollAction() {

        try {
            String selected = allCoursesList.getSelectedValue();
            if (selected == null || selected.trim().isEmpty()) {
                JOptionPane.showMessageDialog(StudentCourseFrame.this, 
                    "Please select a course to enroll.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            String[] parts = selected.split(" - ");
            if (parts.length < 1) {
                JOptionPane.showMessageDialog(StudentCourseFrame.this, 
                    "Invalid course format.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            String courseId = parts[0];
            Course course = courseMap.get(courseId);
            
            if (course == null) {
                JOptionPane.showMessageDialog(StudentCourseFrame.this, 
                    "Course not found in system.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (course.getEnrolledStudentIds().contains(account.getID())) {
                JOptionPane.showMessageDialog(StudentCourseFrame.this, 
                    "You are already enrolled in this course.", "Info", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            
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
                
        } catch (IllegalStateException ise) {
            JOptionPane.showMessageDialog(StudentCourseFrame.this, 
                "Cannot enroll: " + ise.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(StudentCourseFrame.this, 
                "Error enrolling in course: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            System.err.println("Exception in StudentCourseFrame.handleEnrollAction: " + ex.getMessage());
        }
    }

    private void handleDropAction() {

        try {
            String selected = enrolledCoursesList.getSelectedValue();
            if (selected == null || selected.trim().isEmpty()) {
                JOptionPane.showMessageDialog(StudentCourseFrame.this, 
                    "Please select a course to drop.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            String[] parts = selected.split(" - ");
            if (parts.length < 1) {
                JOptionPane.showMessageDialog(StudentCourseFrame.this, 
                    "Invalid course format.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            String courseId = parts[0];
            Course course = courseMap.get(courseId);
            
            if (course == null) {
                JOptionPane.showMessageDialog(StudentCourseFrame.this, 
                    "Course not found in system.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (!course.getEnrolledStudentIds().contains(account.getID())) {
                JOptionPane.showMessageDialog(StudentCourseFrame.this, 
                    "You are not enrolled in this course.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            course.removeStudent(account.getID());
            enrolledCourses.removeIf(c -> c.getCourseId().equals(courseId));
            refreshCourseLists();
            JOptionPane.showMessageDialog(StudentCourseFrame.this, 
                "Successfully dropped " + course.getCourseName(), "Success", JOptionPane.INFORMATION_MESSAGE);
                
        } catch (IllegalStateException ise) {
            JOptionPane.showMessageDialog(StudentCourseFrame.this, 
                "Cannot drop course: " + ise.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(StudentCourseFrame.this, 
                "Error dropping course: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            System.err.println("Exception in StudentCourseFrame.handleDropAction: " + ex.getMessage());
        }
    }

    private void handleSaveAndReturn() {

        try {
            dispose();
            if (account.getCourses() != null) {
                account.getCourses().clear();
                account.getCourses().addAll(enrolledCourses);
            }
            new StudentPanel(account);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error saving course changes: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            System.err.println("Exception in StudentCourseFrame.handleSaveAndReturn: " + ex.getMessage());
        }
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
                    
            int totalCredits = 0;
            for (Course c : courseMap.values()) {
                if (c != null) {
                    String display = c.getCourseId() + " - " + c.getCourseName() + " (" + c.getCredits() + " credits) - " + 
                                   c.getCurrentStudents() + "/" + c.getMaxStudents() + " students";
                    allCoursesModel.addElement(display);
                    if (myCourses.contains(c.getCourseId())) {
                        enrolledCoursesModel.addElement(display);
                        totalCredits += c.getCredits();
                    }
                }
            }
            
            // Update total credits label
            totalCreditsLabel.setText("Total Credits: " + totalCredits);
            
        } catch (Exception ex) {
            System.err.println("Error refreshing course lists: " + ex.getMessage());
            throw new RuntimeException("Failed to refresh course lists", ex);
        }
    }
} 