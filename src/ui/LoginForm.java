package ui;

import launcher.Main;
import model.*;
import util.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.HashMap;
import java.util.Map;

public class LoginForm extends JFrame {

    Map<String, Account> accounts;
    Account admin = new Account("11111", "111111", "hamimlohani@gmail.com", "Hamim", "Lohani", "Male", "24-06-2005", "", "");
    Map<String, Account> adminAccounts = new HashMap<>();

    JTextField idField;
    JPasswordField passwordField;
    JButton loginButton, studentLoginButton, registerButton, notificationButton;

    private final String ID_PLACE_HOLDER = "ID/Registration Number";
    private final String PASSWORD_PLACE_HOLDER = "Password";

    public LoginForm() {
        try {
            this.accounts = Main.getAccounts();
            if (this.accounts == null) {
                this.accounts = new HashMap<>();
            }
            adminAccounts.put(admin.getID(), admin);
            createComponents();
            layoutComponents();
            setPlaceHolders();
            addEvenListeners();
            setupFrame();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error initializing login form: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            System.err.println("Exception in LoginForm constructor: " + ex.getMessage());
        }
    }

    private void setupFrame() {
        setTitle("Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setResizable(false);
        setLocationRelativeTo(null);
        pack();
        setVisible(true);
        if (loginButton != null) {
            loginButton.requestFocusInWindow();
        }
    }

    private void createComponents() {
        idField = new JTextField(20);
        idField.setFont(new Font("Arial", Font.PLAIN, 16));
        passwordField = new JPasswordField(20);
        passwordField.setFont(new Font("Arial", Font.PLAIN, 16));

        idField.setFocusable(true);
        passwordField.setFocusable(true);

        loginButton = new JButton("Administration");
        loginButton.setFocusPainted(false);

        studentLoginButton = new JButton("Student and Teacher");
        studentLoginButton.setFocusPainted(false);

        registerButton = new JButton("Register");
        registerButton.setFocusPainted(false);

        notificationButton = new JButton("Notification");
        notificationButton.setFocusPainted(false);
    }

    private void layoutComponents() {
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        // add title label
        JLabel titleLabel = new JLabel("Log In");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        c.insets = new Insets(20, 0, 20, 0);
        add(titleLabel, c);

        // add id field
        c.gridx = 0;
        c.gridy++;
        c.gridwidth = 2;
        c.insets = new Insets(5, 20, 5, 20);
        c.fill = GridBagConstraints.HORIZONTAL;
        add(idField, c);

        // add password field
        c.gridx = 0;
        c.gridy++;
        c.gridwidth = 2;
        c.insets = new Insets(5, 20, 5, 20);
        add(passwordField, c);

        // add buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(loginButton);
        buttonPanel.add(studentLoginButton);
        buttonPanel.add(registerButton);
        c.gridx = 0;
        c.gridy++;
        c.gridwidth = 2;
        c.insets = new Insets(5, 20, 5, 20);
        add(buttonPanel, c);

        c.gridy++;
        c.insets = new Insets(5, 20, 25, 20);
        add(notificationButton, c);
    }

    private void setPlaceHolders() {
        idField.setText(ID_PLACE_HOLDER);
        idField.setHorizontalAlignment(JTextField.CENTER);
        idField.setForeground(Color.GRAY);
        passwordField.setText(PASSWORD_PLACE_HOLDER);
        passwordField.setHorizontalAlignment(JTextField.CENTER);
        passwordField.setForeground(Color.GRAY);
        passwordField.setEchoChar((char) 0);

        idField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (idField.getText().equals(ID_PLACE_HOLDER)) {
                    idField.setText("");
                    idField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (idField.getText().isEmpty()) {
                    idField.setText(ID_PLACE_HOLDER);
                    idField.setForeground(Color.GRAY);
                }
            }
        });

        passwordField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (String.valueOf(passwordField.getPassword()).equals(PASSWORD_PLACE_HOLDER)) {
                    passwordField.setText("");
                    passwordField.setForeground(Color.BLACK);
                    passwordField.setEchoChar('*');
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (String.valueOf(passwordField.getPassword()).isEmpty()) {
                    passwordField.setText(PASSWORD_PLACE_HOLDER);
                    passwordField.setForeground(Color.GRAY);
                    passwordField.setEchoChar((char) 0);
                }
            }
        });
    }

    private void addEvenListeners() {
        loginButton.addActionListener(e -> {
            try {
                String id = idField.getText().trim();
                String password = String.valueOf(passwordField.getPassword()).trim();

                if (validForm()) {
                    Account account = adminAccounts.get(id);
                    if (account == null) {
                        JOptionPane.showMessageDialog(this, "Invalid ID\n\n- Register your ID", "Error", JOptionPane.ERROR_MESSAGE);
                        getRootPane().setDefaultButton(registerButton);
                        return;
                    }

                    if (!account.getPassword().equals(password)) {
                        JOptionPane.showMessageDialog(this, "Passwords do not match", "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(this, "Login Successful!!", "Success", JOptionPane.INFORMATION_MESSAGE);
                        dispose();
                        new AdministrationForm();
                    }
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error during administration login: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                System.err.println("Exception in administration login: " + ex.getMessage());
            }
        });
        getRootPane().setDefaultButton(loginButton);

        studentLoginButton.addActionListener(e -> {
            try {
                String id = idField.getText().trim();
                String password = String.valueOf(passwordField.getPassword()).trim();

                if (validForm()) {
                    Account account = accounts.get(id);
                    if (account == null) {
                        JOptionPane.showMessageDialog(this, "Invalid ID\n\n- Register your ID", "Error", JOptionPane.ERROR_MESSAGE);
                        getRootPane().setDefaultButton(registerButton);
                        return;
                    } else if (!account.getPassword().equals(password)) {
                        JOptionPane.showMessageDialog(this, "Passwords do not match", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    } else {
                        JOptionPane.showMessageDialog(this, "Login Successful!!\n\n", "Success", JOptionPane.INFORMATION_MESSAGE);
                        dispose();

                        if (account.getStatus().equals("Student")) {
                            new StudentPanel((StudentAccount) account);
                        } else if (account.getStatus().equals("Teacher")) {
                            new TeacherPanel(account);
                        } else {
                            JOptionPane.showMessageDialog(this, "Invalid account status", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            } catch (ClassCastException cce) {
                JOptionPane.showMessageDialog(this, "Invalid account type for student login", "Error", JOptionPane.ERROR_MESSAGE);
                System.err.println("ClassCastException in student login: " + cce.getMessage());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error during student/teacher login: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                System.err.println("Exception in student/teacher login: " + ex.getMessage());
            }
        });

        registerButton.addActionListener(e -> {
            try {
                dispose();
                new SignInFrame();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error opening registration form: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                System.err.println("Exception in register button: " + ex.getMessage());
            }
        });

        notificationButton.addActionListener(e -> {
            try {
                StringBuilder notification = new StringBuilder();
                for (Notification n : Main.getNotifications()) {
                    if (n != null) {
                        notification.append("\n#").append(n.getDate()).append(" - ").append(n.getNote());
                    }
                }
                JOptionPane.showMessageDialog(this, Utils.ScrollPanel(notification), "Notification", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "No notification available" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                System.err.println("Exception in notification button: " + ex.getMessage());
            }
        });
    }

    private boolean validForm() {
        try {
            if (idField.getText().equals(ID_PLACE_HOLDER) || String.valueOf(passwordField.getPassword()).equals(PASSWORD_PLACE_HOLDER)) {
                JOptionPane.showMessageDialog(this, "Please fill all the fields!", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
            return true;
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error validating form: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
}












