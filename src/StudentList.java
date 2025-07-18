import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class StudentList extends JFrame {
    private JTable studentTable;
    private DefaultTableModel tableModel;
    private DefaultTableModel allStudentsModel;
    private JTextField nameField, emailField, ageField, majorField, gpaField;
    private JComboBox<String> classComboBox;
    private JComboBox<String> departmentComboBox;
    private JComboBox<String> classFilterComboBox;
    private JButton updateButton, deleteButton, clearButton, backButton;
    private int selectedRow = -1;

    Map<String, Account> accounts = new HashMap<>();

    public StudentList() {
        this.accounts = Main.getAccounts();

        initializeComponents();
        setupLayout();
        setupEventListeners();
        loadStudentsFromAccounts();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Student Management System");
        setSize(1200, 700); // Increased size to accommodate input fields
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initializeComponents() {
        // Create table with column names
        String[] columnNames = {"Roll", "Name", "ID", "Email", "DOB", "Department", "GPA", "Class"};

        // Create models for all students and filtered view
        allStudentsModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        studentTable = new JTable(tableModel);
        studentTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        studentTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                selectedRow = studentTable.getSelectedRow();
                if (selectedRow != -1) {
                    populateFields();
                }
            }
        });

        // Create input fields
        nameField = new JTextField(20);
        emailField = new JTextField(20);
        ageField = new JTextField(20);
        majorField = new JTextField(20);
        gpaField = new JTextField(20);

        // Create class combo box for adding students
        String[] classes = SignInFrame.classes;
        classComboBox = new JComboBox<>(classes);

        // Create class filter combo box
        String[] classFilters = SignInFrame.classes;
        classFilterComboBox = new JComboBox<>(classFilters);
        classFilterComboBox.addActionListener(e -> filterStudents());

        // Create department filter combo box
        String[] departmentFilters = SignInFrame.departments;
        departmentComboBox = new JComboBox<>(departmentFilters);
        departmentComboBox.addActionListener(e -> filterStudents());

        // Create buttons
        updateButton = new JButton("Update Student");
        deleteButton = new JButton("Delete Student");
        clearButton = new JButton("Clear Fields");
        backButton = new JButton("Back");

        // Set button colors
        updateButton.setBackground(new Color(52, 152, 219));
        updateButton.setForeground(Color.WHITE);
        deleteButton.setBackground(new Color(231, 76, 60));
        deleteButton.setForeground(Color.WHITE);
        clearButton.setBackground(new Color(149, 165, 166));
        clearButton.setForeground(Color.WHITE);
        backButton.setBackground(new Color(231, 76, 60));
        backButton.setForeground(Color.WHITE);

        // Make buttons opaque for color to show
        updateButton.setOpaque(true);
        deleteButton.setOpaque(true);
        clearButton.setOpaque(true);
        backButton.setOpaque(true);

        updateButton.setBorderPainted(false);
        deleteButton.setBorderPainted(false);
        clearButton.setBorderPainted(false);
        backButton.setBorderPainted(false);
    }

    private void setupLayout() {
        setLayout(new BorderLayout());

        // Create main panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Create top panel with filters
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        // Filter panel
        JPanel filterPanel = new JPanel(new GridLayout(2, 2, 10, 5));
        filterPanel.add(new JLabel("Year:"));
        filterPanel.add(classFilterComboBox);
        filterPanel.add(new JLabel("Department:"));
        filterPanel.add(departmentComboBox);
        filterPanel.setBorder(BorderFactory.createTitledBorder("Filters"));

        topPanel.add(filterPanel, BorderLayout.WEST);

        mainPanel.add(topPanel, BorderLayout.NORTH);

        // Create center panel with table and input form
        JPanel centerPanel = new JPanel(new BorderLayout());

        // Table panel
        JScrollPane scrollPane = new JScrollPane(studentTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Student List"));
        scrollPane.setPreferredSize(new Dimension(800, 400));

        centerPanel.add(scrollPane, BorderLayout.CENTER);

        // Fixed: Add input form panel to the right
        JPanel inputPanel = createInputPanel();
        centerPanel.add(inputPanel, BorderLayout.EAST);

        mainPanel.add(centerPanel, BorderLayout.CENTER);

        // Button panel at bottom
        JPanel buttonPanel = createButtonPanel();
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    // Fixed: Create input panel that was missing
    private JPanel createInputPanel() {
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBorder(BorderFactory.createTitledBorder("Edit Student Information"));
        inputPanel.setPreferredSize(new Dimension(320, 450));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Personal Information Section
        JLabel personalLabel = new JLabel("Personal Information");
        personalLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
        personalLabel.setForeground(new Color(70, 130, 180));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        inputPanel.add(personalLabel, gbc);

        // Name field
        gbc.gridwidth = 1;
        gbc.gridx = 0; gbc.gridy = 1;
        inputPanel.add(new JLabel("Full Name:"), gbc);
        gbc.gridx = 1;
        nameField.setPreferredSize(new Dimension(180, 25));
        inputPanel.add(nameField, gbc);

        // Email field
        gbc.gridx = 0; gbc.gridy = 2;
        inputPanel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        emailField.setPreferredSize(new Dimension(180, 25));
        inputPanel.add(emailField, gbc);

        // DOB field
        gbc.gridx = 0; gbc.gridy = 3;
        inputPanel.add(new JLabel("Date of Birth:"), gbc);
        gbc.gridx = 1;
        ageField.setPreferredSize(new Dimension(180, 25));
        inputPanel.add(ageField, gbc);

        // Academic Information Section
        JLabel academicLabel = new JLabel("Academic Information");
        academicLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
        academicLabel.setForeground(new Color(70, 130, 180));
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        gbc.insets = new Insets(15, 8, 8, 8);
        inputPanel.add(academicLabel, gbc);

        // Department field
        gbc.gridwidth = 1;
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.gridx = 0; gbc.gridy = 5;
        inputPanel.add(new JLabel("Department:"), gbc);
        gbc.gridx = 1;
        majorField.setPreferredSize(new Dimension(180, 25));
        inputPanel.add(majorField, gbc);

        // Class field
        gbc.gridx = 0; gbc.gridy = 6;
        inputPanel.add(new JLabel("Class Year:"), gbc);
        gbc.gridx = 1;
        classComboBox.setPreferredSize(new Dimension(180, 25));
        inputPanel.add(classComboBox, gbc);

        // GPA field
        gbc.gridx = 0; gbc.gridy = 7;
        inputPanel.add(new JLabel("GPA:"), gbc);
        gbc.gridx = 1;
        gpaField.setPreferredSize(new Dimension(180, 25));
        inputPanel.add(gpaField, gbc);

        // Add some spacing at the bottom
        gbc.gridx = 0; gbc.gridy = 8; gbc.gridwidth = 2;
        gbc.weighty = 1.0;
        inputPanel.add(new JLabel(), gbc);

        return inputPanel;
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(backButton);
        return buttonPanel;
    }

    private void setupEventListeners() {
        updateButton.addActionListener(e -> updateStudent());
        deleteButton.addActionListener(e -> deleteStudent());
        clearButton.addActionListener(e -> clearFields());
        backButton.addActionListener(e -> {
            dispose();
            new AdministrationForm();
        });
    }

    private void updateStudent() {
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a student to update.",
                    "Selection Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (validateFields()) {
            try {
                // Get the student ID from the displayed table (column index 2)
                String studentId = tableModel.getValueAt(selectedRow, 2).toString();

                // Update the actual account in the accounts map
                Account account = accounts.get(studentId);
                if (account instanceof StudentAccount) {
                    StudentAccount student = (StudentAccount) account;

                    // Create a new StudentAccount with updated information
                    String[] nameParts = nameField.getText().trim().split(" ", 2);
                    String firstName = nameParts[0];
                    String lastName = nameParts.length > 1 ? nameParts[1] : "";

                    StudentAccount updatedStudent = new StudentAccount(
                            student.getID(),
                            student.getPassword(),
                            emailField.getText().trim(),
                            firstName,
                            lastName,
                            student.getGender(),
                            ageField.getText().trim(), // DOB
                            classComboBox.getSelectedItem().toString(),
                            student.getRoll(),
                            majorField.getText().trim(), // Department
                            student.getStatus()
                    );

                    // Update the accounts map
                    accounts.put(studentId, updatedStudent);
                }

                // Reload data and refresh view
                loadStudentsFromAccounts();
                clearFields();
                studentTable.clearSelection();
                selectedRow = -1;
                JOptionPane.showMessageDialog(this, "Student updated successfully!");

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error updating student: " + ex.getMessage(),
                        "Update Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void deleteStudent() {
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a student to delete.",
                    "Selection Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete this student?",
                "Confirm Delete", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            // Get the student ID from the displayed table (column index 2)
            String studentId = tableModel.getValueAt(selectedRow, 2).toString();

            // Remove from the main accounts map
            accounts.remove(studentId);

            // Reload data and refresh view
            loadStudentsFromAccounts();
            clearFields();
            selectedRow = -1;
            JOptionPane.showMessageDialog(this, "Student deleted successfully!");
        }
    }

    private void clearFields() {
        nameField.setText("");
        emailField.setText("");
        ageField.setText("");
        majorField.setText("");
        gpaField.setText("");
        classComboBox.setSelectedIndex(0);
        studentTable.clearSelection();
        selectedRow = -1;
    }

    private void populateFields() {
        if (selectedRow != -1) {
            nameField.setText(tableModel.getValueAt(selectedRow, 1).toString());
            emailField.setText(tableModel.getValueAt(selectedRow, 3).toString());
            ageField.setText(tableModel.getValueAt(selectedRow, 4).toString()); // DOB
            majorField.setText(tableModel.getValueAt(selectedRow, 5).toString()); // Department
            gpaField.setText(tableModel.getValueAt(selectedRow, 6).toString()); // GPA
            classComboBox.setSelectedItem(tableModel.getValueAt(selectedRow, 7).toString()); // Class
        }
    }

    private boolean validateFields() {
        if (nameField.getText().trim().isEmpty() ||
                emailField.getText().trim().isEmpty() ||
                ageField.getText().trim().isEmpty() ||
                majorField.getText().trim().isEmpty() ||
                gpaField.getText().trim().isEmpty()) {

            JOptionPane.showMessageDialog(this, "Please fill in all fields.",
                    "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        try {
            double gpa = Double.parseDouble(gpaField.getText().trim());
            if (gpa < 0.0 || gpa > 4.0) {
                JOptionPane.showMessageDialog(this, "Please enter a valid GPA (0.0-4.0).",
                        "Validation Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid number for GPA.",
                    "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

    // Fixed: Simplified filtering logic
    private void filterStudents() {
        String selectedClass = classFilterComboBox.getSelectedItem().toString();
        String selectedDept = departmentComboBox.getSelectedItem().toString();

        tableModel.setRowCount(0);

        for (int i = 0; i < allStudentsModel.getRowCount(); i++) {
            String studentClass = allStudentsModel.getValueAt(i, 7) != null ?
                    allStudentsModel.getValueAt(i, 7).toString() : ""; // Class is now column 7
            String studentDept = allStudentsModel.getValueAt(i, 5) != null ?
                    allStudentsModel.getValueAt(i, 5).toString() : ""; // Department is now column 5

            boolean classMatch = selectedClass.equals("Select") || selectedClass.equals(studentClass);
            boolean deptMatch = selectedDept.equals("Select") || selectedDept.equals(studentDept);

            if (classMatch && deptMatch) {
                Vector<Object> rowData = new Vector<>();
                for (int j = 0; j < allStudentsModel.getColumnCount(); j++) {
                    rowData.add(allStudentsModel.getValueAt(i, j));
                }
                tableModel.addRow(rowData);
            }
        }

        studentTable.clearSelection();
        selectedRow = -1;
        clearFields();
    }

    private void loadStudentsFromAccounts() {
        allStudentsModel.setRowCount(0);
        for (Account acc : accounts.values()) {
            if (acc instanceof StudentAccount) {
                StudentAccount student = (StudentAccount) acc;
                Vector<Object> row = new Vector<>();
                row.add(student.getRoll());
                row.add(student.getFirstName() + " " + student.getLastName());
                row.add(student.getID());
                row.add(student.getEmail());
                row.add(student.getDob());
                row.add(student.getDepartment()); // Department column
                row.add(student.getCg());
                row.add(student.getClassNo());
                allStudentsModel.addRow(row);
            }
        }
        filterStudents();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new StudentList());
    }
}
