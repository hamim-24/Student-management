package ui;

import launcher.Main;
import model.*;
import util.utils;

import javax.swing.*;
import java.awt.*;
import java.util.Map;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.util.HashMap;

public class StudentPanel extends JFrame {

    Map<String, Question> questionMap;
    StudentAccount account;
    private JPanel mainPanel;
    JButton questionStatusButton;
    JTextField examSearchField;

    public StudentPanel(StudentAccount account) {
        // Validate account parameter
        if (account == null) {
            JOptionPane.showMessageDialog(null, "Invalid account information", "Error", JOptionPane.ERROR_MESSAGE);
            dispose();
            new LoginForm();
            return;
        }
        
        this.questionMap = Main.getQuestionMap();
        this.account = account;

        setTitle("Student Panel");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(245, 247, 250));

        // Header
        JLabel headerLabel = new JLabel("Student Dashboard", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 28));
        headerLabel.setBorder(BorderFactory.createEmptyBorder(24, 0, 24, 0));
        headerLabel.setForeground(new Color(44, 62, 80));
        add(headerLabel, BorderLayout.NORTH);

        // Main panel with GridBagLayout
        mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(new Color(255, 255, 255));
        mainPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(24, 32, 24, 32),
                BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)), "Exam Management", 0, 0, new Font("Arial", Font.BOLD, 16), new Color(52, 73, 94))
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 12, 12, 12);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        questionStatusButton = new JButton(utils.PUBLISHED_STATUS);
        questionStatusButton.setFont(new Font("Arial", Font.PLAIN, 14));
        questionStatusButton.setForeground(new Color(231, 76, 60));
        questionStatusButton.setPreferredSize(new Dimension(250, 20));
        mainPanel.add(questionStatusButton, gbc);

        // Search area
        gbc.gridy++;
        gbc.gridwidth = 1;
        JLabel searchLabel = new JLabel("Enter Exam Code:");
        searchLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        mainPanel.add(searchLabel, gbc);

        gbc.gridx = 1;
        examSearchField = new JTextField(15);
        examSearchField.setFont(new Font("Arial", Font.PLAIN, 15));
        mainPanel.add(examSearchField, gbc);

        // Exam Button
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        JButton examButton = new JButton("EXAM");
        utils.styleButton(examButton);
        examButton.setToolTipText("EXAM");
        mainPanel.add(examButton, gbc);

        // Search/View Exam Button
        gbc.gridy++;
        JButton searchExamButton = new JButton("Search/View Exam");
        utils.styleButton(searchExamButton);
        searchExamButton.setToolTipText("Search for an existing exam by code");
        mainPanel.add(searchExamButton, gbc);

        // Show Information Button
        gbc.gridy++;
        JButton showInfo = new JButton("Show Information");
        utils.styleButton(showInfo);
        showInfo.setToolTipText("Show my Information");
        mainPanel.add(showInfo, gbc);

        // Results Button
        gbc.gridy++;
        JButton resultButton = new JButton("Results");
        utils.styleButton(resultButton);
        resultButton.setToolTipText("show results");
        mainPanel.add(resultButton, gbc);

        // Course Management Button
        gbc.gridy++;
        JButton courseButton = new JButton("Course Management");
        utils.styleButton(courseButton);
        courseButton.setToolTipText("Manage your courses");
        mainPanel.add(courseButton, gbc);

        // Action for course management
        courseButton.addActionListener(e -> {
            try {
                dispose();
                new StudentCourseFrame(account);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error opening course management: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Back Button
        gbc.gridy++;
        JButton backButton = new JButton("Back");
        utils.styleButton(backButton);
        backButton.setToolTipText("Return to main menu");
        mainPanel.add(backButton, gbc);

        add(mainPanel, BorderLayout.CENTER);

        // Button actions
        examButton.addActionListener(e -> {
            try {
                String examCode = examSearchField.getText().trim();
                if (examCode.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Please enter a valid exam code", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                Boolean examRunning = utils.EXAM_CODE.get(examCode);
                if (examRunning == null) {
                    JOptionPane.showMessageDialog(this, "Exam is not found..", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                Question question = Main.getQuestionMap().get(examCode);
                if (question == null) {
                    JOptionPane.showMessageDialog(this, "Question is not found..", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                String examYear = question.getYear() != null ? question.getYear().trim() : "";
                String examDepartment = question.getDepartment() != null ? question.getDepartment().trim() : "";
                String examSession = question.getSession() != null ? question.getSession().trim() : "";
                String examCourse = question.getCourseId() != null ? question.getCourseId().trim() : "";
                
                if (examYear.isEmpty() || examDepartment.isEmpty() || examSession.isEmpty() || examCourse.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Invalid exam configuration", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                if (!(account.getDepartment().equals(examDepartment) && account.getYear().equals(examYear) && account.getSession().equals(examSession))) {
                    JOptionPane.showMessageDialog(this, "Exam is not for you!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (!examRunning) {
                    JOptionPane.showMessageDialog(this, "Exam is finished..", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                // Check if student is enrolled in the course
                boolean courseIs = false;
                if (account.getCourses() != null) {
                    for (Course c : account.getCourses()) {
                        if (c != null && c.getCourseId() != null && c.getCourseId().equals(examCourse)) {
                            courseIs = true;
                            break;
                        }
                    }
                }
                
                if (!courseIs) {
                    JOptionPane.showMessageDialog(this, "You don't have this course..", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Map<String, Boolean> examDoneMap = account.getEXAM_DONE();
                if (examDoneMap == null) {
                    examDoneMap = new HashMap<>();
                    // Initialize the map in the account
                    for (String key : examDoneMap.keySet()) {
                        account.setEXAM_DONE(key, examDoneMap.get(key));
                    }
                }
                
                Boolean examDone = examDoneMap.get(examCode);
                if (examDone == null || !examDone) {
                    account.setEXAM_DONE(examCode, true);
                    dispose();
                    new StudentExamFrame(account, examCode);
                } else {
                    JOptionPane.showMessageDialog(StudentPanel.this, "Your exam is done", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error processing exam: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                System.err.println("Exception in exam button: " + ex.getMessage());
                ex.printStackTrace();
            }
        });
        
        searchExamButton.addActionListener(e -> {
            try {
                searchExam(examSearchField, false, this);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error searching exam: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        showInfo.addActionListener(e -> {
            try {
                JOptionPane.showMessageDialog(this, account, "Info", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error displaying information: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        resultButton.addActionListener(e -> {
            try {
                String examCode = examSearchField.getText().trim();
                if (examCode.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Please enter a valid exam code", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                Map<String, String> resultInfo = account.getResultInfo();
                if (resultInfo == null) {
                    resultInfo = new HashMap<>();
                    // The resultInfo map is already initialized in the constructor
                }
                
                boolean isPromotionExam = examCode.equals(utils.promotion) && resultInfo.containsKey(examCode);
                
                Boolean examRunning = utils.EXAM_CODE.get(examCode);

                if (examRunning == null && !isPromotionExam) {
                    JOptionPane.showMessageDialog(this, "Your promotion is not found", "Result Details", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                
                if (!isPromotionExam && examRunning != null && examRunning) {
                    JOptionPane.showMessageDialog(this, "Result not published yet - exam is still running", "Result Details", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                
                String result = resultInfo.get(examCode);
                
                if (result != null && !result.trim().isEmpty()) {
                    StringBuilder resultBuilder = new StringBuilder();
                    resultBuilder.append("# ").append(result).append("\n\n");
                    JOptionPane.showMessageDialog(this, utils.ScrollPanel(resultBuilder), "Result Details", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Result not found for this exam", "Error", JOptionPane.ERROR_MESSAGE);
                }
                
            } catch (NullPointerException npe) {
                JOptionPane.showMessageDialog(this, "Your promotion is not found", "System Error", JOptionPane.ERROR_MESSAGE);
                System.err.println("NullPointerException in resultButton: " + npe.getMessage());
            } catch (IllegalArgumentException iae) {
                JOptionPane.showMessageDialog(this, "Error: Invalid exam code format", "Input Error", JOptionPane.ERROR_MESSAGE);
                System.err.println("IllegalArgumentException in resultButton: " + iae.getMessage());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "An unexpected error occurred while retrieving results. Please try again.", "System Error", JOptionPane.ERROR_MESSAGE);
                System.err.println("Exception in resultButton: " + ex.getMessage());
                ex.printStackTrace();
            }
        });

        backButton.addActionListener(e -> {
            try {
                dispose();
                new LoginForm();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error returning to login: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        examSearchField.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) {
                updateButtons();
            }

            public void removeUpdate(DocumentEvent e) {
                updateButtons();
            }

            public void changedUpdate(DocumentEvent e) {
                updateButtons();
            }

            private void updateButtons() {
                try {
                    String code = examSearchField.getText().trim();
                    searchExamButton.setText("Search/View '" + code + "' Exam");
                    examButton.setText("EXAM " + code);
                    resultButton.setText("Results " + code);

                    // Add these lines to force UI refresh
                    mainPanel.revalidate();
                    mainPanel.repaint();
                } catch (Exception ex) {
                    System.err.println("Error updating buttons: " + ex.getMessage());
                }
            }
        });

        questionStatusButton.addActionListener(e -> {
            try {
                TeacherPanel.questionStatus(this);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error checking question status: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        pack();
        setMinimumSize(new Dimension(500, 600));
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void searchExam(JTextField examSearchField, boolean isTeacher, JFrame frame) {

        String searchExam = examSearchField.getText().trim();
        if (searchExam.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Please enter a valid exam code", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            Question questionSet = Main.getQuestionMap().get(searchExam);
            if (questionSet == null) {
                JOptionPane.showMessageDialog(frame, "No Question Found!", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (!isTeacher && utils.EXAM_CODE.get(searchExam) != null && utils.EXAM_CODE.get(searchExam)) {
                JOptionPane.showMessageDialog(frame,
                        "The Exam is running",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            } else {
                StringBuilder qs = new StringBuilder();
                qs.append("Exam Code: " + questionSet.getQuestionCode() + "\n\n");
                int i = 1;
                for (SingleQuestion SQ : questionSet.getSingleQuestions()) {
                    qs.append("  " + i + ". " + SQ.toString() + "\n");
                    i++;
                }
                JOptionPane.showMessageDialog(frame, utils.ScrollPanel(qs), "Exam Details", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

} 