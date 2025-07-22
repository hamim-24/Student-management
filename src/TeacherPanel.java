import javax.swing.*;
import java.awt.*;
import java.util.Map;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class TeacherPanel extends JFrame {
    Map<String, Question> questionMap;
    Question questionSet;
    Account account;
    private JPanel mainPanel;
    JTextField examSearchField;
    JLabel questionStatusLabel = new JLabel();

    public TeacherPanel(Account account) {

        this.questionMap = Main.getQuestionMap();
        this.account = account;

        setTitle("Teacher Panel");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(245, 247, 250));

        // Header
        JLabel headerLabel = new JLabel("Teacher Dashboard", SwingConstants.CENTER);
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

        questionStatusLabel = new JLabel(utils.PUBLISHED_STATUS);
        questionStatusLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        questionStatusLabel.setForeground(new Color(231, 76, 60));
        questionStatusLabel.setPreferredSize(new Dimension(250, 20));
        mainPanel.add(questionStatusLabel, gbc);
        gbc.gridy++;

        // Create Exam Button
        JButton createExamButton = new JButton("Create New Exam");
        utils.styleButton(createExamButton);
        createExamButton.setToolTipText("Create a new MCQ exam");
        mainPanel.add(createExamButton, gbc);

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

        // Search/View Exam Button
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        JButton searchExamButton = new JButton("Search/View Exam");
        utils.styleButton(searchExamButton);
        searchExamButton.setToolTipText("Search for an existing exam by code");
        mainPanel.add(searchExamButton, gbc);


        // Publish Exam Button
        gbc.gridy++;
        JButton publishExamButton = new JButton("Publish Exam");
        utils.styleButton(publishExamButton);
        publishExamButton.setToolTipText("Publish an exam for students");
        mainPanel.add(publishExamButton, gbc);

        // Publish Results Button
        gbc.gridy++;
        JButton publishResultButton = new JButton("Publish Results");
        utils.styleButton(publishResultButton);
        publishResultButton.setToolTipText("Publish results for an exam");
        mainPanel.add(publishResultButton, gbc);

        // Back Button
        gbc.gridy++;
        JButton backButton = new JButton("Back to Main Menu");
        utils.styleButton(backButton);
        backButton.setToolTipText("Return to main menu");
        mainPanel.add(backButton, gbc);

        add(mainPanel, BorderLayout.CENTER);

        // Button actions
        createExamButton.addActionListener(e -> {
            dispose();
            new MCQQuestionCreator(account);
        });
        searchExamButton.addActionListener(e -> {
            String searchExam = examSearchField.getText().trim();
            questionSet = questionMap.get(searchExam);
            if (questionSet == null) {
                JOptionPane.showMessageDialog(this, "No Question Found!", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                StringBuilder qs = new StringBuilder();
                qs.append("Exam Name: " + questionSet.getExamName() + "\n");
                qs.append("Exam Code: " + questionSet.getQuestionCode() + "\n\n");
                int i = 1;
                for (SingleQuestion SQ : questionSet.getSingleQuestions()) {
                    qs.append("  " + i + ". " + SQ.toString() + "\n");
                    i++;
                }
                JTextArea textArea = new JTextArea(qs.toString());
                textArea.setEditable(false);
                textArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
                textArea.setBackground(new Color(245, 247, 250));
                JScrollPane scrollPane = new JScrollPane(textArea);
                scrollPane.setPreferredSize(new Dimension(400, 250));
                JOptionPane.showMessageDialog(this, scrollPane, "Exam Details", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        publishExamButton.addActionListener(e -> {
            String publishExam = examSearchField.getText().trim();
            questionSet = questionMap.get(publishExam);
            if (questionSet != null && !utils.IS_PUBLISHED) {
                int result = JOptionPane.showConfirmDialog(this, "Are you sure to publish question code: " + questionSet.getQuestionCode() + "?", "Publish Results", JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                    utils.QUESTION_CODE = publishExam;
                    utils.IS_PUBLISHED = true;
                    utils.PUBLISHED_STATUS = "Exam code: '" + publishExam + "' Running";
                    questionStatusLabel.setText(utils.PUBLISHED_STATUS);
                    questionStatusLabel.setText(utils.PUBLISHED_STATUS);
                }
            } else if (utils.IS_PUBLISHED) {
                JOptionPane.showMessageDialog(this, "Exam Code: '" + utils.QUESTION_CODE + "' is already published", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Question is not found", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        publishResultButton.addActionListener(e -> {
            if (utils.IS_PUBLISHED) {
                int res = JOptionPane.showConfirmDialog(this, "Are you sure to publish results?", "Results", JOptionPane.YES_NO_OPTION);
                if  (res == JOptionPane.YES_OPTION) {
                    utils.QUESTION_CODE = null;
                    utils.IS_PUBLISHED = false;
                    utils.PUBLISHED_STATUS = "No Exam is running";

                    Map<String, Account> studentAccounts = Main.getAccounts();
                    for (Account acc : studentAccounts.values()) {
                        if (acc.getStatus().equals("Student")) {
                            StudentAccount studentAccount = (StudentAccount) acc;
                            if (studentAccount.getEXAM_DONE() == false) {
                                studentAccount.setResultInfo("You didn't participate in the exam");
                            }
                            ((StudentAccount) acc).setEXAM_DONE(false);
                        }
                    }

                    questionStatusLabel.setText(utils.PUBLISHED_STATUS);
                    JOptionPane.showMessageDialog(this, "Result Published", "Success", JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Question is not found", "Error", JOptionPane.ERROR_MESSAGE);
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
                publishExamButton.setText("Publish '" + code + "' Exam");
                publishResultButton.setText("Publish '" + code + "' Results");

                // Add these lines to force UI refresh
                mainPanel.revalidate();
                mainPanel.repaint();
            }
        });

        pack();
        setMinimumSize(new Dimension(500, 600));
        setLocationRelativeTo(null);
        setVisible(true);
    }

} 