import javax.swing.*;
import java.awt.*;

public class StudentPanel extends JFrame {
    Account account;
    public StudentPanel(Account account) {
        this.account = account;
        setTitle(account.getFirstName() + " " + account.getLastName());
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel availableExamsLabel = new JLabel("Available Exams:");
        JList<String> examList = new JList<>(new String[]{"(No published exams yet)"});
        JScrollPane examScroll = new JScrollPane(examList);
        examScroll.setPreferredSize(new Dimension(250, 100));

        JButton takeExamButton = new JButton("Take Exam");
        JButton viewResultButton = new JButton("View Results");
        JButton backButton = new JButton("Back to Main Menu");

        takeExamButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "Take Exam coming soon!"));
        viewResultButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "View Results coming soon!"));
        backButton.addActionListener(e -> {
            new LoginForm();
            dispose();
        });

        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        add(availableExamsLabel, gbc);
        gbc.gridy++;
        add(examScroll, gbc);
        gbc.gridwidth = 1;
        gbc.gridy++;
        add(takeExamButton, gbc);
        gbc.gridx = 1;
        add(viewResultButton, gbc);
        gbc.gridx = 0; gbc.gridy++;
        add(backButton, gbc);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
} 