import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MCQQuestionCreator extends JFrame {

    private JTextField questionCodeField;
    private JPanel mcqPanel;
    private List<MCQComponent> mcqComponents;
    private int mcqCounter = 1;

    ArrayList<SingleQuestion> questionList;
    Account account;

    public MCQQuestionCreator(Account account) {

        this.account = account;

        mcqComponents = new ArrayList<>();
        initializeUI();
    }

    private void initializeUI() {
        setTitle("MCQ Question Creator");
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(245, 247, 250));

        JPanel headerPanel = createHeaderPanel();
        add(headerPanel, BorderLayout.NORTH);

        mcqPanel = new JPanel();
        mcqPanel.setLayout(new BoxLayout(mcqPanel, BoxLayout.Y_AXIS));
        mcqPanel.setBackground(new Color(255, 255, 255));
        JScrollPane scrollPane = new JScrollPane(mcqPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setPreferredSize(new Dimension(850, 400));
        scrollPane.getViewport().setBackground(new Color(255, 255, 255));
        add(scrollPane, BorderLayout.CENTER);

        // Button Panel
        JPanel buttonPanel = createButtonPanel();
        add(buttonPanel, BorderLayout.SOUTH);

        // Add first MCQ by default
        addMCQ();

        pack();
        setMinimumSize(new Dimension(900, 600));
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JPanel createHeaderPanel() {

        JPanel headerPanel = new JPanel(new GridBagLayout());
        headerPanel.setBackground(new Color(236, 240, 241));
        TitledBorder border = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)), "Question Details", TitledBorder.LEFT, TitledBorder.TOP, new Font("Arial", Font.BOLD, 16), new Color(52, 73, 94));
        headerPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(24, 32, 24, 32), border));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;

        // Question Code
        gbc.gridx = 0; gbc.gridy = 0;
        JLabel codeLabel = new JLabel("Question Code:");
        codeLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        headerPanel.add(codeLabel, gbc);
        gbc.gridx = 1;
        questionCodeField = new JTextField(30);
        questionCodeField.setFont(new Font("Arial", Font.PLAIN, 15));
        questionCodeField.setToolTipText("Enter a unique code for this exam");
        headerPanel.add(questionCodeField, gbc);

        return headerPanel;
    }

    private JPanel createButtonPanel() {

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 24, 12));
        buttonPanel.setBackground(new Color(245, 247, 250));

        JButton addMCQButton = new JButton("Add MCQ");
        styleButton(addMCQButton);
        addMCQButton.setToolTipText("Add a new MCQ question");
        addMCQButton.addActionListener(e -> addMCQ());

        JButton saveButton = new JButton("Save Question");
        styleButton(saveButton);
        saveButton.setToolTipText("Save the exam and all MCQs");
        saveButton.addActionListener(e -> {
            saveQuestion();
        });

        JButton clearButton = new JButton("Clear All");
        styleButton(clearButton);
        clearButton.setToolTipText("Clear all fields and MCQs");

        clearButton.addActionListener(e -> {

            int result = JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to clear all data?",
                    "Confirm Clear",
                    JOptionPane.YES_NO_OPTION);

            if (result == JOptionPane.YES_OPTION) {
                clearAll();
            }
        });

        JButton exitButton = new JButton("Back");
        styleButton(exitButton);
        exitButton.setToolTipText("Exit the Question Creator");
        exitButton.addActionListener(e -> {
            dispose();
            new TeacherPanel(account);
        });

        buttonPanel.add(addMCQButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(exitButton);

        return buttonPanel;
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

    private void addMCQ() {

        MCQComponent mcqComponent = new MCQComponent(mcqCounter++);
        mcqComponents.add(mcqComponent);
        mcqPanel.add(Box.createVerticalStrut(12));
        mcqPanel.add(mcqComponent);
        mcqPanel.revalidate();
        mcqPanel.repaint();
    }

    private void saveQuestion() {
        String questionCode = questionCodeField.getText().trim();

        if (questionCode.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter Question Code!",
                    "Missing Information", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (mcqComponents.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please add at least one MCQ!",
                    "No MCQs", JOptionPane.WARNING_MESSAGE);
            return;
        }

        questionList = new ArrayList();
        for (int i = 0; i < mcqComponents.size(); i++) {
            MCQComponent mcq = mcqComponents.get(i);
            String question = mcq.getQuestion().trim();
            String[] options = mcq.getOptions();
            String answer = mcq.getSelectedAnswer();

            if (question.isEmpty()) {
                JOptionPane.showMessageDialog(this, "MCQ " + (i + 1) + " question is empty!",
                        "Empty Question", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (answer == null) {
                JOptionPane.showMessageDialog(this, "Please select an answer for MCQ " + (i + 1) + "!",
                        "No Answer Selected", JOptionPane.WARNING_MESSAGE);
                return;
            }
            SingleQuestion singleQuestion = new SingleQuestion(question, options, answer);
            questionList.add(singleQuestion);
        }

        Question questionSet = new Question(questionList, questionCode);
        Main.getQuestionMap().put(questionCode, questionSet);

        JOptionPane.showMessageDialog(this, "Question saved successfully as " + questionCode + "!",
                "Success", JOptionPane.INFORMATION_MESSAGE);
        clearAll();

    }

    private void clearAll() {

            questionCodeField.setText("");
            mcqComponents.clear();
            mcqPanel.removeAll();
            mcqCounter = 1;
            addMCQ(); // Add one MCQ by default
            mcqPanel.revalidate();
            mcqPanel.repaint();

    }

    // Inner class for MCQ components
    private class MCQComponent extends JPanel {

        private JTextArea questionArea;
        private JTextField[] optionFields;
        private ButtonGroup answerGroup;
        private JRadioButton[] answerButtons;

        public MCQComponent(int number) {
            setBackground(new Color(236, 240, 241));
            setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createEmptyBorder(24, 32, 24, 32),
                    BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)), "MCQ", 0, 0, new Font("Arial", Font.BOLD, 16), new Color(52, 73, 94))
            ));            setLayout(new GridBagLayout());
            initializeComponents();
        }

        private void initializeComponents() {

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(6, 6, 6, 6);
            gbc.anchor = GridBagConstraints.WEST;

            // Question
            gbc.gridx = 0; gbc.gridy = 0;
            JLabel qLabel = new JLabel("Question:");
            qLabel.setFont(new Font("Arial", Font.PLAIN, 14));
            add(qLabel, gbc);
            gbc.gridx = 1; gbc.gridwidth = 3; gbc.fill = GridBagConstraints.HORIZONTAL;
            questionArea = new JTextArea(3, 40);
            questionArea.setLineWrap(true);
            questionArea.setWrapStyleWord(true);
            questionArea.setFont(new Font("Arial", Font.PLAIN, 14));
            questionArea.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(4, 4, 4, 4)));
            add(new JScrollPane(questionArea), gbc);

            // Options
            gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 1; gbc.fill = GridBagConstraints.NONE;
            JLabel optLabel = new JLabel("Options:");
            optLabel.setFont(new Font("Arial", Font.PLAIN, 14));
            add(optLabel, gbc);

            optionFields = new JTextField[4];
            answerButtons = new JRadioButton[4];
            answerGroup = new ButtonGroup();

            for (int i = 0; i < 4; i++) {
                gbc.gridx = 1; gbc.gridy = 1 + i; gbc.gridwidth = 1;
                optionFields[i] = new JTextField(25);
                optionFields[i].setFont(new Font("Arial", Font.PLAIN, 14));
                optionFields[i].setToolTipText("Option " + (i + 1));
                add(optionFields[i], gbc);

                gbc.gridx = 2;
                answerButtons[i] = new JRadioButton("Correct");
                answerButtons[i].setFont(new Font("Arial", Font.PLAIN, 13));
                answerButtons[i].setBackground(new Color(236, 240, 241));
                answerButtons[i].setToolTipText("Mark as correct answer");
                answerGroup.add(answerButtons[i]);
                add(answerButtons[i], gbc);
            }

            // Remove MCQ button
            gbc.gridx = 3; gbc.gridy = 1; gbc.gridheight = 4; gbc.anchor = GridBagConstraints.NORTHEAST;
            JButton removeButton = new JButton("Remove");
            styleButton(removeButton);
            removeButton.setBackground(new Color(231, 76, 60));
            removeButton.setToolTipText("Remove this MCQ");
            removeButton.addActionListener(e -> removeMCQ());
            add(removeButton, gbc);
        }

        private void removeMCQ() {

            mcqComponents.remove(this);
            mcqPanel.remove(this);
            mcqPanel.revalidate();
            mcqPanel.repaint();
        }

        public String getQuestion() {

            return questionArea.getText();
        }

        public String[] getOptions() {

            String[] options = new String[4];
            for (int i = 0; i < 4; i++) {
                options[i] = optionFields[i].getText();
            }
            return options;
        }

        public String getSelectedAnswer() {

            for (int i = 0; i < 4; i++) {
                if (answerButtons[i].isSelected()) {
                    return optionFields[i].getText();
                }
            }
            return null;
        }
    }
}