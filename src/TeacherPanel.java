import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class TeacherPanel extends JFrame {
    Map<String, Question> questionMap;
    Question questionSet;
    Account account;
    private JPanel mainPanel;
    public TeacherPanel(Account account) {
        this.questionMap = Main.getQuestionMap();
        this.account = account;

        setTitle("Teacher Panel");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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

        // Create Exam Button
        JButton createExamButton = new JButton("Create New Exam");
        styleButton(createExamButton);
        createExamButton.setToolTipText("Create a new MCQ exam");
        mainPanel.add(createExamButton, gbc);

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
        styleButton(searchExamButton);
        searchExamButton.setToolTipText("Search for an existing exam by code");
        mainPanel.add(searchExamButton, gbc);


        // Publish Exam Button
        gbc.gridy++;
        JButton publishExamButton = new JButton("Publish Exam");
        styleButton(publishExamButton);
        publishExamButton.setToolTipText("Publish an exam for students");
        mainPanel.add(publishExamButton, gbc);

        // Publish Results Button
        gbc.gridy++;
        JButton publishResultButton = new JButton("Publish Results");
        styleButton(publishResultButton);
        publishResultButton.setToolTipText("Publish results for an exam");
        mainPanel.add(publishResultButton, gbc);

        // Back Button
        gbc.gridy++;
        JButton backButton = new JButton("Back to Main Menu");
        styleButton(backButton);
        backButton.setToolTipText("Return to main menu");
        mainPanel.add(backButton, gbc);

        add(mainPanel, BorderLayout.CENTER);

        // Button actions
        createExamButton.addActionListener(e -> new MCQQuestionCreator());
        searchExamButton.addActionListener(e -> {
            String searchExam = examSearchField.getText().trim();
            questionSet = questionMap.get(searchExam);
            if(questionSet == null){
                JOptionPane.showMessageDialog(this, "No Question Found!", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                StringBuilder qs = new StringBuilder();
                qs.append("Exam Name: " + questionSet.getQuestionName() + "\n");
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
        publishExamButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "Publish Exam coming soon!"));
        publishResultButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "Publish Results coming soon!"));
        backButton.addActionListener(e -> {
            new LoginForm();
            dispose();
        });

        examSearchField.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { updateButtons(); }
            public void removeUpdate(DocumentEvent e) { updateButtons(); }
            public void changedUpdate(DocumentEvent e) { updateButtons(); }
            private void updateButtons() {
                String code = examSearchField.getText().trim();
                if (!code.isEmpty()) {
                    searchExamButton.setText("Search/View " + code + " Exam");
                    publishExamButton.setText("Publish " + code + " Exam");
                    publishResultButton.setText("Publish " + code + " Results");
                } else {
                    searchExamButton.setText("Search/View Exam");
                    publishExamButton.setText("Publish Exam");
                    publishResultButton.setText("Publish Results");
                }
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

    private void styleButton(JButton button) {
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setBackground(new Color(52, 152, 219));
        button.setForeground(Color.BLACK);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(41, 128, 185)),
                BorderFactory.createEmptyBorder(8, 18, 8, 18)));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(41, 128, 185));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(52, 152, 219));
            }
        });
    }
} 