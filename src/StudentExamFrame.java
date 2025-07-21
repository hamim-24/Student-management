import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class StudentExamFrame extends JFrame {
    private final Question question;
    private final JLabel timerLabel;
    private final Timer timer;
    private int timeLeft; // in seconds
    private final List<SingleQuestion> singleQuestions;
    private final ButtonGroup[] answerGroups;
    private final JRadioButton[][] optionButtons;
    private final int totalQuestions;
    private boolean submitted = false;

    Account account;

    public StudentExamFrame(Account account) {

        this.account = account;
        this.account = account;
        Question q = null;
        try {
            q = Main.getQuestionMap().get(utils.QUESTION_CODE);
            if (q == null) {
                throw new Exception("No exam found for the given code!");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            dispose();
            // Assign dummy values to final fields to satisfy the compiler
            this.question = null;
            this.singleQuestions = null;
            this.totalQuestions = 0;
            this.answerGroups = null;
            this.optionButtons = null;
            this.timer = null;
            this.timerLabel = null;
            return;
        }
        this.question = q;
        this.singleQuestions = question.getSingleQuestions();
        this.totalQuestions = singleQuestions.size();
        this.answerGroups = new ButtonGroup[totalQuestions];
        this.optionButtons = new JRadioButton[totalQuestions][4];
        this.timeLeft = totalQuestions * 30; 

        setTitle("Exam: " + question.getExamName());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        setMinimumSize(new Dimension(900, 600));

        // Header panel with exam name, code, and timer
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBorder(BorderFactory.createEmptyBorder(16, 24, 16, 24));
        JLabel nameLabel = new JLabel(question.getExamName() + ",    Code: " + question.getQuestionCode());
        nameLabel.setFont(new Font("Arial", Font.BOLD, 22));
        headerPanel.add(nameLabel, BorderLayout.WEST);
        timerLabel = new JLabel();
        timerLabel.setFont(new Font("Arial", Font.BOLD, 22));
        timerLabel.setForeground(new Color(231, 76, 60));
        headerPanel.add(timerLabel, BorderLayout.EAST);
        add(headerPanel, BorderLayout.NORTH);

        // Center panel with questions in a scroll pane
        JPanel questionsPanel = new JPanel();
        questionsPanel.setLayout(new BoxLayout(questionsPanel, BoxLayout.Y_AXIS));
        questionsPanel.setBackground(Color.WHITE);

        for (int i = 0; i < totalQuestions; i++) {
            SingleQuestion sq = singleQuestions.get(i);
            JPanel qPanel = new JPanel(new GridBagLayout());
            qPanel.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createEmptyBorder(16, 16, 16, 16),
                    BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)),
                            "Question " + (i + 1), 0, 0, new Font("Arial", Font.BOLD, 16), new Color(52, 73, 94))));
            qPanel.setBackground(new Color(245, 247, 250));
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(6, 6, 6, 6);
            gbc.anchor = GridBagConstraints.WEST;
            gbc.gridx = 0; gbc.gridy = 0;
            JLabel qText = new JLabel("<html><b>" + sq.getQuestion() + "</b></html>");
            qText.setFont(new Font("Arial", Font.PLAIN, 16));
            qPanel.add(qText, gbc);

            // Options
            answerGroups[i] = new ButtonGroup();
            String[] options = sq.getOptions();
            for (int j = 0; j < 4; j++) {
                gbc.gridy++;
                optionButtons[i][j] = new JRadioButton(options[j]);
                optionButtons[i][j].setFont(new Font("Arial", Font.PLAIN, 15));
                answerGroups[i].add(optionButtons[i][j]);
                qPanel.add(optionButtons[i][j], gbc);
            }
            questionsPanel.add(qPanel);
            questionsPanel.add(Box.createVerticalStrut(10));
        }

        JScrollPane scrollPane = new JScrollPane(questionsPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane, BorderLayout.CENTER);

        // Submit button
        JButton submitButton = new JButton("Submit");
        utils.styleButton(submitButton);
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(submitButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Timer logic
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timeLeft--;
                updateTimerLabel();
                if (timeLeft <= 0) {
                    timer.stop();
                    if (!submitted) {
                        submitted = true;
                        submitExam();
                    }
                }
            }
        });
        updateTimerLabel();
        timer.start();

        // Submit button action
        submitButton.addActionListener(e -> {
            if (!submitted) {
                submitted = true;
                timer.stop();
                submitExam();
                new StudentPanel(account);
            }
        });

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void updateTimerLabel() {
        int minutes = timeLeft / 60;
        int seconds = timeLeft % 60;
        timerLabel.setText(String.format("Time Left: %02d:%02d", minutes, seconds));
    }

    private void submitExam() {
        int correct = 0;
        StringBuilder result = new StringBuilder();
        result.append("Exam: ").append(question.getExamName()).append("\n");
        result.append("Code: ").append(question.getQuestionCode()).append("\n\n");
        for (int i = 0; i < totalQuestions; i++) {
            SingleQuestion sq = singleQuestions.get(i);
            String correctAnswer = sq.getAnswer();
            String selected = null;
            for (int j = 0; j < 4; j++) {
                if (optionButtons[i][j].isSelected()) {
                    selected = optionButtons[i][j].getText();
                    break;
                }
            }
            result.append("Q").append(i + 1).append(": ").append(sq.getQuestion()).append("\n");
            result.append("Your answer: ").append(selected == null ? "(No answer)" : selected).append("\n");
            result.append("Correct answer: ").append(correctAnswer).append("\n");
            if (selected != null && selected.equals(correctAnswer)) {
                result.append("Result: Correct\n\n");
                correct++;
            } else {
                result.append("Result: Incorrect\n\n");
            }
        }
        result.append("Total Score: ").append(correct).append(" / ").append(totalQuestions);
        JTextArea textArea = new JTextArea(result.toString());
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        textArea.setBackground(new Color(245, 247, 250));
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(400, 250));
        JOptionPane.showMessageDialog(this, scrollPane, "Exam Result", JOptionPane.INFORMATION_MESSAGE);
        dispose();
    }
} 