package ui;

import launcher.Main;
import model.*;
import util.Utils;

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

        initializeFrame();
        createComponents();
        addEventListeners();
        setupDocumentListener();
        setVisible(true);
    }

    private void initializeFrame() {

        setTitle("Student Panel");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(245, 247, 250));
        add(createHeader(), BorderLayout.NORTH);
        pack();
        setMinimumSize(new Dimension(500, 700));
        setLocationRelativeTo(null);
    }

    private void createComponents() {

        createHeader();
        createMainPanel();
    }

private JLabel createHeader() {

        JLabel headerLabel = new JLabel("Student Dashboard", SwingConstants.CENTER);

        return Utils.setHeader(headerLabel);
    }

    private void createMainPanel() {

        mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(new Color(255, 255, 255));
        mainPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(24, 32, 24, 32),
                BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)),
                        "Exam Management", 0, 0,
                        new Font("Arial", Font.BOLD, 16), new Color(52, 73, 94))
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 12, 12, 12);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        createStatusButton(gbc);
        createSearchArea(gbc);
        createActionButtons(gbc);

        add(mainPanel, BorderLayout.CENTER);
    }

    private void createStatusButton(GridBagConstraints gbc) {

        questionStatusButton = new JButton(Utils.PUBLISHED_STATUS);
        questionStatusButton.setFont(new Font("Arial", Font.PLAIN, 14));
        questionStatusButton.setForeground(new Color(231, 76, 60));
        questionStatusButton.setPreferredSize(new Dimension(250, 20));
        mainPanel.add(questionStatusButton, gbc);
    }

    private void createSearchArea(GridBagConstraints gbc) {
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
    }

    private void createActionButtons(GridBagConstraints gbc) {
        // Exam Button
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        JButton examButton = new JButton("EXAM");
        Utils.styleButton(examButton);
        examButton.setToolTipText("EXAM");
        mainPanel.add(examButton, gbc);

        // Search/View Exam Button
        gbc.gridy++;
        JButton searchExamButton = new JButton("Search/View Exam");
        Utils.styleButton(searchExamButton);
        searchExamButton.setToolTipText("Search for an existing exam by code");
        mainPanel.add(searchExamButton, gbc);

        // Show Information Button
        gbc.gridy++;
        JButton showInfo = new JButton("Show Information");
        Utils.styleButton(showInfo);
        showInfo.setToolTipText("Show my Information");
        mainPanel.add(showInfo, gbc);

        // Results Button
        gbc.gridy++;
        JButton resultButton = new JButton("Results");
        Utils.styleButton(resultButton);
        resultButton.setToolTipText("show results");
        mainPanel.add(resultButton, gbc);

        // Course Management Button
        gbc.gridy++;
        JButton courseButton = new JButton("Course Management");
        Utils.styleButton(courseButton);
        courseButton.setToolTipText("Manage your courses");
        mainPanel.add(courseButton, gbc);

        // Back Button
        gbc.gridy++;
        JButton backButton = new JButton("Back");
        Utils.styleButton(backButton);
        backButton.setToolTipText("Return to main menu");
        mainPanel.add(backButton, gbc);
    }

    private void addEventListeners() {

        addExamButtonListener();
        addSearchExamButtonListener();
        addShowInfoButtonListener();
        addResultButtonListener();
        addCourseButtonListener();
        addBackButtonListener();
        addQuestionStatusButtonListener();
    }

    private void addExamButtonListener() {

        JButton examButton = findButtonByText("EXAM");
        if (examButton != null) {
            examButton.addActionListener(e -> handleExamAction());
        }
    }

    private void addSearchExamButtonListener() {

        JButton searchExamButton = findButtonByText("Search/View Exam");
        if (searchExamButton != null) {
            searchExamButton.addActionListener(e -> handleSearchExamAction());
        }
    }

    private void addShowInfoButtonListener() {

        JButton showInfo = findButtonByText("Show Information");
        if (showInfo != null) {
            showInfo.addActionListener(e -> handleShowInfoAction());
        }
    }

    private void addResultButtonListener() {

        JButton resultButton = findButtonByText("Results");
        if (resultButton != null) {
            resultButton.addActionListener(e -> handleResultAction());
        }
    }

    private void addCourseButtonListener() {

        JButton courseButton = findButtonByText("Course Management");
        if (courseButton != null) {
            courseButton.addActionListener(e -> handleCourseAction());
        }
    }

    private void addBackButtonListener() {

        JButton backButton = findButtonByText("Back");
        if (backButton != null) {
            backButton.addActionListener(e -> handleBackAction());
        }
    }

    private void addQuestionStatusButtonListener() {

        questionStatusButton.addActionListener(e -> handleQuestionStatusAction());
    }

    private JButton findButtonByText(String text) {

        for (Component comp : mainPanel.getComponents()) {
            if (comp instanceof JButton && ((JButton) comp).getText().contains(text)) {
                return (JButton) comp;
            }
        }
        return null;
    }

    private void setupDocumentListener() {

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
                    updateButtonTexts(code);
                    mainPanel.revalidate();
                    mainPanel.repaint();
                } catch (Exception ex) {
                    System.err.println("Error updating buttons: " + ex.getMessage());
                }
            }
        });
    }

    private void updateButtonTexts(String code) {

        JButton searchExamButton = findButtonByText("Search/View");
        JButton examButton = findButtonByText("EXAM");
        JButton resultButton = findButtonByText("Results");

        if (searchExamButton != null) {
            searchExamButton.setText("Search/View '" + code + "' question");
        }
        if (examButton != null) {
            examButton.setText("EXAM '" + code + "'");
        }
        if (resultButton != null) {
            resultButton.setText("Results '" + code + "'");
        }
    }

    private void handleExamAction() {

        try {
            String examCode = examSearchField.getText().trim();
            if (examCode.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a valid exam code", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (!validateExamCode(examCode)) {
                return;
            }
            
            if (!validateExamAccess(examCode)) {
                return;
            }
            
            if (!validateCourseEnrollment(examCode)) {
                return;
            }
            
            if (!validateExamStatus(examCode)) {
                return;
            }
            
            startExam(examCode);
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error processing exam: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            System.err.println("Exception in exam button: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private boolean validateExamCode(String examCode) {

        Boolean examRunning = Utils.EXAM_CODE.get(examCode);
        if (examRunning == null) {
            JOptionPane.showMessageDialog(this, "Exam is not found..", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        Question question = Main.getQuestionMap().get(examCode);
        if (question == null) {
            JOptionPane.showMessageDialog(this, "Question is not found..", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        return true;
    }

    private boolean validateExamAccess(String examCode) {

        Question question = Main.getQuestionMap().get(examCode);
        String examYear = question.getYear() != null ? question.getYear().trim() : "";
        String examDepartment = question.getDepartment() != null ? question.getDepartment().trim() : "";
        String examSession = question.getSession() != null ? question.getSession().trim() : "";
        String examCourse = question.getCourseId() != null ? question.getCourseId().trim() : "";
        
        if (examYear.isEmpty() || examDepartment.isEmpty() || examSession.isEmpty() || examCourse.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Invalid exam configuration", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        if (!(account.getDepartment().equals(examDepartment) && account.getYear().equals(examYear) && account.getSession().equals(examSession))) {
            JOptionPane.showMessageDialog(this, "Exam is not for you!", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        return true;
    }

    private boolean validateCourseEnrollment(String examCode) {

        Question question = Main.getQuestionMap().get(examCode);
        String examCourse = question.getCourseId() != null ? question.getCourseId().trim() : "";
        
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
            return false;
        }
        
        return true;
    }

    private boolean validateExamStatus(String examCode) {

        Boolean examRunning = Utils.EXAM_CODE.get(examCode);
        if (!examRunning) {
            JOptionPane.showMessageDialog(this, "Exam is finished..", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        Map<String, Boolean> examDoneMap = account.getEXAM_DONE();
        if (examDoneMap == null) {
            examDoneMap = new HashMap<>();
            for (String key : examDoneMap.keySet()) {
                account.setEXAM_DONE(key, examDoneMap.get(key));
            }
        }
        
        Boolean examDone = examDoneMap.get(examCode);
        if (examDone == null || !examDone) {
            account.setEXAM_DONE(examCode, true);
            return true;
        } else {
            JOptionPane.showMessageDialog(StudentPanel.this, "Your exam is done", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    private void startExam(String examCode) {

        dispose();
        new StudentExamFrame(account, examCode);
    }

    private void handleSearchExamAction() {

        try {
            searchExam(examSearchField, false, this);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error searching exam: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleShowInfoAction() {

        try {
            JOptionPane.showMessageDialog(this, account, "Info", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error displaying information: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleResultAction() {

        try {
            String examCode = examSearchField.getText().trim();
            if (examCode.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a valid exam code", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            boolean isPromotionExam = examCode.equals(Utils.promotion);
            Boolean examRunning = Utils.EXAM_CODE.get(examCode);

            StringBuilder result = new StringBuilder();
            if (isPromotionExam) {
                 result.append(account.getPromotion());
            } else if (examRunning != null && examRunning) {
                JOptionPane.showMessageDialog(this, "Result not published yet - exam is still running", "Result Details", JOptionPane.INFORMATION_MESSAGE);
                return;
            } else {
                boolean resoultFount = false;
                for (Result r : Main.getResultList()) {
                    String code = r.getExamCode();
                    String year = r.getYear();
                    String department = r.getDepartment();
                    String session = r.getSession();
                    if (code.equals(examCode) && year.equals(account.getYear()) && session.equals(account.getSession()) && department.equals(account.getDepartment())) {
                        result.append(r.getResultInfo());
                        result.append("\nCourse ID: ").append(questionMap.get(examCode).getCourseId()).append("\n");
                        result.append("Mark: ").append(r.getMark()).append("\n");
                        result.append("Correct: ").append(r.getCorrect()).append("\n");
                        result.append("Incorrect: ").append(r.getIncorrect()).append("\n");
                        result.append("CG: ").append(r.getCg());
                        resoultFount = true;
                        break;
                    }
                }
                if (!resoultFount) {
                    JOptionPane.showMessageDialog(this, "Result is not found", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            JOptionPane.showMessageDialog(this, Utils.ScrollPanel(result), "Result Details", JOptionPane.INFORMATION_MESSAGE);

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
    }

    private void handleCourseAction() {

        try {
            dispose();
            new StudentCourseFrame(account);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error opening course management: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleBackAction() {

        try {
            dispose();
            new LoginForm();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error returning to login: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleQuestionStatusAction() {

        try {
            TeacherPanel.questionStatus(this);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error checking question status: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void searchExam(JTextField examSearchField, boolean isTeacher, JFrame frame) {

        String searchExam = examSearchField.getText().trim();
        if (searchExam.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Please enter a valid exam code", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Question questionSet = Main.getQuestionMap().get(searchExam);
        if (questionSet == null) {
            JOptionPane.showMessageDialog(frame, "No Question Found!", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (!isTeacher && Utils.EXAM_CODE.get(searchExam) != null && Utils.EXAM_CODE.get(searchExam)) {
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
            JOptionPane.showMessageDialog(frame, Utils.ScrollPanel(qs), "Exam Details", JOptionPane.INFORMATION_MESSAGE);
        }

    }
} 