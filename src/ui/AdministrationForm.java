package ui;

import launcher.Main;
import model.*;

import util.*;
import javax.swing.*;
import java.awt.*;
import java.util.Map;

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
        JButton promotionButton = new JButton("Promotion");
        utils.styleButton(promotionButton);
        promotionButton.setToolTipText("Set Promotion");
        mainPanel.add(promotionButton, gbc);
        promotionButton.addActionListener(e -> handlePromotion());

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

    private void handlePromotion() {
        String department = (String) JOptionPane.showInputDialog(
                this,
                "Select Department:",
                "Department",
                JOptionPane.QUESTION_MESSAGE,
                null,
                utils.DEPARTMENTS,
                utils.DEPARTMENTS[0]
        );
        if (department == null || department.equals("Select")) return;
        String year = (String) JOptionPane.showInputDialog(
                this,
                "Select Year:",
                "Year",
                JOptionPane.QUESTION_MESSAGE,
                null,
                utils.YEARS,
                utils.YEARS[0]
        );
        if (year == null || year.equals("Select")) {
            JOptionPane.showMessageDialog(this, "Please Select Year", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int res = JOptionPane.showConfirmDialog(this, "Are you sure to promoted student?\nDepartment: " + department + "\nYear: " + year, "Confirm", JOptionPane.YES_NO_OPTION);
        if (res == JOptionPane.YES_OPTION) {
            Map<String, Account> accounts = Main.getAccounts();
            for (Account s : accounts.values()) {
                if (s instanceof StudentAccount studentAccount) {
                    if (studentAccount.getYear().equals(year) && studentAccount.getDepartment().equals(department)) {
                        if (studentAccount.getCg() >= 2) {
                            for (int i = 1; i < 5; i++) {
                                if (studentAccount.getYear().equals(year)) {
                                    studentAccount.setResultInfo(utils.promotion, "Congratulation for your brilliant success now you are " + utils.YEARS[i + 1] + " student");
                                    studentAccount.setYear(utils.YEARS[i + 1]);
                                }
                            }
                        } else {
                            studentAccount.setResultInfo(utils.promotion, "Sorry, You didn't promoted");
                        }
                    }
                }
            }
            Notification notification = new Notification("Students have been promoted Department: " + department + " year: " + year + " Search: '" + utils.promotion + "' for result");
            Main.getNotifications().add(notification);

            JOptionPane.showMessageDialog(this, "Promotion Complete", "Promotion Complete", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Student have not promoted", "Promotion Complete", JOptionPane.ERROR_MESSAGE);
        }
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