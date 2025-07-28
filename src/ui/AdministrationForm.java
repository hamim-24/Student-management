package ui;

import launcher.Main;
import model.*;

import util.*;
import javax.swing.*;
import java.awt.*;

public class AdministrationForm extends JFrame {

    private JPanel mainPanel;

    public AdministrationForm() {

        setTitle("Administration Panel");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(245, 247, 250));
        add(createHeader(), BorderLayout.NORTH);

        setupMainPanel();
        add(mainPanel, BorderLayout.CENTER);

        getRootPane().setDefaultButton(getSearchButton());
        pack();

        setMinimumSize(new Dimension(500, 600));
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JLabel createHeader() {

        JLabel headerLabel = new JLabel("Administration Dashboard", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 28));
        headerLabel.setBorder(BorderFactory.createEmptyBorder(24, 0, 24, 0));
        headerLabel.setForeground(new Color(44, 62, 80));
        return headerLabel;
    }

    private void setupMainPanel() {

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
        JButton studentButton = new JButton("Student List");
        utils.styleButton(studentButton);
        studentButton.setToolTipText("Show Student List");
        mainPanel.add(studentButton, gbc);
        studentButton.addActionListener(e -> {
            this.dispose();
            new StudentList();
        });

        gbc.gridy++;
        JButton teacherButton = new JButton("Teacher List");
        utils.styleButton(teacherButton);
        teacherButton.setToolTipText("Show Teacher List");
        mainPanel.add(teacherButton, gbc);
        teacherButton.addActionListener(e -> {
            this.dispose();
            new TeacherList();
        });

        gbc.gridy++;
        JButton resultButton = new JButton("Result List");
        utils.styleButton(resultButton);
        resultButton.setToolTipText("Show Result List");
        mainPanel.add(resultButton, gbc);
        resultButton.addActionListener(e -> {
            dispose();
            new ResultList();
        });

        gbc.gridy++;
        gbc.gridwidth = 1;
        JLabel searchLabel = new JLabel("Account ID:");
        searchLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        mainPanel.add(searchLabel, gbc);
        gbc.gridx = 1;
        JTextField searchField = new JTextField(15);
        searchField.setFont(new Font("Arial", Font.PLAIN, 15));
        mainPanel.add(searchField, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        JButton searchButton = new JButton("Search Account");
        utils.styleButton(searchButton);
        searchButton.setToolTipText("Show Search Account");
        mainPanel.add(searchButton, gbc);
        searchButton.addActionListener(e -> handleSearchAccount(searchField));

        gbc.gridy++;
        JButton announcementButton = new JButton("Announcement");
        utils.styleButton(announcementButton);
        announcementButton.setToolTipText("Set Announcement");
        mainPanel.add(announcementButton, gbc);
        announcementButton.addActionListener(e -> handleAnnouncement());

        gbc.gridy++;
        JButton backButton = new JButton("Back to Main Menu");
        utils.styleButton(backButton);
        backButton.setToolTipText("Return to main menu");
        mainPanel.add(backButton, gbc);
        backButton.addActionListener(e -> {
            this.dispose();
            new LoginForm();
        });
    }

    private void handleSearchAccount(JTextField searchField) {

        String searchID = searchField.getText().trim();
        Account account = Main.getAccounts().get(searchID);
        if (account == null) {
            JOptionPane.showMessageDialog(this, "No Account Found!", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            StringBuilder qs = new StringBuilder();
            qs.append(account);
            JOptionPane.showMessageDialog(this, utils.ScrollPanel(qs), "Account Details", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void handleAnnouncement() {

        String note = JOptionPane.showInputDialog(this, "Add Announcements: ", "Announcements", JOptionPane.QUESTION_MESSAGE).trim();
        if (note.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please Enter Announcements!", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            int res = JOptionPane.showConfirmDialog(this, "Are you Sure to add this:\n'" + note + "'", "Announcement", JOptionPane.YES_NO_OPTION);
            if (res == JOptionPane.YES_OPTION) {
                Notification notification = new Notification(note);
                Main.getNotifications().add(notification);
                JOptionPane.showMessageDialog(this, "Announcement Added!", "Announcement", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    private JButton getSearchButton() {

        for (Component comp : mainPanel.getComponents()) {
            if (comp instanceof JButton && ((JButton) comp).getText().equals("Search Account")) {
                return (JButton) comp;
            }
        }
        return null;
    }
}