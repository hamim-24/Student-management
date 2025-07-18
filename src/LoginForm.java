import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.HashMap;
import java.util.Map;

public class LoginForm extends JFrame {

    Map<String, Account> accounts = new HashMap<>();
    Account admin = new Account("11111", "111111", "hamimlohani@gmail.com", "Hamim", "Lohani", "Male", "24-06-2005");
    Map<String, Account> adminAccounts = new HashMap<>();

    JTextField idField;
    JPasswordField passwordField;
    JButton loginButton, studentLoginButton, registerButton;

    private final String ID_PLACE_HOLDER = "ID/Registration Number";
    private final String PASSWORD_PLACE_HOLDER = "Password";

    public LoginForm() {

        this.accounts = SignInFrame.getAccounts();
        adminAccounts.put(admin.getID(), admin);

        createComponents();
        layoutComponents();
        setPlaceHolders();
        setupEvenHandelars();

        setTitle("Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setSize(800, 600);
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

        loginButton = new JButton("Administration");
        loginButton.setBackground(new Color(70, 130, 180));
        loginButton.setFocusPainted(false);

        studentLoginButton = new JButton("Student and Teacher");
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

        loginButton.addActionListener(e -> {
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
                    JOptionPane.showMessageDialog(this, "Login Successful!!\n\n" + account, "Success", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
        getRootPane().setDefaultButton(loginButton);

        studentLoginButton.addActionListener(e -> {
            String id = idField.getText().trim();
            String password = String.valueOf(passwordField.getPassword()).trim();

            if (validForm()) {
                Account account = accounts.get(id);
                if (account == null) {
                    JOptionPane.showMessageDialog(this, "Invalid ID\n\n- Register your ID", "Error", JOptionPane.ERROR_MESSAGE);
                    getRootPane().setDefaultButton(registerButton);
                } else if (!account.getPassword().equals(password)) {
                    JOptionPane.showMessageDialog(this, "Passwords do not match", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Login Successful!!\n\n" + account, "Success", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        registerButton.addActionListener(e -> {
            dispose();
            new SignInFrame();
        });
    }



    private boolean validForm() {

        if (idField.getText().equals(ID_PLACE_HOLDER) || String.valueOf(passwordField.getPassword()).equals(PASSWORD_PLACE_HOLDER)) {
            JOptionPane.showMessageDialog(this, "Please fill all the fields!", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }


}












