package ui;

import model.*;
import util.utils;
import launcher.Main;

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
    String department, year;

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
        headerLabel.setFont(new Font("Arial", Font.BOLD, 28));
        headerLabel.setBorder(BorderFactory.createEmptyBorder(24, 0, 24, 0));
        headerLabel.setForeground(new Color(44, 62, 80));
        return headerLabel;
    }

    private void setupMainPanel() {
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
        gbc.gridy++;
        JButton createExamButton = new JButton("Create New Exam");
        utils.styleButton(createExamButton);
        createExamButton.setToolTipText("Create a new MCQ exam");
        mainPanel.add(createExamButton, gbc);
        gbc.gridy++;
        gbc.gridwidth = 1;
        JLabel searchLabel = new JLabel("Enter Exam Code:");
        searchLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        mainPanel.add(searchLabel, gbc);
        gbc.gridx = 1;
        examSearchField = new JTextField(15);
        examSearchField.setFont(new Font("Arial", Font.PLAIN, 15));
        mainPanel.add(examSearchField, gbc);
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        JButton searchExamButton = new JButton("Search/View Exam");
        utils.styleButton(searchExamButton);
        searchExamButton.setToolTipText("Search for an existing exam by code");
        mainPanel.add(searchExamButton, gbc);
        gbc.gridy++;
        JButton publishExamButton = new JButton("Publish Exam");
        utils.styleButton(publishExamButton);
        publishExamButton.setToolTipText("Publish an exam for students");
        mainPanel.add(publishExamButton, gbc);
        gbc.gridy++;
        JButton publishResultButton = new JButton("Publish Results");
        utils.styleButton(publishResultButton);
        publishResultButton.setToolTipText("Publish results for an exam");
        mainPanel.add(publishResultButton, gbc);
        gbc.gridy++;
        JButton backButton = new JButton("Back to Main Menu");
        utils.styleButton(backButton);
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
            public void insertUpdate(DocumentEvent e) { updateButtons(); }
            public void removeUpdate(DocumentEvent e) { updateButtons(); }
            public void changedUpdate(DocumentEvent e) { updateButtons(); }
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
        } else {
            questionSet = questionMap.get(publishExamCode);
            if (questionSet != null) {
                String department = (String) JOptionPane.showInputDialog(
                        this,
                        "Select Department:",
                        "Department",
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        utils.DEPARTMENTS,
                        utils.DEPARTMENTS[0]
                );
                if (department == null || department.equals("Select")) return;
                String year = (String) JOptionPane.showInputDialog(
                        this,
                        "Select Year:",
                        "Year",
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        utils.YEARS,
                        utils.YEARS[0]
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
                        utils.session(),
                        utils.session()[0]
                );
                if (session == null || session.equals("Select")) {
                    JOptionPane.showMessageDialog(this, "Please Select Year", "Error", JOptionPane.ERROR_MESSAGE);
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
                if (Main.getQuestionMap().get(examCode) != null) {
                    JOptionPane.showMessageDialog(this, "Exam exists! Change Code...", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                int res = JOptionPane.showConfirmDialog(this, "Are you sure to publish question?" + "\nName: " + name + "\nNew Code: " + examCode + "\nDepartment: " + department + "\nYear: " + year + "Session: " + session, "Publish Results", JOptionPane.YES_NO_OPTION);
                if (res == JOptionPane.YES_OPTION) {
                    utils.EXAM_CODE.put(examCode, true);
                    utils.PUBLISHED_STATUS = "Exam is running";
                    questionStatusButton.setText(utils.PUBLISHED_STATUS);
                    Question question1 = new Question(questionSet.getSingleQuestions(), examCode, year, department, name, session);
                    questionMap.put(examCode, question1);
                    Notification notification = new Notification("New Exam published. code: " + examCode + " Department: " + department
                            + " Year: " + year + " Session: " + session + " By " + account.getFirstName() + " " + account.getLastName());
                    Main.getNotifications().add(notification);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Question is not found", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void handlePublishResult() {
        String publishExamCode = examSearchField.getText().trim();
        if (publishExamCode.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a valid exam code", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            try {
                questionSet = questionMap.get(publishExamCode);
                department = questionSet.getDepartment();
                year = questionSet.getYear();
            } catch (NullPointerException ex) {
                JOptionPane.showMessageDialog(this, "No question found", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (questionSet == null) {
                JOptionPane.showMessageDialog(this, "Please enter a valid exam code", "Error", JOptionPane.ERROR_MESSAGE);
            }
            Boolean value = utils.EXAM_CODE.get(publishExamCode);
            if (value != null && value) {
                int res = JOptionPane.showConfirmDialog(
                        this,
                        "Are you sure to publish results?",
                        "Results",
                        JOptionPane.YES_NO_OPTION
                );
                if (res == JOptionPane.YES_OPTION) {
                    utils.EXAM_CODE.put(publishExamCode, false);
                    boolean hasRunningExams = false;
                    for (Boolean isRunning : utils.EXAM_CODE.values()) {
                        if (isRunning != null && isRunning) {
                            hasRunningExams = true;
                            break;
                        }
                    }
                    if (hasRunningExams) {
                        utils.PUBLISHED_STATUS = "Exam is running";
                    } else {
                        utils.PUBLISHED_STATUS = "No Exam is running";
                    }
                    Map<String, Account> studentAccounts = Main.getAccounts();
                    Result result = null;
                    for (Account acc : studentAccounts.values()) {
                        if (acc instanceof StudentAccount) {
                            StudentAccount studentAccount = (StudentAccount) acc;
                            Boolean examDone = studentAccount.getEXAM_DONE().get(publishExamCode);
                            if ((examDone == null || examDone == false) && studentAccount.getDepartment().equals(questionSet.getDepartment()) && studentAccount.getYear().equals(questionSet.getYear())) {
                                studentAccount.setResultInfo(publishExamCode, "You didn't participate in the exam");
                                studentAccount.setCg(0.0);
                                studentAccount.setCorrect(0);
                                studentAccount.setMark(0);
                                studentAccount.setIncorrect(0);
                                studentAccount.setEXAM_DONE(publishExamCode, false);
                                result = new Result(publishExamCode, department, year);
                            }
                        }
                    }
                    Main.getResultMap().put(publishExamCode, result);
                    questionStatusButton.setText(utils.PUBLISHED_STATUS);
                    JOptionPane.showMessageDialog(this, "Result Published", "Success", JOptionPane.INFORMATION_MESSAGE);
                    Notification notification = new Notification("Result published. code: " + publishExamCode + " Department: " + department
                            + " Year: " + year + " By " + account.getFirstName() + " " + account.getLastName());
                    Main.getNotifications().add(notification);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Question is not published", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void questionStatus(JFrame frame) {
        StringBuilder result = new StringBuilder();
        Boolean value = utils.EXAM_CODE.isEmpty();
        if (value != null && !value) {
            for (Map.Entry<String, Boolean> entry : utils.EXAM_CODE.entrySet()) {
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
            JOptionPane.showMessageDialog(frame, utils.ScrollPanel(result), "Exams", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(frame, utils.PUBLISHED_STATUS, "Exam Status", JOptionPane.INFORMATION_MESSAGE);
        }
    }

}