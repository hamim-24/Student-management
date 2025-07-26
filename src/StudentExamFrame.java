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

    StudentAccount account;
    String id;
    String resultCode;

    public StudentExamFrame(String id, String examCode) {

        this.id = id;
        this.resultCode = examCode;
        this.account = (StudentAccount) Main.getAccounts().get(id);


        Question q = null;
        try {
            q = Main.getQuestionMap().get(examCode);
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
                new StudentPanel(id);
            }
        });

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private double calculateCGPA(int correctAnswer, int totalAnswers) {
        double mark = correctAnswer / (double) totalAnswers * 100;
        if (mark >= 80) {
            return 4.0;
        } else if (mark >= 75) {
            return 3.75;
        } else if (mark >= 70) {
            return 3.50;
        }  else if (mark >= 65) {
            return 3.25;
        } else if (mark >= 60) {
            return 3.0;
        } else if (mark >= 55) {
            return 2.75;
        } else if (mark >= 50) {
            return 2.5;
        } else if (mark >= 45) {
            return 2.25;
        } else if (mark >= 40) {
            return 2.0;
        } else if (mark >= 35) {
            return 1.75;
        } else if (mark >= 30) {
            return 1.5;
        } else if (mark >= 25) {
            return 1.25;
        } else if (mark >= 20) {
            return 1.0;
        } else if (mark >= 15) {
            return 0.75;
        } else if (mark >= 10) {
            return 0.5;
        } else if (mark >= 5) {
            return 0.25;
        } else {
            return 0.0;
        }
    }

    private void updateTimerLabel() {
        int minutes = timeLeft / 60;
        int seconds = timeLeft % 60;
        timerLabel.setText(String.format("Time Left: %02d:%02d", minutes, seconds));
    }

    private void submitExam() {
        int correct = 0;
        int incorrect = 0;
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
            } else if (selected != null && !selected.equals(correctAnswer)) {
                incorrect++;
                result.append("Result: Incorrect\n\n");
            } else {
                result.append("Result: Skipped\n\n");
            }
        }
        result.append("Total Score: ").append(correct).append(" / ").append(totalQuestions);
        account.setResultInfo(resultCode, result.toString());
        account.setCg(calculateCGPA(correct, totalQuestions));
        account.setCorrect(correct);
        account.setIncorrect(incorrect);
        account.setMark((double) correct / totalQuestions * 100);

        dispose();
    }
} 