package ui;

import launcher.Main;
import model.*;
import util.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class SignInFrame extends JFrame {

    private Map<String, Account> accounts = new HashMap<>();

    private Account account;
    private JLabel PasswordStatusLabel, mainTitleLabel, titleLabel, accountLabel, firstNameLabel, lastNameLabel, idLabel, idStatusLabel,
            emailLabel, dobLabel, genderLabel, passwordLabel, confirmPasswordLabel, photoLabel, filePathLabel, statusLabel, yearLabel, rollLabel, departmentLabel, sessionLabel;
    private JTextField firstNameTextField, lastNameTextField, emailTextField, idTextField, rollTextField;
    private JPasswordField passwordTextField, confirmPasswordTextField;
    private JButton createButton, resetButton, choosePhotoButton, loginButton;
    private JRadioButton maleRadioButton, femaleRadioButton;
    private ButtonGroup genderGroup;
    private JComboBox<String> dayCombo, monthCombo, yearCombo, statusCombo, acYearCombo, departmentCombo, sessionCombo;
    String filePath;

    JPanel mainPanel;
    GridBagConstraints gbc;

    public boolean studentFieldsVisible = false;

    private int lineNumber = -1;
    private int line1, line2;

    public SignInFrame() {
        this.accounts = Main.getAccounts();
        initializeFrame();
        createComponents();
        layoutComponents();
        addEventListeners();
        setVisible(true);
        getRootPane().setDefaultButton(createButton);
    }

    private void initializeFrame() {

        setTitle("Sign In");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setSize(800, 600);
        setLocationRelativeTo(null);
    }

    private void createComponents() {

        sessionLabel = new JLabel("Session:");
        sessionCombo = new JComboBox<>(utils.session());

        // Status labels with proper initialization
        PasswordStatusLabel = new JLabel(" "); // Space to maintain height
        PasswordStatusLabel.setFont(new Font(Font.MONOSPACED, Font.BOLD, 8));
        PasswordStatusLabel.setForeground(Color.RED);
        PasswordStatusLabel.setPreferredSize(new Dimension(300, 7));

        idStatusLabel = new JLabel(" "); // Space to maintain height
        idStatusLabel.setFont(new Font(Font.MONOSPACED, Font.BOLD, 8));
        idStatusLabel.setForeground(Color.RED);
        idStatusLabel.setPreferredSize(new Dimension(300, 7));

        // Labels
        firstNameLabel = new JLabel("First Name:");
        lastNameLabel = new JLabel("Last Name:");
        emailLabel = new JLabel("Email:");
        dobLabel = new JLabel("Date of Birth:");
        genderLabel = new JLabel("Gender:");
        passwordLabel = new JLabel("Password:");
        confirmPasswordLabel = new JLabel("Confirm Password:");
        idLabel = new JLabel("ID/Registration Number:");
        photoLabel = new JLabel("Photo(optional):");
        statusLabel = new JLabel("Status:");
        yearLabel = new JLabel("Year:");
        rollLabel = new JLabel("Roll:");
        departmentLabel = new JLabel("Department:");

        // Radio buttons
        maleRadioButton = new JRadioButton("Male");
        femaleRadioButton = new JRadioButton("Female");
        genderGroup = new ButtonGroup();
        genderGroup.add(maleRadioButton);
        genderGroup.add(femaleRadioButton);

        // File path label
        filePathLabel = new JLabel(utils.DEFAULT_FILE_PATH);
        filePathLabel.setForeground(Color.GRAY);
        filePathLabel.setPreferredSize(new Dimension(200, 30));

        // Text fields
        firstNameTextField = new JTextField(20);
        lastNameTextField = new JTextField(20);
        emailTextField = new JTextField(20);
        passwordTextField = new JPasswordField(20);
        confirmPasswordTextField = new JPasswordField(20);
        idTextField = new JTextField(20);
        rollTextField = new JTextField(20);

        // Buttons
        createButton = new JButton("Create Account");
        createButton.setFocusable(false);
        choosePhotoButton = new JButton("Choose");
        choosePhotoButton.setFocusable(false);
        resetButton = new JButton("Reset");
        resetButton.setFocusable(false);
        loginButton = new JButton("Login");
        loginButton.setFocusable(false);

        // Combo boxes
        dayCombo = new JComboBox<>();
        for (int i = 0; i < 31; i++) {
            dayCombo.addItem(String.format("%02d", i + 1));
        }
        monthCombo = new JComboBox<>(utils.MONTHS);
        yearCombo = new JComboBox<>();
        for (int i = 0; i < 50; i++) {
            yearCombo.addItem(String.valueOf(LocalDate.now().getYear() - i));
        }
        statusCombo = new JComboBox<>(new String[]{"Select", "Teacher", "Student"});
        acYearCombo = new JComboBox<>(utils.YEARS);
        departmentCombo = new JComboBox<>(utils.DEPARTMENTS);

        // Title labels
        titleLabel = new JLabel("PERSONAL INFORMATION:");
        titleLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
        titleLabel.setForeground(new Color(70, 130, 180));

        accountLabel = new JLabel("ACCOUNT INFORMATION:");
        accountLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
        accountLabel.setForeground(new Color(70, 130, 180));

        mainTitleLabel = new JLabel("Create Account");
        mainTitleLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
        mainTitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
    }

    private void layoutComponents() {

        setLayout(new BorderLayout());
        mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(new EmptyBorder(30, 30, 30, 30));
        gbc = new GridBagConstraints();

        // Main title
        gbc.gridx = 0;
        gbc.gridy = nextLine();
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 0, 20, 0);
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(mainTitleLabel, gbc);

        // Section title
        gbc.gridy = nextLine();
        gbc.insets = new Insets(0, 0, 10, 0);
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(titleLabel, gbc);

        // Reset constraints for form fields
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Form fields
        addFormField(mainPanel, firstNameLabel, firstNameTextField, gbc, nextLine());
        addFormField(mainPanel, lastNameLabel, lastNameTextField, gbc, nextLine());
        addFormField(mainPanel, emailLabel, emailTextField, gbc, nextLine());
        addFormField(mainPanel, sessionLabel, sessionCombo, gbc, nextLine());
        addFormField(mainPanel, departmentLabel, departmentCombo, gbc, nextLine());
        addFormField(mainPanel, statusLabel, statusCombo, gbc, nextLine());
        line1 = nextLine(); // for year
        line2 = nextLine(); // for roll

        // Gender field
        gbc.gridx = 0;
        gbc.gridy = nextLine();
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(genderLabel, gbc);

        JPanel genderPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        genderPanel.add(maleRadioButton);
        genderPanel.add(Box.createHorizontalStrut(10));
        genderPanel.add(femaleRadioButton);

        gbc.gridx = 1;
        gbc.gridwidth = 2;
        mainPanel.add(genderPanel, gbc);

        // DOB field
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = nextLine();
        mainPanel.add(dobLabel, gbc);

        JPanel dobPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        dobPanel.add(dayCombo);
        dobPanel.add(Box.createHorizontalStrut(5));
        dobPanel.add(monthCombo);
        dobPanel.add(Box.createHorizontalStrut(5));
        dobPanel.add(yearCombo);

        gbc.gridx = 1;
        gbc.gridwidth = 2;
        mainPanel.add(dobPanel, gbc);

        // Photo field
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = nextLine();
        mainPanel.add(photoLabel, gbc);

        JPanel photoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        photoPanel.add(choosePhotoButton);
        photoPanel.add(Box.createHorizontalStrut(10));
        photoPanel.add(filePathLabel);

        gbc.gridx = 1;
        gbc.gridwidth = 2;
        mainPanel.add(photoPanel, gbc);

        // Account section title
        gbc.gridwidth = 3;
        gbc.gridx = 0;
        gbc.gridy = nextLine();
        gbc.insets = new Insets(20, 0, 10, 0);
        mainPanel.add(accountLabel, gbc);

        // Reset insets for form fields
        gbc.gridwidth = 1;

        // ID field
        addFormField(mainPanel, idLabel, idTextField, gbc, nextLine());

        // ID status label - positioned directly under ID field
        gbc.gridx = 1;
        gbc.gridy = nextLine();
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 5, -5, 5); // No top margin to place directly under
        mainPanel.add(idStatusLabel, gbc);

        // Password fields
        addFormField(mainPanel, passwordLabel, passwordTextField, gbc, nextLine());
        addFormField(mainPanel, confirmPasswordLabel, confirmPasswordTextField, gbc, nextLine());

        // Password status label - positioned directly under confirm password field
        gbc.gridx = 1;
        gbc.gridy = nextLine();
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 5, 0, 5); // No top margin to place directly under
        mainPanel.add(PasswordStatusLabel, gbc);

        // Reset insets for button panel
        gbc.insets = new Insets(20, 0, 0, 0);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.add(loginButton);
        buttonPanel.add(createButton);
        buttonPanel.add(resetButton);

        gbc.gridx = 0;
        gbc.gridy = nextLine();
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(buttonPanel, gbc);

        add(mainPanel, BorderLayout.CENTER);

        // Add action listeners
        choosePhotoButton.addActionListener(new FormActionListener());
        resetButton.addActionListener(new FormActionListener());
        createButton.addActionListener(new FormActionListener());
        loginButton.addActionListener(new FormActionListener());

        pack();
    }

    private void shiftComponentsDown() {
        // When student fields are added at rows 6-7, shift everything else down by 2 rows
        Component[] components = mainPanel.getComponents();
        for (Component comp : components) {
            GridBagConstraints constraints = ((GridBagLayout) mainPanel.getLayout()).getConstraints(comp);
            if (constraints.gridy >= 8) {
                constraints.gridy += 2;
                mainPanel.add(comp, constraints);
            }
        }
    }

    private void shiftComponentsUp() {
        // When student fields are removed, shift everything back up by 2 rows
        Component[] components = mainPanel.getComponents();
        for (Component comp : components) {
            GridBagConstraints constraints = ((GridBagLayout) mainPanel.getLayout()).getConstraints(comp);
            if (constraints.gridy >= 10) {
                constraints.gridy -= 2;
                mainPanel.add(comp, constraints);
            }
        }
    }

    public static void addFormField(JPanel panel, JLabel label, JComponent component, GridBagConstraints gbc, int row) {

        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridy = row;
        gbc.gridx = 0;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(label, gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(component, gbc);
    }

    private void addEventListeners() {

        firstNameTextField.addFocusListener(new ValidationFocusListener());
        lastNameTextField.addFocusListener(new ValidationFocusListener());
        emailTextField.addFocusListener(new ValidationFocusListener());
        idTextField.addFocusListener(new ValidationFocusListener());
        passwordTextField.addFocusListener(new ValidationFocusListener());
        confirmPasswordTextField.addFocusListener(new ValidationFocusListener());

        // Add action listener for status combo box to handle real-time changes
        statusCombo.addActionListener(e -> {

            String selectedStatus = getStatus();

            if (selectedStatus.equals("Student") && !studentFieldsVisible) {
                // Show student fields
                addFormField(mainPanel, yearLabel, acYearCombo, gbc, line1);
                addFormField(mainPanel, rollLabel, rollTextField, gbc, line2);
                studentFieldsVisible = true;

                // Shift other components down
                shiftComponentsDown();

            } else if (!selectedStatus.equals("Student") && studentFieldsVisible) {
                // Hide student fields
                mainPanel.remove(yearLabel);
                mainPanel.remove(acYearCombo);
                mainPanel.remove(rollLabel);
                mainPanel.remove(rollTextField);
                studentFieldsVisible = false;

                // Clear the text fields
                acYearCombo.setSelectedIndex(0);
                rollTextField.setText("");

                // Shift other components up
                shiftComponentsUp();
            }

            // Revalidate and repaint the panel
            mainPanel.revalidate();
            mainPanel.repaint();

            getRootPane().setDefaultButton(createButton);
            pack();
        });
    }

    private class FormActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();

            if ("Choose".equals(command)) {
                JFileChooser fileChooser = new JFileChooser();
                int result = fileChooser.showOpenDialog(SignInFrame.this);
                if (result == JFileChooser.APPROVE_OPTION) {
                    filePath = fileChooser.getSelectedFile().getAbsolutePath();
                    filePathLabel.setText(filePath);
                    filePathLabel.setForeground(Color.BLACK);
                }
            } else if ("Reset".equals(command)) {
                resetFrame();
            } else if ("Create Account".equals(command)) {
                validateAndCreateAccount();
            } else if ("Login".equals(command)) {
                dispose();
                new LoginForm();
            }
        }

    }

    private void validateAndCreateAccount() {

        boolean isValid = true;
        boolean pwdMatch = false;
        StringBuilder errorMessage = new StringBuilder("Please correct the following errors:\n");

        // Clear previous error styling and status messages
        PasswordStatusLabel.setText(" ");
        idStatusLabel.setText(" ");
        confirmPasswordTextField.setBackground(Color.WHITE);
        passwordTextField.setBackground(Color.WHITE);
        idTextField.setBackground(Color.WHITE);
        emailTextField.setBackground(Color.WHITE);

        // Validate fields
        if (getFirstName().isEmpty()) {
            errorMessage.append("- First name is required\n");
            isValid = false;
        }

        if (getLastName().isEmpty()) {
            errorMessage.append("- Last name is required\n");
            isValid = false;
        }

        if (getEmail().isEmpty()) {
            errorMessage.append("- Email is required\n");
            isValid = false;
        } else if (!utils.EMAIL_PATTERN.matcher(getEmail()).matches()) {
            errorMessage.append("- Email format is invalid\n");
            emailTextField.setBackground(new Color(255, 230, 230));
            isValid = false;
        }

        if (getDepartment().isEmpty()) {
            errorMessage.append("- Department is required\n");
            isValid = false;
        }

        if (getStatus().equals("Select")) {
            errorMessage.append("- Status is required\n");
            isValid = false;
        }

        if (getStatus().equals("Student")) {
            if (getYear().equals("Select")) {
                errorMessage.append("- Year is required\n");
                isValid = false;
            }
            if (rollTextField.getText().trim().isEmpty()) {
                errorMessage.append("- Roll is required\n");
                isValid = false;
            } else if (getRoll() == -1) {
                errorMessage.append("- Roll Must be an integer\n");
            }
        }

        if (!maleRadioButton.isSelected() && !femaleRadioButton.isSelected()) {
            errorMessage.append("- Gender is required\n");
            isValid = false;
        }

        if (getId().isEmpty()) {
            errorMessage.append("- ID is required\n");
            isValid = false;
        } else if (checkId(getId())) {
            errorMessage.append("- ID already exists\n");
            idTextField.setBackground(new Color(255, 230, 230));
            idStatusLabel.setText("ID exists, Change ID");
            isValid = false;
        }

        if (passwordTextField.getPassword().length == 0) {
            errorMessage.append("- Password is required\n");
            isValid = false;
        } else if (passwordTextField.getPassword().length > 5 && confirmPasswordTextField.getPassword().length > 5) {
            String password = new String(passwordTextField.getPassword());
            String confirmPassword = new String(confirmPasswordTextField.getPassword());
            if (password.equals(confirmPassword)) {
                pwdMatch = true;
            } else {
                confirmPasswordTextField.setBackground(new Color(255, 230, 230));
                PasswordStatusLabel.setText("Passwords don't match");
                pwdMatch = false;
                isValid = false;
                errorMessage.append("- Passwords don't match\n");
            }
        } else {
            passwordTextField.setBackground(new Color(255, 230, 230));
            PasswordStatusLabel.setText("Password must be at least 6 characters");
            errorMessage.append("- Password must be at least 6 characters\n");
            isValid = false;
        }

        if (isValid && pwdMatch) {
            if (getStatus().equals("Teacher")) {
                account = new TeacherAccount(getId(), getPassword(), getEmail(), getFirstName(), getLastName(), getGender(), getDob(), getDepartment(), getStatus());
            } else if (getStatus().equals("Student")) {
                account = new StudentAccount(getId(), getPassword(), getEmail(), getFirstName(), getLastName(), getGender(), getDob(), getYear(), getRoll(), getDepartment(), getStatus(), 0.0, getSession());
            }

            // Add to the main accounts map instead of local map
            accounts.put(getId(), account);

            JOptionPane.showMessageDialog(this,
                    "Account created successfully!\n\n" +
                            account.toString(),
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);

            dispose();
            new LoginForm();

        } else if (!isValid) {
            JOptionPane.showMessageDialog(this,
                    errorMessage.toString(),
                    "Validation Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private class ValidationFocusListener extends java.awt.event.FocusAdapter {
        @Override
        public void focusLost(java.awt.event.FocusEvent e) {
            JComponent source = (JComponent) e.getSource();

            if (source == emailTextField) {
                String email = emailTextField.getText().trim();
                if (!email.isEmpty() && !utils.EMAIL_PATTERN.matcher(email).matches()) {
                    emailTextField.setBackground(new Color(255, 230, 230));
                } else {
                    emailTextField.setBackground(Color.WHITE);
                }
            }

            if (source == passwordTextField || source == confirmPasswordTextField) {
                String password = new String(passwordTextField.getPassword());
                String confirmPassword = new String(confirmPasswordTextField.getPassword());

                if (password.length() <= 5 && !password.isEmpty()) {
                    passwordTextField.setBackground(new Color(255, 230, 230));
                    PasswordStatusLabel.setText("Password must be at least 6 characters");
                } else if (password.length() > 5 && !confirmPassword.isEmpty() && !password.equals(confirmPassword)) {
                    confirmPasswordTextField.setBackground(new Color(255, 230, 230));
                    PasswordStatusLabel.setText("Passwords don't match");
                } else if (password.length() > 5 && confirmPassword.length() > 5 && password.equals(confirmPassword)) {
                    confirmPasswordTextField.setBackground(Color.WHITE);
                    passwordTextField.setBackground(Color.WHITE);
                    PasswordStatusLabel.setText(" ");
                } else {
                    passwordTextField.setBackground(Color.WHITE);
                    confirmPasswordTextField.setBackground(Color.WHITE);
                    PasswordStatusLabel.setText(" ");
                }
            }

            if (source == idTextField) {
                String id = getId();
                if (!id.isEmpty() && checkId(id)) {
                    idTextField.setBackground(new Color(255, 230, 230));
                    idStatusLabel.setText("ID exists, Change ID");
                } else {
                    idTextField.setBackground(Color.WHITE);
                    idStatusLabel.setText(" ");
                }
            }
        }
    }

    private void resetFrame() {
        // Clear status labels
        PasswordStatusLabel.setText(" ");
        idStatusLabel.setText(" ");

        // Clear text fields
        firstNameTextField.setText("");
        lastNameTextField.setText("");
        emailTextField.setText("");
        idTextField.setText("");
        passwordTextField.setText("");
        confirmPasswordTextField.setText("");
        acYearCombo.setSelectedIndex(0);
        rollTextField.setText("");

        // Reset background colors
        emailTextField.setBackground(Color.WHITE);
        idTextField.setBackground(Color.WHITE);
        passwordTextField.setBackground(Color.WHITE);
        confirmPasswordTextField.setBackground(Color.WHITE);

        // Reset other components
        genderGroup.clearSelection();
        dayCombo.setSelectedIndex(0);
        monthCombo.setSelectedIndex(0);
        yearCombo.setSelectedIndex(0);
        statusCombo.setSelectedIndex(0);
        departmentCombo.setSelectedIndex(0);
        filePathLabel.setText(utils.DEFAULT_FILE_PATH);
        filePathLabel.setForeground(Color.GRAY);
        filePath = null;

        // Hide student fields if they were visible
        if (studentFieldsVisible) {
            mainPanel.remove(yearLabel);
            mainPanel.remove(acYearCombo);
            mainPanel.remove(rollLabel);
            mainPanel.remove(rollTextField);
            studentFieldsVisible = false;
            shiftComponentsUp();
            mainPanel.revalidate();
            mainPanel.repaint();
            pack();
        }
    }

    private boolean checkId(String id) {
        return accounts.get(id) != null;
    }

    private String getFirstName() {
        return firstNameTextField.getText().trim();
    }

    private String getLastName() {
        return lastNameTextField.getText().trim();
    }

    private String getEmail() {
        return emailTextField.getText().trim();
    }

    private String getPassword() {
        return String.valueOf(passwordTextField.getPassword()).trim();
    }

    private String getId() {
        return idTextField.getText().trim();
    }

    private String getGender() {
        return maleRadioButton.isSelected() ? "Male" : "Female";
    }

    private String getDob() {
        return dayCombo.getSelectedItem().toString().trim() + "-" + monthCombo.getSelectedItem().toString().trim() + "-" + yearCombo.getSelectedItem().toString().trim();
    }

    private String getStatus() {
        return statusCombo.getSelectedItem().toString().trim();
    }

    private String getYear() {
        return acYearCombo.getSelectedItem().toString().trim();
    }

    private String getDepartment() {
        return departmentCombo.getSelectedItem().toString().trim();
    }

    private int getRoll() {
        try {
            return Integer.parseInt(rollTextField.getText().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private String getSession() {
        return sessionCombo.getSelectedItem().toString().trim();
    }
    public int nextLine() {
        return ++lineNumber;
    }

}