import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class AdministrationForm extends JFrame {

    private JPanel mainPanel;

    public AdministrationForm() {

        setTitle("Administration Panel");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(245, 247, 250));

        // Header
        JLabel headerLabel = new JLabel("Administration Dashboard", SwingConstants.CENTER);
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
        JButton studentButton = new JButton("Student List");
        utils.styleButton(studentButton);
        studentButton.setToolTipText("Show Student List");
        mainPanel.add(studentButton, gbc);

        // Search/View Exam Button
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        JButton teacherButton = new JButton("Teacher List");
        utils.styleButton(teacherButton);
        teacherButton.setToolTipText("Show Teacher List");
        mainPanel.add(teacherButton, gbc);

        // Search area
        gbc.gridy++;
        gbc.gridwidth = 1;
        JLabel searchLabel = new JLabel("Account ID:");
        searchLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        mainPanel.add(searchLabel, gbc);

        gbc.gridx = 1;
        JTextField searchField = new JTextField(15);
        searchField.setFont(new Font("Arial", Font.PLAIN, 15));
        mainPanel.add(searchField, gbc);


        // Search account Button
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        JButton searchButton = new JButton("Search Account");
        utils.styleButton(searchButton);
        searchButton.setToolTipText("Show Search Account");
        mainPanel.add(searchButton, gbc);

        // Back Button
        gbc.gridy++;
        JButton backButton = new JButton("Back to Main Menu");
        utils.styleButton(backButton);
        backButton.setToolTipText("Return to main menu");
        mainPanel.add(backButton, gbc);

        add(mainPanel, BorderLayout.CENTER);

        // Button actions
        studentButton.addActionListener(e -> {
            this.dispose();
            new StudentList();
        });
        teacherButton.addActionListener(e -> {
            this.dispose();
            new TeacherList();
        });
        getRootPane().setDefaultButton(searchButton);
        searchButton.addActionListener(e -> {
            String searchID = searchField.getText().trim();
            Account account = Main.getAccounts().get(searchID);
            if (account == null) {
                JOptionPane.showMessageDialog(this, "No Account Found!", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                StringBuilder qs = new StringBuilder();

                qs.append(account);

                JTextArea textArea = new JTextArea(qs.toString());
                textArea.setEditable(false);
                textArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
                textArea.setBackground(new Color(245, 247, 250));
                JScrollPane scrollPane = new JScrollPane(textArea);
                scrollPane.setPreferredSize(new Dimension(400, 250));
                JOptionPane.showMessageDialog(this, scrollPane, "Account Details", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        backButton.addActionListener(e -> {
            this.dispose();
            new LoginForm();
        });

        pack();
        setMinimumSize(new Dimension(500, 600));
        setLocationRelativeTo(null);
        setVisible(true);
    }

}