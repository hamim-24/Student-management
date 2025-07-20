import javax.swing.*;
import java.awt.*;

public class TeacherPanel extends JFrame {
    Account account;
    public TeacherPanel(Account account) {

        this.account = account;

        setTitle("Teacher Panel");
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JButton createExamButton = new JButton("Create New Exam");
        JButton searchExamButton = new JButton("Search/View Exams");
        JButton publishExamButton = new JButton("Publish Exam");
        JButton publishResultButton = new JButton("Publish Results");
        JButton backButton = new JButton("Back to Main Menu");

        createExamButton.addActionListener(e -> new MCQQuestionCreator());
        searchExamButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "Search/View Exams coming soon!"));
        publishExamButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "Publish Exam coming soon!"));
        publishResultButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "Publish Results coming soon!"));
        backButton.addActionListener(e -> {
            new LoginForm();
            dispose();
        });

        gbc.gridx = 0; gbc.gridy = 0;
        add(createExamButton, gbc);
        gbc.gridy++;
        add(searchExamButton, gbc);
        gbc.gridy++;
        add(publishExamButton, gbc);
        gbc.gridy++;
        add(publishResultButton, gbc);
        gbc.gridy++;
        add(backButton, gbc);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
} 