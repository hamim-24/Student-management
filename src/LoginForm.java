import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.HashMap;
import java.util.Map;

public class LoginForm extends JFrame {

    Map<String, Account> accounts = new HashMap<>();

    JTextField idField;
    JPasswordField passwordField;
    JButton loginButton, studentLoginButton, registerButton;

    private final String ID_PLACE_HOLDER = "IDENTITY  NUMBER";
    private final String PASSWORD_PLACE_HOLDER = "PASSWORD";

    public LoginForm() {

        this.accounts = SignInFrame.getAccounts();

        createComponents();
        layoutComponents();
        setPlaceHolders();
        setupEvenHandelars();

        setTitle("Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);

        pack();

        setVisible(true);
        loginButton.requestFocusInWindow();
    }

    private void createComponents() {

        idField = new JTextField(20);
        passwordField = new JPasswordField(20);

        idField.setFocusable(true);
        passwordField.setFocusable(true);

        loginButton = new JButton("Login");
        loginButton.setBackground(new Color(70, 130, 180));
        loginButton.setFocusPainted(false);

        studentLoginButton = new JButton("Student Login");
        studentLoginButton.setBackground(new Color(70, 130, 180));
        studentLoginButton.setFocusPainted(false);

        registerButton = new JButton("Register");
        registerButton.setBackground(new Color(70, 130, 180));
        registerButton.setFocusPainted(false);

    }

    private void layoutComponents() {

        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        JLabel titleLabel = new JLabel("Log In");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        c.insets = new Insets(20, 0, 20, 0);
        add(titleLabel, c);

        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 2;
        c.insets = new Insets(5, 20, 5, 20);
        c.fill = GridBagConstraints.HORIZONTAL;
        add(idField, c);

        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 2;
        c.insets = new Insets(5, 20, 5, 20);
        add(passwordField, c);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(loginButton);
        buttonPanel.add(studentLoginButton);
        buttonPanel.add(registerButton);
        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 2;
        c.insets = new Insets(15, 15, 15, 15);
        add(buttonPanel, c);
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
                if (String.valueOf(passwordField.getPassword()).length() == 0) {
                    passwordField.setText(PASSWORD_PLACE_HOLDER);
                    passwordField.setForeground(Color.GRAY);
                    passwordField.setEchoChar((char) 0);
                }
            }
        });
    }

    private void setupEvenHandelars() {

        String id = idField.getText();
        String password = String.valueOf(passwordField.getPassword());

        loginButton.addActionListener(e -> {

            Account teacherAccount = accounts.get(id);
            if (validForm()) {
                if (teacherAccount == null) {
                    JOptionPane.showMessageDialog(this, "Invalid ID\n\n- Register your ID", "Error", JOptionPane.ERROR_MESSAGE);
                    getRootPane().setDefaultButton(registerButton);
                } else if (!teacherAccount.getPassword().equals(password)) {
                    JOptionPane.showMessageDialog(this, "Passwords do not match", "Error", JOptionPane.ERROR_MESSAGE);
                } else {

                }
            }
        });
        getRootPane().setDefaultButton(loginButton);

        studentLoginButton.addActionListener(e -> {

            Account studentAccount = accounts.get(id);
            if (validForm()) {
                if (studentAccount == null) {
                    JOptionPane.showMessageDialog(this, "Invalid ID\n\n- Register your ID", "Error", JOptionPane.ERROR_MESSAGE);
                    getRootPane().setDefaultButton(registerButton);
                } else if (!studentAccount.getPassword().equals(password)) {
                    JOptionPane.showMessageDialog(this, "Passwords do not match", "Error", JOptionPane.ERROR_MESSAGE);
                } else {


                }
            }
        });

        registerButton.addActionListener(e -> {
            new SignInFrame();
        });
    }



    private boolean validForm() {

        if (idField.getText().equals(ID_PLACE_HOLDER) || passwordField.getText().equals(PASSWORD_PLACE_HOLDER)) {
            JOptionPane.showMessageDialog(this, "Please fill all the fields!", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }


}












