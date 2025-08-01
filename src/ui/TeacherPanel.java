package ui;

import model.*;
import util.Utils;
import launcher.Main;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.swing.*;
import java.awt.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;


public class TeacherPanel extends JFrame {

    Map<String, Question> questionMap;
    Question questionSet;
    Account account;
    private JPanel mainPanel;
    JTextField examSearchField;
    JButton questionStatusButton;

    public TeacherPanel(Account account) {

        this.questionMap = Main.getQuestionMap();
        this.account = account;
        setTitle("Teacher Panel");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(245, 247, 250));
        add(createHeader(), BorderLayout.NORTH);
        setupMainPanel();
        add(mainPanel, BorderLayout.CENTER);
        pack();
        setMinimumSize(new Dimension(500, 600));
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JLabel createHeader() {

        JLabel headerLabel = new JLabel("Teacher Dashboard", SwingConstants.CENTER);

        return Utils.setHeader(headerLabel);
    }

    private void setupMainPanel() {

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

        //exam status button
        questionStatusButton = new JButton(Utils.PUBLISHED_STATUS);
        questionStatusButton.setFont(new Font("Arial", Font.PLAIN, 14));
        questionStatusButton.setForeground(new Color(231, 76, 60));
        questionStatusButton.setPreferredSize(new Dimension(250, 20));
        mainPanel.add(questionStatusButton, gbc);

        // create exam button
        gbc.gridy++;
        JButton createExamButton = new JButton("Create New Exam");
        Utils.styleButton(createExamButton);
        createExamButton.setToolTipText("Create a new MCQ exam");
        mainPanel.add(createExamButton, gbc);

        // code label
        gbc.gridy++;
        gbc.gridwidth = 1;
        JLabel searchLabel = new JLabel("Enter Exam Code:");
        searchLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        mainPanel.add(searchLabel, gbc);

        // code text field
        gbc.gridx = 1;
        examSearchField = new JTextField(15);
        examSearchField.setFont(new Font("Arial", Font.PLAIN, 15));
        mainPanel.add(examSearchField, gbc);

        // search button
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        JButton searchExamButton = new JButton("Search/View Exam");
        Utils.styleButton(searchExamButton);
        searchExamButton.setToolTipText("Search for an existing exam by code");
        mainPanel.add(searchExamButton, gbc);

        // publish exam button
        gbc.gridy++;
        JButton publishExamButton = new JButton("Publish Exam");
        Utils.styleButton(publishExamButton);
        publishExamButton.setToolTipText("Publish an exam for students");
        mainPanel.add(publishExamButton, gbc);

        // publish result button
        gbc.gridy++;
        JButton publishResultButton = new JButton("Publish Results");
        Utils.styleButton(publishResultButton);
        publishResultButton.setToolTipText("Publish results for an exam");
        mainPanel.add(publishResultButton, gbc);

        // back button
        gbc.gridy++;
        JButton backButton = new JButton("Back to Main Menu");
        Utils.styleButton(backButton);
        backButton.setToolTipText("Return to main menu");
        mainPanel.add(backButton, gbc);

        setupButtonActions(createExamButton, searchExamButton, publishExamButton, publishResultButton, backButton);
        setupExamSearchFieldListener(searchExamButton, publishExamButton, publishResultButton);
        questionStatusButton.addActionListener(e -> questionStatus(this));
    }

    private void setupButtonActions(JButton createExamButton, JButton searchExamButton, JButton publishExamButton, JButton publishResultButton, JButton backButton) {
        createExamButton.addActionListener(e -> {
            dispose();
            new MCQQuestionCreator(account);
        });
        searchExamButton.addActionListener(e -> {
            StudentPanel.searchExam(examSearchField, true, this);
        });
        publishExamButton.addActionListener(e -> handlePublishExam());
        publishResultButton.addActionListener(e -> handlePublishResult());
        backButton.addActionListener(e -> {
            this.dispose();
            new LoginForm();
        });
    }

    private void setupExamSearchFieldListener(JButton searchExamButton, JButton publishExamButton, JButton publishResultButton) {

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
                String code = examSearchField.getText().trim();
                searchExamButton.setText("Search/View '" + code + "' Exam");
                publishExamButton.setText("Publish '" + code + "' Exam");
                publishResultButton.setText("Publish '" + code + "' Results");
                mainPanel.revalidate();
                mainPanel.repaint();
            }
        });
    }

    private void handlePublishExam() {

        String publishExamCode = examSearchField.getText().trim();
        if (publishExamCode.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a valid exam code", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        questionSet = questionMap.get(publishExamCode);
        if (questionSet == null) {
            JOptionPane.showMessageDialog(this, "Question is not found", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String department = (String) JOptionPane.showInputDialog(
                this,
                "Select Department:",
                "Department",
                JOptionPane.QUESTION_MESSAGE,
                null,
                Utils.DEPARTMENTS,
                Utils.DEPARTMENTS[0]
        );
        if (department == null || department.equals("Select")) {
            JOptionPane.showMessageDialog(this, "Please select department", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String year = (String) JOptionPane.showInputDialog(
                this,
                "Select Year:",
                "Year",
                JOptionPane.QUESTION_MESSAGE,
                null,
                Utils.YEARS,
                Utils.YEARS[0]
        );
        if (year == null || year.equals("Select")) {
            JOptionPane.showMessageDialog(this, "Please Select Year", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String session = (String) JOptionPane.showInputDialog(
                this,
                "Select Year:",
                "Year",
                JOptionPane.QUESTION_MESSAGE,
                null,
                Utils.session(),
                Utils.session()[0]
        );
        if (session == null || session.equals("Select")) {
            JOptionPane.showMessageDialog(this, "Please Select Year", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        List<String> courses = new ArrayList<>();
        courses.add("Select");
        courses.addAll(Main.getCourseMap().keySet());

        String course = (String) JOptionPane.showInputDialog(
                this,
                "Select Course:",
                "Course",
                JOptionPane.QUESTION_MESSAGE,
                null,
                courses.toArray(),
                courses.getFirst()
        );
        if (course == null || course.equals("Select")) {
            JOptionPane.showMessageDialog(this, "Please Select Course", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String name = JOptionPane.showInputDialog(this, "Enter exam name: ", "Exam name", JOptionPane.QUESTION_MESSAGE).trim();
        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a valid exam name", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String examCode = JOptionPane.showInputDialog(this, "Enter exam Code:", "Result", JOptionPane.QUESTION_MESSAGE).trim();
        if (examCode.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a valid exam code", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (Main.getQuestionMap().get(examCode) != null || examCode.equals(Utils.promotion)) {
            JOptionPane.showMessageDialog(this, "Exam exists! Change Code...", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int res = JOptionPane.showConfirmDialog(this, "Are you sure to publish question?" + "\nName: "
                        + name + "\nNew Code: " + examCode + "\nDepartment: " + department + "\nYear: " + year + "\nSession: " + session + "\nCourse: " + course,
                "Publish Results", JOptionPane.YES_NO_OPTION);
        if (res == JOptionPane.YES_OPTION) {
            Utils.EXAM_CODE.put(examCode, true);
            Utils.PUBLISHED_STATUS = "Exam is running";
            questionStatusButton.setText(Utils.PUBLISHED_STATUS);
            Question question1 = new Question(questionSet.getSingleQuestions(), examCode, year, department, name, session, course);
            questionMap.put(examCode, question1);
            Notification notification = new Notification("New Exam published. code: " + examCode + " Department: " + department
                    + " Year: " + year + " Session: " + session + " Course: " + course + " By " + account.getFirstName() + " " + account.getLastName());
            Main.getNotifications().add(notification);
        }

    }

    private void handlePublishResult() {

        String publishExamCode = examSearchField.getText().trim();
        if (publishExamCode.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a valid exam code", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            questionSet = questionMap.get(publishExamCode);
        } catch (NullPointerException ex) {
            JOptionPane.showMessageDialog(this, "No question found", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String department = questionSet.getDepartment();
        String year = questionSet.getYear();
        String session = questionSet.getSession();
        String course = questionSet.getCourseId();
        if (questionSet == null) {
            JOptionPane.showMessageDialog(this, "Please enter a valid exam code", "Error", JOptionPane.ERROR_MESSAGE);
        }

        Boolean value = Utils.EXAM_CODE.get(publishExamCode);
        if (value == null || !value) {
            JOptionPane.showMessageDialog(this, "Question is not published", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int res = JOptionPane.showConfirmDialog(
                this,
                "Are you sure to publish results?",
                "Results",
                JOptionPane.YES_NO_OPTION
        );
        if (res == JOptionPane.NO_OPTION) return;

        Utils.EXAM_CODE.put(publishExamCode, false);
        boolean hasRunningExams = false;
        for (Boolean isRunning : Utils.EXAM_CODE.values()) {
            if (isRunning != null && isRunning) {
                hasRunningExams = true;
                break;
            }
        }
        if (hasRunningExams) {
            Utils.PUBLISHED_STATUS = "Exam is running";
        } else {
            Utils.PUBLISHED_STATUS = "No Exam is running";
        }

        Map<String, Account> studentAccounts = Main.getAccounts();
        Result result;
        for (Account acc : studentAccounts.values()) {
            if (acc instanceof StudentAccount) {
                StudentAccount studentAccount = (StudentAccount) acc;
                boolean courseIs = false;
                for (Course c : studentAccount.getCourses()) {
                    if (c.getCourseId().equals(course)) {
                        courseIs = true;
                        break;
                    }
                }

                Boolean examDone = studentAccount.getEXAM_DONE().get(publishExamCode);
                if ((examDone == null || !examDone) && courseIs && studentAccount.getDepartment().equals(department) && studentAccount.getYear().equals(year) && studentAccount.getSession().equals(session)) {

                    studentAccount.setEXAM_DONE(publishExamCode, false);
                    result = new Result((StudentAccount) acc, 0, 0,0, publishExamCode, "You didn't participate", 0.0);
                    Main.getResultList().add(result);
                }
            }
        }

        questionStatusButton.setText(Utils.PUBLISHED_STATUS);
        JOptionPane.showMessageDialog(this, "Result Published", "Success", JOptionPane.INFORMATION_MESSAGE);
        Notification notification = new Notification("Result published. code: " + publishExamCode + " Department: " + department
                + " Year: " + year + " By " + account.getFirstName() + " " + account.getLastName());
        Main.getNotifications().add(notification);


    }

    public static void questionStatus(JFrame frame) {

        StringBuilder result = new StringBuilder();
        Boolean value = Utils.EXAM_CODE.isEmpty();
        if (value == null || value) {
            JOptionPane.showMessageDialog(frame, Utils.PUBLISHED_STATUS, "Exam Status", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        for (Map.Entry<String, Boolean> entry : Utils.EXAM_CODE.entrySet()) {
            String examCode = entry.getKey();
            Boolean isRunning = entry.getValue();
            if (isRunning != null) {
                Question question = Main.getQuestionMap().get(examCode);
                if (question != null) {
                    result.append("Exam Code: ").append(examCode).append("\n");
                    result.append("Exam Name: ").append(question.getExamName()).append("\n");
                    result.append("Department: ").append(question.getDepartment()).append("\n");
                    result.append("Year: ").append(question.getYear()).append("\n");
                    result.append("Session: ").append(question.getSession()).append("\n");
                    result.append("Course: ").append(question.getCourseId()).append("\n");
                    if (isRunning) {
                        result.append("Status: Running\n");
                    } else {
                        result.append("Status: Result Published\n");
                    }
                    result.append("=".repeat(50)).append("\n");
                } else {
                    result.append("Exam Code: ").append(examCode).append("\n (Question not found)\n\n");
                }
            }
        }
        JOptionPane.showMessageDialog(frame, Utils.ScrollPanel(result), "Exams", JOptionPane.INFORMATION_MESSAGE);

    }

}