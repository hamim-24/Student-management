import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MCQQuestionCreator extends JFrame {
    private JTextField questionNameField;
    private JTextField questionCodeField;
    private JPanel mcqPanel;
    private List<MCQComponent> mcqComponents;
    private int mcqCounter = 1;

    public MCQQuestionCreator() {
        mcqComponents = new ArrayList<>();
        initializeUI();
    }

    private void initializeUI() {
        setTitle("MCQ Question Creator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Header Panel
        JPanel headerPanel = createHeaderPanel();
        add(headerPanel, BorderLayout.NORTH);

        // MCQ Panel with ScrollPane
        mcqPanel = new JPanel();
        mcqPanel.setLayout(new BoxLayout(mcqPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(mcqPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setPreferredSize(new Dimension(800, 400));
        add(scrollPane, BorderLayout.CENTER);

        // Button Panel
        JPanel buttonPanel = createButtonPanel();
        add(buttonPanel, BorderLayout.SOUTH);

        // Add first MCQ by default
        addMCQ();

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new GridBagLayout());
        headerPanel.setBorder(BorderFactory.createTitledBorder("Question Details"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Question Name
        gbc.gridx = 0; gbc.gridy = 0;
        headerPanel.add(new JLabel("Exam Name:"), gbc);
        gbc.gridx = 1;
        questionNameField = new JTextField(30);
        headerPanel.add(questionNameField, gbc);

        // Question Code
        gbc.gridx = 0; gbc.gridy = 1;
        headerPanel.add(new JLabel("Question Code:"), gbc);
        gbc.gridx = 1;
        questionCodeField = new JTextField(30);
        headerPanel.add(questionCodeField, gbc);

        return headerPanel;
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout());

        JButton addMCQButton = new JButton("Add MCQ");
        addMCQButton.addActionListener(e -> addMCQ());

        JButton saveButton = new JButton("Save Question");
        saveButton.addActionListener(e -> saveQuestion());

        JButton clearButton = new JButton("Clear All");
        clearButton.addActionListener(e -> clearAll());

        buttonPanel.add(addMCQButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(clearButton);

        return buttonPanel;
    }

    private void addMCQ() {
        MCQComponent mcqComponent = new MCQComponent(mcqCounter++);
        mcqComponents.add(mcqComponent);
        mcqPanel.add(mcqComponent);
        mcqPanel.revalidate();
        mcqPanel.repaint();
    }

    private void saveQuestion() {
        String questionName = questionNameField.getText().trim();
        String questionCode = questionCodeField.getText().trim();

        if (questionName.isEmpty() || questionCode.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter both Question Name and Question Code!",
                    "Missing Information", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (mcqComponents.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please add at least one MCQ!",
                    "No MCQs", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            String filename = questionCode + ".txt";
            FileWriter writer = new FileWriter(filename);

            // Write question details
            writer.write("\t\tExam Name: " + questionName + "\n");
            writer.write("\t\tQuestion Code: " + questionCode + "\n");
            writer.write("=" + "=".repeat(50) + "\n\n");

            // Write MCQs
            StringBuilder answers = new StringBuilder();
            for (int i = 0; i < mcqComponents.size(); i++) {
                MCQComponent mcq = mcqComponents.get(i);
                String question = mcq.getQuestion().trim();
                String[] options = mcq.getOptions();
                String answer = mcq.getSelectedAnswer();

                if (question.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "MCQ " + (i + 1) + " question is empty!",
                            "Empty Question", JOptionPane.WARNING_MESSAGE);
                    writer.close();
                    return;
                }

                if (answer == null) {
                    JOptionPane.showMessageDialog(this, "Please select an answer for MCQ " + (i + 1) + "!",
                            "No Answer Selected", JOptionPane.WARNING_MESSAGE);
                    writer.close();
                    return;
                }

                writer.write("Q" + (i + 1) + ". " + question + "\n");
                writer.write("A) " + options[0] + "\n");
                writer.write("B) " + options[1] + "\n");
                writer.write("C) " + options[2] + "\n");
                writer.write("D) " + options[3] + "\n");
                writer.write("\n");

                answers.append(answer);
                if (i < mcqComponents.size() - 1) {
                    answers.append(", ");
                }
            }

            // Write answers in the last line
            writer.write(answers.toString());
            writer.close();

            JOptionPane.showMessageDialog(this, "Question saved successfully as " + filename + "!",
                    "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving file: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearAll() {
        int result = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to clear all data?",
                "Confirm Clear",
                JOptionPane.YES_NO_OPTION);

        if (result == JOptionPane.YES_OPTION) {
            questionNameField.setText("");
            questionCodeField.setText("");
            mcqComponents.clear();
            mcqPanel.removeAll();
            mcqCounter = 1;
            addMCQ(); // Add one MCQ by default
            mcqPanel.revalidate();
            mcqPanel.repaint();
        }
    }

    // Inner class for MCQ components
    private class MCQComponent extends JPanel {
        private JTextArea questionArea;
        private JTextField[] optionFields;
        private ButtonGroup answerGroup;
        private JRadioButton[] answerButtons;

        public MCQComponent(int number) {
            setBorder(BorderFactory.createTitledBorder("MCQ " + number));
            setLayout(new GridBagLayout());
            initializeComponents();
        }

        private void initializeComponents() {
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(5, 5, 5, 5);
            gbc.anchor = GridBagConstraints.WEST;

            // Question
            gbc.gridx = 0; gbc.gridy = 0;
            add(new JLabel("Question:"), gbc);
            gbc.gridx = 1; gbc.gridwidth = 3; gbc.fill = GridBagConstraints.HORIZONTAL;
            questionArea = new JTextArea(3, 40);
            questionArea.setLineWrap(true);
            questionArea.setWrapStyleWord(true);
            questionArea.setBorder(BorderFactory.createLoweredBevelBorder());
            add(new JScrollPane(questionArea), gbc);

            // Options
            optionFields = new JTextField[4];
            answerButtons = new JRadioButton[4];
            answerGroup = new ButtonGroup();
            String[] labels = {"A)", "B)", "C)", "D)"};

            for (int i = 0; i < 4; i++) {
                gbc.gridx = 0; gbc.gridy = i + 1; gbc.gridwidth = 1; gbc.fill = GridBagConstraints.NONE;
                add(new JLabel(labels[i]), gbc);

                gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
                optionFields[i] = new JTextField(30);
                add(optionFields[i], gbc);

                gbc.gridx = 2; gbc.fill = GridBagConstraints.NONE;
                answerButtons[i] = new JRadioButton();
                answerButtons[i].setActionCommand(labels[i].substring(0, 1)); // A, B, C, D
                answerGroup.add(answerButtons[i]);
                add(answerButtons[i], gbc);

                gbc.gridx = 3;
                add(new JLabel("Answer"), gbc);
            }

            // Remove MCQ button
            gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 4;
            JButton removeButton = new JButton("Remove This MCQ");
            removeButton.addActionListener(e -> removeMCQ());
            add(removeButton, gbc);
        }

        private void removeMCQ() {
            if (mcqComponents.size() > 1) {
                mcqComponents.remove(this);
                mcqPanel.remove(this);
                mcqPanel.revalidate();
                mcqPanel.repaint();
                updateMCQNumbers();
            } else {
                JOptionPane.showMessageDialog(this, "At least one MCQ is required!",
                        "Cannot Remove", JOptionPane.WARNING_MESSAGE);
            }
        }

        public String getQuestion() {
            return questionArea.getText();
        }

        public String[] getOptions() {
            String[] options = new String[4];
            for (int i = 0; i < 4; i++) {
                options[i] = optionFields[i].getText().trim();
            }
            return options;
        }

        public String getSelectedAnswer() {
            return answerGroup.getSelection() != null ?
                    answerGroup.getSelection().getActionCommand() : null;
        }
    }

    private void updateMCQNumbers() {
        for (int i = 0; i < mcqComponents.size(); i++) {
            MCQComponent mcq = mcqComponents.get(i);
            TitledBorder border = (TitledBorder) mcq.getBorder();
            border.setTitle("MCQ " + (i + 1));
        }
        mcqPanel.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {

            new MCQQuestionCreator();
        });
    }
}