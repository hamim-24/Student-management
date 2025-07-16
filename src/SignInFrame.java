import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class SignInFrame extends JFrame {
    private static Map<String, Account> accounts = new HashMap<>();

    private Account account;
    private JLabel PasswordStatusLabel, mainTitleLabel, titleLabel, accountLabel, firstNameLabel, lastNameLabel, idLabel, idStatusLabel,
            emailLabel, dobLabel, genderLabel, passwordLabel, confirmPasswordLabel, photoLabel, filePathLabel, statusLabel;
    private JTextField firstNameTextField, lastNameTextField, emailTextField, idTextField;
    private JPasswordField passwordTextField, confirmPasswordTextField;
    private JButton createButton, resetButton, choosePhotoButton, loginButton;
    private JRadioButton maleRadioButton, femaleRadioButton;
    private ButtonGroup genderGroup;
    private JComboBox<String> dayCombo, monthCombo, yearCombo, statusCombo;
    String filePath;

    final String DEFAULT_FILE_PATH = "No Path Selected";

    private static final String[] months = {
            "January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December"
    };

    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Za-z0-9+_.-]+@([A-Za-z0-9-]+\\.)+[A-Za-z]{2,}$"
    );

    public SignInFrame() {
        initializeFrame();
        createComponents();
        layoutComponents();
        addEventListeners();

        setVisible(true);
    }

    private void initializeFrame() {
        setTitle("Sign In");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
    }

    private void createComponents() {
        PasswordStatusLabel = new JLabel();
        PasswordStatusLabel.setFont(new Font(Font.MONOSPACED, Font.BOLD, 10));
        PasswordStatusLabel.setForeground(Color.RED);

        idStatusLabel = new JLabel();
        idStatusLabel.setFont(new Font(Font.MONOSPACED, Font.BOLD, 10));
        idStatusLabel.setForeground(Color.RED);

        // Labels
        firstNameLabel = new JLabel("First Name:");
        lastNameLabel = new JLabel("Last Name:");
        emailLabel = new JLabel("Email:");
        dobLabel = new JLabel("DOB:");
        genderLabel = new JLabel("Gender:");
        passwordLabel = new JLabel("Password:");
        confirmPasswordLabel = new JLabel("Confirm Password:");
        idLabel = new JLabel("ID:");
        photoLabel = new JLabel("Photo:");
        statusLabel = new JLabel("Status:");

        // Radio buttons
        maleRadioButton = new JRadioButton("Male");
        femaleRadioButton = new JRadioButton("Female");
        genderGroup = new ButtonGroup();
        genderGroup.add(maleRadioButton);
        genderGroup.add(femaleRadioButton);

        // File path label
        filePathLabel = new JLabel(DEFAULT_FILE_PATH);
        filePathLabel.setForeground(Color.GRAY);
        filePathLabel.setPreferredSize(new Dimension(200, 30));

        // Text fields
        firstNameTextField = new JTextField(20);
        lastNameTextField = new JTextField(20);
        emailTextField = new JTextField(20);
        passwordTextField = new JPasswordField(20);
        confirmPasswordTextField = new JPasswordField(20);
        idTextField = new JTextField(20);

        // Buttons
        createButton = new JButton("Create Account");
        createButton.setFocusable(false);
        choosePhotoButton = new JButton("Choose");
        choosePhotoButton.setFocusable(false);
        resetButton = new JButton("Reset");
        resetButton.setFocusable(false);
        loginButton = new JButton("Login");
        loginButton.setFocusable(false);
        loginButton.setFocusable(false);

        // Combo boxes
        dayCombo = new JComboBox<>();
        for (int i = 0; i < 31; i++) {
            dayCombo.addItem(String.format("%02d", i + 1));
        }
        monthCombo = new JComboBox<>(months);
        yearCombo = new JComboBox<>();
        for (int i = 0; i < 50; i++) {
            yearCombo.addItem(String.valueOf(LocalDate.now().getYear() - i));
        }

        statusCombo = new JComboBox<>(new String[]{"Select", "Teacher", "Student"});

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
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(new EmptyBorder(30, 30, 30, 30));
        GridBagConstraints gbc = new GridBagConstraints();

        // Main title
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 0, 20, 0);
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(mainTitleLabel, gbc);

        // Section title
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 10, 0);
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(titleLabel, gbc);

        // Reset constraints for form fields
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Form fields
        addFormField(mainPanel, firstNameLabel, firstNameTextField, gbc, 2);
        addFormField(mainPanel, lastNameLabel, lastNameTextField, gbc, 3);
        addFormField(mainPanel, emailLabel, emailTextField, gbc, 4);
        addFormField(mainPanel, statusLabel, statusCombo, gbc, 5);

        // Gender field
        gbc.gridx = 0;
        gbc.gridy = 6;
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
        gbc.gridy = 7;
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
        gbc.gridy = 8;
        mainPanel.add(photoLabel, gbc);

        JPanel photoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        photoPanel.add(choosePhotoButton);
        photoPanel.add(Box.createHorizontalStrut(10));
        photoPanel.add(filePathLabel);

        gbc.gridx = 1;
        gbc.gridwidth = 2;
        mainPanel.add(photoPanel, gbc);

        gbc.gridwidth = 3;
        gbc.gridx = 0;
        gbc.gridy = 9;
        gbc.insets = new Insets(20, 0, 10, 0);
        mainPanel.add(accountLabel, gbc);
        gbc.insets = new Insets(5, 5, 5, 5);

        // Password fields
        gbc.gridwidth = 1;
        addFormField(mainPanel, idLabel, idTextField, gbc, 10);

        // id status label
        gbc.gridx = 1;
        gbc.gridy = 11;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(-5, 5, 5, 5);
        mainPanel.add(idStatusLabel, gbc);
        gbc.insets = new Insets(5, 5, 5, 5);

        addFormField(mainPanel, passwordLabel, passwordTextField, gbc, 12);
        addFormField(mainPanel, confirmPasswordLabel, confirmPasswordTextField, gbc, 13);

        // Error label
        gbc.gridx = 1;
        gbc.gridy = 14;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(-5, 5, 5, 5);
        mainPanel.add(PasswordStatusLabel, gbc);
        gbc.insets = new Insets(5, 5, 5, 5);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.add(loginButton);
        buttonPanel.add(resetButton);
        buttonPanel.add(createButton);

        createButton.setVisible(false);

        gbc.gridx = 0;
        gbc.gridy = 15;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(20, 0, 0, 0);
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

    public static void addFormField(JPanel panel, JLabel label, JComponent component, GridBagConstraints gbc, int row) {
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
        boolean pwdMatck = false;
        StringBuilder errorMessage = new StringBuilder("Please correct the following errors:\n");

        // Clear previous error styling
        PasswordStatusLabel.setText("");
        confirmPasswordTextField.setBackground(Color.WHITE);
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
        } else if (!EMAIL_PATTERN.matcher(getEmail()).matches()) {
            errorMessage.append("- Email format is invalid\n");
            emailTextField.setBackground(new Color(255, 230, 230));
            isValid = false;
        }

        if (getStatus().equals("Select")) {
            errorMessage.append("- Status is required\n");
            isValid = false;
        }

        if (getId().isEmpty()) {
            errorMessage.append("- ID is required\n");
            isValid = false;
        }

        if (passwordTextField.getPassword().length == 0) {
            errorMessage.append("- Password is required\n");
            isValid = false;
        }

        if (passwordTextField.getPassword().length > 5 || confirmPasswordTextField.getPassword().length > 5) {
            String password = new String(passwordTextField.getPassword());
            String confirmPassword = new String(confirmPasswordTextField.getPassword());
            if (password.equals(confirmPassword)) {
                pwdMatck = true;
            } else {
                confirmPasswordTextField.setBackground(new Color(255, 230, 230));
                PasswordStatusLabel.setText("Passwords do't match");
                pwdMatck = false;
                isValid = false;
                errorMessage.append("- Passwords don't match\n");
            }
        } else {
            passwordTextField.setBackground(new Color(255, 230, 230));
            PasswordStatusLabel.setText("Password must be at least 6 characters");
            errorMessage.append("- Password must be at least 6 characters\n");
            isValid = false;
        }

        if (!maleRadioButton.isSelected() && !femaleRadioButton.isSelected()) {
            errorMessage.append("- Gender is required\n");
            isValid = false;
        }

        if (isValid && pwdMatck) {
            confirmPasswordTextField.setBackground(Color.WHITE);
            passwordTextField.setBackground(Color.WHITE);

            JOptionPane.showMessageDialog(this,
                    "Account created successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);

            if (getStatus().equals("Teacher")) {
                account = new TeacherAccount(getId(), getPassword(), getEmail(), getFirstName(), getLastName(), getGender(), getDob());
            } else if (getStatus().equals("Student")) {
                account = new StudentAccount(getId(), getPassword(), getEmail(), getFirstName(), getLastName(), getGender(), getDob());
            }
            accounts.put(getId(), account);

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
                if (!email.isEmpty() && !EMAIL_PATTERN.matcher(email).matches()) {
                    emailTextField.setBackground(new Color(255, 230, 230));
                } else {
                    emailTextField.setBackground(Color.WHITE);
                }
            } else if (source == passwordTextField) {
                String password = new String(passwordTextField.getPassword());
                String confirmPassword = new String(confirmPasswordTextField.getPassword());

                if (password.length() <= 5) {
                    passwordTextField.setBackground(new Color(255, 230, 230));
                    PasswordStatusLabel.setText("Password must be at least 6 characters");
                } else if (!confirmPassword.isEmpty() && !password.equals(confirmPassword)) {
                    confirmPasswordTextField.setBackground(new Color(255, 230, 230));
                    PasswordStatusLabel.setText("Passwords do not match");
                } else {
                    confirmPasswordTextField.setBackground(Color.WHITE);
                    passwordTextField.setBackground(Color.WHITE);
                    PasswordStatusLabel.setText("");
                    createButton.setVisible(true);
                    getRootPane().setDefaultButton(createButton);
                }
            } else if (source == idTextField) {
                if (checkId(getId())) {
                    idTextField.setBackground(new Color(255, 230, 230));
                    idStatusLabel.setText("ID exist, Change ID");
                } else {
                    idTextField.setBackground(Color.WHITE);
                    idStatusLabel.setText("");
                }
            }
        }
    }

    private void resetFrame() {
        PasswordStatusLabel.setText("");
        firstNameTextField.setText("");
        lastNameTextField.setText("");
        emailTextField.setText("");
        passwordTextField.setText("");
        confirmPasswordTextField.setText("");

        // Reset background colors
        emailTextField.setBackground(Color.WHITE);
        confirmPasswordTextField.setBackground(Color.WHITE);

        genderGroup.clearSelection();
        dayCombo.setSelectedIndex(0);
        monthCombo.setSelectedIndex(0);
        yearCombo.setSelectedIndex(0);
        filePathLabel.setText(DEFAULT_FILE_PATH);
        filePathLabel.setForeground(Color.GRAY);
        filePath = null;
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

    public static Map<String, Account> getAccounts() {
        return accounts;
    }
}
