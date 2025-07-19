import javax.swing.*;
import java.awt.*;

public class AdministrationForm extends JFrame {

    JLabel titleLabel;
    JButton studentButton, teacherButton, backButton;
    GridBagConstraints gbc;

    public AdministrationForm() {
        titleLabel = new JLabel("Administration Dashboard");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));

        studentButton = new JButton("Student List");
        studentButton.setFocusPainted(false);
        teacherButton = new JButton("Teacher List");
        teacherButton.setFocusPainted(false);
        backButton = new JButton("Back");
        backButton.setFocusPainted(false);

        setTitle("Administration");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(25, 30, 10, 30);
        add(titleLabel, gbc);

        gbc.gridy = 1;
        gbc.gridx = 0;
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.add(studentButton);
        buttonPanel.add(teacherButton);
        buttonPanel.add(backButton);

        add(buttonPanel, gbc);

        pack();

        studentButton.addActionListener(e -> {
            dispose();
            new StudentList();
        });

        teacherButton.addActionListener(e -> {
            dispose();
            new TeacherList(); // Now properly implemented
        });

        backButton.addActionListener(e -> {
            dispose();
            new LoginForm();
        });

        setVisible(true);
    }
}