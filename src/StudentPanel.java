import javax.swing.*;
import java.awt.*;
import java.util.Map;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class StudentPanel extends JFrame {
    Map<String, Question> questionMap;
    StudentAccount account;
    private JPanel mainPanel;
    JButton questionStatusButton;
    JTextField examSearchField;

    public StudentPanel(String id) {
        this.questionMap = Main.getQuestionMap();
        this.account = (StudentAccount) Main.getAccounts().get(id);

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


        // Publish Exam Button
        gbc.gridy++;
        JButton showInfo = new JButton("Show Information");
        utils.styleButton(showInfo);
        showInfo.setToolTipText("Show my Information");
        mainPanel.add(showInfo, gbc);

        // Publish Results Button
        gbc.gridy++;
        JButton resultButton = new JButton("Results");
        utils.styleButton(resultButton);
        resultButton.setToolTipText("show results");
        mainPanel.add(resultButton, gbc);

        // Back Button
        gbc.gridy++;
        JButton backButton = new JButton("Back");
        utils.styleButton(backButton);
        backButton.setToolTipText("Return to main menu");
        mainPanel.add(backButton, gbc);

        add(mainPanel, BorderLayout.CENTER);

        // Button actions
        examButton.addActionListener(e -> {

            String examCode = examSearchField.getText().trim();
            if (examCode.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a valid exam code", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                Boolean examRunning = utils.EXAM_CODE.get(examCode);
                if (examRunning != null) {
                    Question question = Main.getQuestionMap().get(examCode);
                    if (question != null) {
                        String examYear = question.getYear().trim();
                        String examDepartment = question.getDepartment().trim();
                        if (account.getDepartment().equals(examDepartment) && account.getYear().equals(examYear)) {
                            Boolean examDone = account.getEXAM_DONE().get(examCode);
                            if (examDone == null || !examDone) {
                                account.setEXAM_DONE(examCode, true);
                                dispose();
                                new StudentExamFrame(id, examCode);
                            } else {
                                JOptionPane.showMessageDialog(StudentPanel.this, "Your exam is done", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        } else {
                            JOptionPane.showMessageDialog(this, "Exam is not for you!", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "Exam not found", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Exam is not found", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        searchExamButton.addActionListener(e -> {
            searchExam(examSearchField, false, this);
        });
        showInfo.addActionListener(e -> JOptionPane.showMessageDialog(this, account, "Info", JOptionPane.INFORMATION_MESSAGE));
        resultButton.addActionListener(e -> {
            String examCode = examSearchField.getText().trim();
            if (examCode.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a valid exam code", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                Boolean examRunning = utils.EXAM_CODE.get(examCode);
                if (examRunning != null) {
                    if (!examRunning) {
                        Map<String, String> resultInfo = account.getResultInfo();
                        if (resultInfo != null && !resultInfo.isEmpty()) {
                            StringBuilder result = new StringBuilder();
                            result.append(resultInfo.get(examCode));

                            JOptionPane.showMessageDialog(this, TeacherPanel.ScrollPanel(result), "Result Details", JOptionPane.INFORMATION_MESSAGE);
                        }
                    } else if (examRunning) {
                        JOptionPane.showMessageDialog(this, "Result didn't published", "Result Details", JOptionPane.INFORMATION_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "No results available for this exam", "Result Details", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
        backButton.addActionListener(e -> {
            this.dispose();
            new LoginForm();
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

                String code = examSearchField.getText().trim();
                searchExamButton.setText("Search/View '" + code + "' Exam");
                examButton.setText("EXAM " + code);
                resultButton.setText("Results " + code);

                // Add these lines to force UI refresh
                mainPanel.revalidate();
                mainPanel.repaint();
            }
        });

        questionStatusButton.addActionListener(e -> {
           TeacherPanel.questionStatus(this);
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
                JOptionPane.showMessageDialog(frame, TeacherPanel.ScrollPanel(qs), "Exam Details", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

} 