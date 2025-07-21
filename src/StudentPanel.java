import javax.swing.*;
import java.awt.*;
import java.util.Map;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class StudentPanel extends JFrame {
    Map<String, Question> questionMap;
    Question questionSet;
    Account account;
    private JPanel mainPanel;
    JLabel questionStatusLabel;

    public StudentPanel(Account account) {
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

        questionStatusLabel = new JLabel(utils.PUBLISHED_STATUS);
        questionStatusLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        questionStatusLabel.setForeground(new Color(231, 76, 60));
        questionStatusLabel.setPreferredSize(new Dimension(250, 20));
        mainPanel.add(questionStatusLabel, gbc);
        gbc.gridy++;

        // Create Exam Button
        JButton examButton = new JButton("EXAM");
        utils.styleButton(examButton);
        examButton.setToolTipText("EXAM");
        mainPanel.add(examButton, gbc);

        // Search area
        gbc.gridy++;
        gbc.gridwidth = 1;
        JLabel searchLabel = new JLabel("Enter Exam Code:");
        searchLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        mainPanel.add(searchLabel, gbc);

        gbc.gridx = 1;
        JTextField examSearchField = new JTextField(15);
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
        JButton showInfo = new JButton("Show Information");
        utils.styleButton(showInfo);
        showInfo.setToolTipText("Show my Information");
        mainPanel.add(showInfo, gbc);

        // Publish Results Button
        gbc.gridy++;
        JButton publishResultButton = new JButton("Results");
        utils.styleButton(publishResultButton);
        publishResultButton.setToolTipText("show results");
        mainPanel.add(publishResultButton, gbc);

        // Back Button
        gbc.gridy++;
        JButton backButton = new JButton("Back");
        utils.styleButton(backButton);
        backButton.setToolTipText("Return to main menu");
        mainPanel.add(backButton, gbc);

        add(mainPanel, BorderLayout.CENTER);

        // Button actions
        examButton.addActionListener(e -> {
            if (utils.IS_PUBLISHED) {
                dispose();
                new StudentExamFrame(utils.QUESTION_CODE);
            } else  {
                JOptionPane.showMessageDialog(StudentPanel.this, "No exam found", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        searchExamButton.addActionListener(e -> {
            String searchExam = examSearchField.getText().trim();
            questionSet = questionMap.get(searchExam);
            if (questionSet == null) {
                JOptionPane.showMessageDialog(this, "No Question Found!", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (questionSet.getQuestionCode().equals(utils.QUESTION_CODE)) {
                JOptionPane.showMessageDialog(this, "The Exam is running", "Error", JOptionPane.ERROR_MESSAGE);
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
        showInfo.addActionListener(e -> JOptionPane.showMessageDialog(this, account, "Info", JOptionPane.INFORMATION_MESSAGE));
        publishResultButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "Publish Results coming soon!"));
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