package ui;

import launcher.Main;
import model.*;
import util.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class StudentExamFrame extends JFrame {
    private Question question;
    private JLabel timerLabel;
    private Timer timer;
    private int timeLeft; // in seconds
    private List<SingleQuestion> singleQuestions;
    private JRadioButton[][] optionButtons;
    private int totalQuestions;
    private boolean submitted = false;

    StudentAccount account;
    String resultCode;

    public StudentExamFrame(StudentAccount account, String examCode) {
        // Validate parameters first
        if (account == null) {
            JOptionPane.showMessageDialog(null, "Invalid account information", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (examCode == null || examCode.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Invalid exam code", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        this.account = account;
        this.resultCode = examCode;

        initializeExamData(examCode);
        initializeFrame();
        createComponents();
        setupTimer();
        setVisible(true);
    }

    private void initializeExamData(String examCode) {

        try {
            Question q = Main.getQuestionMap().get(examCode);
            if (q == null) {
                throw new Exception("No exam found for the given code!");
            }

            List<SingleQuestion> questions = q.getSingleQuestions();
            int questionsCount = questions.size();
            JRadioButton[][] buttons = new JRadioButton[questionsCount][4];

            // Initialize final fields with validated values
            this.question = q;
            this.singleQuestions = questions;
            this.totalQuestions = questionsCount;
            this.optionButtons = buttons;
            this.timeLeft = questionsCount * 30;

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            dispose();
            new StudentPanel(account);
        }
    }

    private void initializeFrame() {

        setTitle("Exam: " + question.getExamName());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        setMinimumSize(new Dimension(900, 600));
        setLocationRelativeTo(null);
    }

    private void createComponents() {

        createHeaderPanel();
        createQuestionsPanel();
        createButtonPanel();
    }

    private void createHeaderPanel() {

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
    }

    private void createQuestionsPanel() {

        JPanel questionsPanel = new JPanel();
        questionsPanel.setLayout(new BoxLayout(questionsPanel, BoxLayout.Y_AXIS));
        questionsPanel.setBackground(Color.WHITE);

        for (int i = 0; i < totalQuestions; i++) {
            createQuestionPanel(questionsPanel, i);
        }

        JScrollPane scrollPane = new JScrollPane(questionsPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void createQuestionPanel(JPanel questionsPanel, int questionIndex) {

        SingleQuestion sq = singleQuestions.get(questionIndex);
        JPanel qPanel = new JPanel(new GridBagLayout());
        qPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(16, 16, 16, 16),
                BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)),
                        "Question " + (questionIndex + 1), 0, 0, new Font("Arial", Font.BOLD, 16), new Color(52, 73, 94))));
        qPanel.setBackground(new Color(245, 247, 250));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0;
        gbc.gridy = 0;
        
        // Question text
        JLabel qText = new JLabel("<html><b>" + sq.getQuestion() + "</b></html>");
        qText.setFont(new Font("Arial", Font.PLAIN, 16));
        qPanel.add(qText, gbc);

        // Options
        ButtonGroup answerGroup = new ButtonGroup();
        String[] options = sq.getOptions();
        for (int j = 0; j < 4; j++) {
            gbc.gridy++;
            optionButtons[questionIndex][j] = new JRadioButton(options[j]);
            optionButtons[questionIndex][j].setFont(new Font("Arial", Font.PLAIN, 15));
            answerGroup.add(optionButtons[questionIndex][j]);
            qPanel.add(optionButtons[questionIndex][j], gbc);
        }
        
        questionsPanel.add(qPanel);
        questionsPanel.add(Box.createVerticalStrut(10));
    }

    private void createButtonPanel() {

        JButton submitButton = new JButton("Submit");
        Utils.styleButton(submitButton);
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(submitButton);
        add(buttonPanel, BorderLayout.SOUTH);
        
        // Add submit button action
        submitButton.addActionListener(e -> handleSubmitAction());
    }

    private void setupTimer() {

        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleTimerTick();
            }
        });
        updateTimerLabel();
        timer.start();
    }

    private void handleTimerTick() {

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

    private void handleSubmitAction() {

        if (!submitted) {
            submitted = true;
            timer.stop();
            submitExam();
            new StudentPanel(account);
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
        
        buildResultString(result);
        calculateResults(result, correct, incorrect);
        dispose();
    }

    private void buildResultString(StringBuilder result) {

        result.append("Exam: ").append(question.getExamName()).append("\n");
        result.append("Code: ").append(question.getQuestionCode()).append("\n\n");
        
        for (int i = 0; i < totalQuestions; i++) {
            SingleQuestion sq = singleQuestions.get(i);
            String correctAnswer = sq.getAnswer();
            String selected = getSelectedAnswer(i);
            
            result.append("Q").append(i + 1).append(": ").append(sq.getQuestion()).append("\n");
            result.append("Your answer: ").append(selected == null ? "(No answer)" : selected).append("\n");
            result.append("Correct answer: ").append(correctAnswer).append("\n");
            
            if (selected != null && selected.equals(correctAnswer)) {
                result.append("Result: Correct\n\n");
            } else if (selected != null && !selected.equals(correctAnswer)) {
                result.append("Result: Incorrect\n\n");
            } else {
                result.append("Result: Skipped\n\n");
            }
        }
    }

    private String getSelectedAnswer(int questionIndex) {

        for (int j = 0; j < 4; j++) {
            if (optionButtons[questionIndex][j].isSelected()) {
                return optionButtons[questionIndex][j].getText();
            }
        }
        return null;
    }

    private void calculateResults(StringBuilder result, int correct, int incorrect) {

        for (int i = 0; i < totalQuestions; i++) {
            SingleQuestion sq = singleQuestions.get(i);
            String correctAnswer = sq.getAnswer();
            String selected = getSelectedAnswer(i);
            
            if (selected != null && selected.equals(correctAnswer)) {
                correct++;
            } else if (selected != null && !selected.equals(correctAnswer)) {
                incorrect++;
            }
        }
        double mark = (double) correct / (double) totalQuestions * 100;
        result.append("Total Score: ").append(correct).append(" / ").append(totalQuestions);

        double averageCG = getAverageCG(correct, incorrect);
        account.setCg(averageCG);
        Result re = new Result(account, mark, correct, incorrect, resultCode, result.toString());
        Main.getResultList().add(re);

    }

    private double getAverageCG(int correct, int incorrect) {

        double cg = account.getCg();
        double currentCG = calculateCGPA(correct, incorrect);
        double totalCG = (currentCG + cg) / 2;
        int totalExam = 1;

        for (Result r : Main.getResultList()) {
            String id = r.getId();
            String department = r.getDepartment();
            String session = r.getSession();
            if (id.equals(account.getID()) && session.equals(account.getSession()) && department.equals(account.getDepartment())) {
                totalCG += r.getCg();
                totalExam++;
            }
        }
        return (totalExam > 0) ? totalCG / totalExam : currentCG;
    }

    private double calculateCGPA(int correctAnswer, int totalAnswers) {

        double mark = correctAnswer / (double) totalAnswers * 100;
        if (mark >= 80) {
            return 4.0;
        } else if (mark >= 75) {
            return 3.75;
        } else if (mark >= 70) {
            return 3.50;
        } else if (mark >= 65) {
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
} 