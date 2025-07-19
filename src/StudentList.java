import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class StudentList extends JFrame {
    private JTable studentTable;
    private DefaultTableModel tableModel;
    private DefaultTableModel allStudentsModel;
    private JTextField nameField, emailField, dobField, gpaField;
    private JComboBox<String> classComboBox;
    private JComboBox<String> departmentComboBox, updateDepartmentCombo;
    private JComboBox<String> classFilterComboBox;

    // New filter components
    private JTextField rollFilterField, idFilterField, cgpaFilterField;
    private JButton clearFiltersButton;

    private JButton updateButton, deleteButton, clearButton, backButton;
    private int selectedRow = -1;

    static int totalStudents = 0;
    private int filteredStudentCount = 0;
    private JLabel filteredCountLabel;

    Map<String, Account> accounts = new HashMap<>();

    public StudentList() {

        this.accounts = Main.getAccounts();

        initializeComponents();
        setupLayout();
        setupEventListeners();
        loadStudentsFromAccounts();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Student Management System");
        setSize(1400, 700); // Increased width to accommodate new filters
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
        
        // Initialize filtered count label
        filteredCountLabel = new JLabel("Students: 0 / 0 (filtered/total)");

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

        // Set custom renderer to change background color for CGPA below 2.0
        studentTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                try {
                    double cgpa = Double.parseDouble(table.getValueAt(row, 6).toString());
                    if (cgpa < 2.0) {
                        c.setBackground(new Color(255, 102, 102)); // Light red
                    } else {
                        c.setBackground(isSelected ? table.getSelectionBackground() : Color.WHITE);
                    }
                } catch (Exception e) {
                    c.setBackground(isSelected ? table.getSelectionBackground() : Color.WHITE);
                }
                return c;
            }
        });

        // Create input fields
        nameField = new JTextField(20);
        emailField = new JTextField(20);
        dobField = new JTextField(20);
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
        updateDepartmentCombo = new JComboBox<>(departmentFilters);

        // Initialize new filter components
        rollFilterField = new JTextField(10);
        rollFilterField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                filterStudents();
            }
        });

        idFilterField = new JTextField(10);
        idFilterField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                filterStudents();
            }
        });

        cgpaFilterField = new JTextField(10);
        cgpaFilterField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                filterStudents();
            }
        });

        clearFiltersButton = new JButton("Clear Filters");
        clearFiltersButton.setBackground(new Color(149, 165, 166));
        clearFiltersButton.setForeground(Color.WHITE);
        clearFiltersButton.setOpaque(true);
        clearFiltersButton.setBorderPainted(false);

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

        // Enhanced filter panel with new filters
        JPanel filterPanel = createEnhancedFilterPanel();
        topPanel.add(filterPanel, BorderLayout.CENTER);

        mainPanel.add(topPanel, BorderLayout.NORTH);

        // Create center panel with table and input form
        JPanel centerPanel = new JPanel(new BorderLayout());

        // Table panel
        JScrollPane scrollPane = new JScrollPane(studentTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Student List"));
        scrollPane.setPreferredSize(new Dimension(900, 400)); // Adjusted width

        centerPanel.add(scrollPane, BorderLayout.CENTER);

        // Add input form panel to the right
        JPanel inputPanel = createInputPanel();
        centerPanel.add(inputPanel, BorderLayout.EAST);

        mainPanel.add(centerPanel, BorderLayout.CENTER);

        // Button panel at bottom
        JPanel buttonPanel = createButtonPanel();
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private JPanel createEnhancedFilterPanel() {
        JPanel filterPanel = new JPanel(new GridBagLayout());
        filterPanel.setBorder(BorderFactory.createTitledBorder("Filters"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // First row - existing filters
        gbc.gridx = 0; gbc.gridy = 0;
        filterPanel.add(new JLabel("Year:"), gbc);
        gbc.gridx = 1;
        filterPanel.add(classFilterComboBox, gbc);

        gbc.gridx = 2;
        filterPanel.add(new JLabel("Department:"), gbc);
        gbc.gridx = 3;
        filterPanel.add(departmentComboBox, gbc);

        // Second row - new filters
        gbc.gridx = 4;
        filterPanel.add(new JLabel("Roll:"), gbc);
        gbc.gridx = 5;
        filterPanel.add(rollFilterField, gbc);

        gbc.gridx = 6;
        filterPanel.add(new JLabel("Student ID:"), gbc);
        gbc.gridx = 7;
        filterPanel.add(idFilterField, gbc);

        gbc.gridx = 8;
        filterPanel.add(new JLabel("Min CGPA:"), gbc);
        gbc.gridx = 9;
        filterPanel.add(cgpaFilterField, gbc);

        // Clear filters button
        gbc.gridx = 10; gbc.gridheight = 2;
        //gbc.fill = GridBagConstraints.VERTICAL;
        filterPanel.add(clearFiltersButton, gbc);

        gbc.gridx = 11; gbc.gridheight = 1;
        filterPanel.add(filteredCountLabel, gbc);

        return filterPanel;
    }

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
        dobField.setPreferredSize(new Dimension(180, 25));
        inputPanel.add(dobField, gbc);

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
        updateDepartmentCombo.setPreferredSize(new Dimension(180, 25));
        inputPanel.add(updateDepartmentCombo, gbc);

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

        // Clear filters button action
        clearFiltersButton.addActionListener(e -> clearAllFilters());
    }

    private void clearAllFilters() {
        classFilterComboBox.setSelectedIndex(0);
        departmentComboBox.setSelectedIndex(0);
        rollFilterField.setText("");
        idFilterField.setText("");
        cgpaFilterField.setText("");
        filterStudents();
    }

    private void updateStudent() {
        getRootPane().setDefaultButton(updateButton);
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
                            dobField.getText().trim(), // DOB
                            classComboBox.getSelectedItem().toString(),
                            student.getRoll(),
                            updateDepartmentCombo.getSelectedItem().toString(), // Department
                            student.getStatus(),
                            Double.parseDouble(gpaField.getText().trim())
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
        dobField.setText("");
        updateDepartmentCombo.setSelectedIndex(0);
        gpaField.setText("");
        classComboBox.setSelectedIndex(0);
        studentTable.clearSelection();
        selectedRow = -1;
    }

    private void populateFields() {
        if (selectedRow != -1) {
            nameField.setText(tableModel.getValueAt(selectedRow, 1).toString());
            emailField.setText(tableModel.getValueAt(selectedRow, 3).toString());
            dobField.setText(tableModel.getValueAt(selectedRow, 4).toString()); // DOB
            updateDepartmentCombo.setSelectedItem(tableModel.getValueAt(selectedRow, 5).toString()); // Department
            gpaField.setText(tableModel.getValueAt(selectedRow, 6).toString()); // GPA
            classComboBox.setSelectedItem(tableModel.getValueAt(selectedRow, 7).toString()); // Class
        }
    }

    private boolean validateFields() {
        if (nameField.getText().trim().isEmpty() ||
                emailField.getText().trim().isEmpty() ||
                dobField.getText().trim().isEmpty() ||
                updateDepartmentCombo.getSelectedItem().equals("Select") ||
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

    // Enhanced filtering logic with new filters
    private void filterStudents() {
        String selectedClass = classFilterComboBox.getSelectedItem().toString();
        String selectedDept = departmentComboBox.getSelectedItem().toString();
        String rollFilter = rollFilterField.getText().trim();
        String idFilter = idFilterField.getText().trim().toLowerCase();
        String cgpaFilter = cgpaFilterField.getText().trim();

        tableModel.setRowCount(0);
        
        // Reset filtered student count
        filteredStudentCount = 0;

        for (int i = 0; i < allStudentsModel.getRowCount(); i++) {
            String studentClass = allStudentsModel.getValueAt(i, 7) != null ?
                    allStudentsModel.getValueAt(i, 7).toString() : "";
            String studentDept = allStudentsModel.getValueAt(i, 5) != null ?
                    allStudentsModel.getValueAt(i, 5).toString() : "";
            String studentRoll = allStudentsModel.getValueAt(i, 0) != null ?
                    allStudentsModel.getValueAt(i, 0).toString() : "";
            String studentId = allStudentsModel.getValueAt(i, 2) != null ?
                    allStudentsModel.getValueAt(i, 2).toString().toLowerCase() : "";
            String studentGpa = allStudentsModel.getValueAt(i, 6) != null ?
                    allStudentsModel.getValueAt(i, 6).toString() : "";

            // Existing filters
            boolean classMatch = selectedClass.equals("Select") || selectedClass.equals(studentClass);
            boolean deptMatch = selectedDept.equals("Select") || selectedDept.equals(studentDept);

            // New filters
            boolean rollMatch = rollFilter.isEmpty() || studentRoll.contains(rollFilter);
            boolean idMatch = idFilter.isEmpty() || studentId.contains(idFilter);

            boolean cgpaMatch = true;
            if (!cgpaFilter.isEmpty()) {
                try {
                    double filterCgpa = Double.parseDouble(cgpaFilter);
                    double studentCgpaValue = Double.parseDouble(studentGpa);
                    cgpaMatch = studentCgpaValue >= filterCgpa;
                } catch (NumberFormatException e) {
                    cgpaMatch = true; // If invalid number, don't filter
                }
            }

            if (classMatch && deptMatch && rollMatch && idMatch && cgpaMatch) {
                Vector<Object> rowData = new Vector<>();
                for (int j = 0; j < allStudentsModel.getColumnCount(); j++) {
                    rowData.add(allStudentsModel.getValueAt(i, j));
                }
                tableModel.addRow(rowData);
                
                // Increment filtered student count
                filteredStudentCount++;
            }
        }
        
        // Update filtered count label
        filteredCountLabel.setText("Students: " + filteredStudentCount + " / " + totalStudents + " (filtered/total)");

        studentTable.clearSelection();
        selectedRow = -1;
        clearFields();
    }

    private void loadStudentsFromAccounts() {
        allStudentsModel.setRowCount(0);
        totalStudents = 0;
        
        for (Account acc : accounts.values()) {
            if (acc.getStatus().equals("Student")) {
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
                totalStudents++;
            }
        }
        filterStudents();
    }

}