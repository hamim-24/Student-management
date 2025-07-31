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
        JButton courseButton = new JButton("Course List");
        utils.styleButton(courseButton);
        courseButton.setToolTipText("Show Course List");
        mainPanel.add(courseButton, gbc);
        courseButton.addActionListener(e -> {
            this.dispose();
            new CourseList();
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
        if (department == null || department.equals("Select")) {
            JOptionPane.showMessageDialog(this, "Department Selected !",  "Warning", JOptionPane.WARNING_MESSAGE);
        }

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
            JOptionPane.showMessageDialog(this, "Please Select Year", "Warning", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String[] sessions = utils.session();
        String session = (String) JOptionPane.showInputDialog(
                this,
                "Select session:",
                "Session",
                JOptionPane.QUESTION_MESSAGE,
                null,
                sessions,
                sessions[0]
        );
        if (session == null || session.equals("Select")) {
            JOptionPane.showMessageDialog(this, "Please select a session", "Warning", JOptionPane.ERROR_MESSAGE);
            return;
        }


        int res = JOptionPane.showConfirmDialog(this, "Are you sure to promote students?\nDepartment: " + department + "\nYear: " + year, "Confirm", JOptionPane.YES_NO_OPTION);
        if (res == JOptionPane.YES_OPTION) {
            Map<String, Account> accounts = Main.getAccounts();
            int promotedCount = 0;
            int notPromotedCount = 0;
            
            for (Account s : accounts.values()) {
                if (s instanceof StudentAccount studentAccount) {
                    if (studentAccount.getYear().equals(year) && studentAccount.getDepartment().equals(department) && studentAccount.getSession().equals(session)) {
                        if (studentAccount.getCg() >= 2.0) {
                            // Find current year index and promote to next year
                            String nextYear = getNextYear(studentAccount.getYear());
                            if (nextYear != null) {
                                studentAccount.setYear(nextYear);
                                studentAccount.setResultInfo(utils.promotion, "Congratulations for your brilliant success! You are now a " + nextYear + " student.");
                                promotedCount++;
                            }
                        } else {
                            studentAccount.setResultInfo(utils.promotion, "Sorry, you were not promoted due to low CGPA (minimum 2.0 required).");
                            notPromotedCount++;
                        }
                    }
                }
            }
            
            Notification notification = new Notification("Promotion completed for Department: " + department + ", Year: " + year +
                    "Session: " + session +
                ". Promoted: " + promotedCount + ", Not promoted: " + notPromotedCount + 
                ". Search: '" + utils.promotion + "' for detailed results.");
            Main.getNotifications().add(notification);

            JOptionPane.showMessageDialog(this, 
                "Promotion Complete!\n\nPromoted: " + promotedCount + " students\nNot promoted: " + notPromotedCount + " students", 
                "Promotion Complete", 
                JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Promotion cancelled by user", "Promotion Cancelled", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private String getNextYear(String currentYear) {
        for (int i = 1; i < utils.YEARS.length - 1; i++) {
            if (utils.YEARS[i].equals(currentYear)) {
                return utils.YEARS[i + 1];
            }
        }
        return null;
    }

    private void handleSearchAccount(JTextField searchField) {
        String searchID = searchField.getText().trim();
        
        if (searchID.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter an Account ID!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        Account account = Main.getAccounts().get(searchID);
        if (account == null) {
            JOptionPane.showMessageDialog(this, "No Account Found with ID: " + searchID, "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            StringBuilder qs = new StringBuilder();
            qs.append("Account Details:\n");
            qs.append("================\n");
            qs.append(account);
            JOptionPane.showMessageDialog(this, utils.ScrollPanel(qs), "Account Details", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void handleAnnouncement() {
        String note = JOptionPane.showInputDialog(this, "Add Announcement:", "Announcements", JOptionPane.QUESTION_MESSAGE);
        
        if (note == null) {
            // User cancelled the dialog
            return;
        }
        
        note = note.trim();
        if (note.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter an announcement!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (note.length() > 500) {
            JOptionPane.showMessageDialog(this, "Announcement is too long! Maximum 500 characters allowed.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        int res = JOptionPane.showConfirmDialog(this, "Are you sure you want to add this announcement?\n\n'" + note + "'", "Confirm Announcement", JOptionPane.YES_NO_OPTION);
        if (res == JOptionPane.YES_OPTION) {
            Notification notification = new Notification(note);
            Main.getNotifications().add(notification);
            JOptionPane.showMessageDialog(this, "Announcement added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
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