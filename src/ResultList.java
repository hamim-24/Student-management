import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class ResultList extends JFrame {
    private JTable studentTable;
    private DefaultTableModel tableModel;
    private DefaultTableModel allStudentsModel;
    private JTextField nameField, emailField, dobField, gpaField;
    private JComboBox<String> yearComboBox;
    private JComboBox<String> departmentComboBox, updateDepartmentCombo;
    private JComboBox<String> yearFilterComboBox;

    // New filter components
    private JTextField rollFilterField, idFilterField, cgpaFilterField;
    private JButton clearFiltersButton;

    private JButton updateButton, deleteButton, clearButton, backButton;
    private int selectedRow = -1;

    static int totalStudents = 0;
    private int filteredStudentCount = 0;
    private JLabel filteredCountLabel;

    Map<String, Account> accounts = new HashMap<>();

    public ResultList() {

        this.accounts = Main.getAccounts();

        initializeComponents();
        setupLayout();
        setupEventListeners();
        loadStudentsFromAccounts();

        setTitle("Student List");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 700); // Adjusted width for better fit
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initializeComponents() {
        // Create table with column names
        String[] columnNames = {"Roll", "Name", "ID", "Email", "DOB", "Department", "GPA", "Year"};

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

        // backgroud is red if cgpa < 2
        studentTable = new JTable(tableModel) {
            @Override
            public Component prepareRenderer(javax.swing.table.TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);
                try {
                    Object cgpaObj = getValueAt(row, 6);
                    if (cgpaObj != null && !cgpaObj.toString().isEmpty()) {
                        double cgpa = Double.parseDouble(cgpaObj.toString());
                        if (cgpa < 2.0) {
                            c.setBackground(new Color(255, 204, 204)); // Softer red
                        } else if (isRowSelected(row)) {
                            c.setBackground(new Color(184, 207, 229));
                        } else {
                            c.setBackground(row % 2 == 0 ? new Color(245, 245, 245) : Color.WHITE);
                        }
                    }
                } catch (Exception e) {
                    if (isRowSelected(row)) {
                        c.setBackground(new Color(184, 207, 229));
                    } else {
                        c.setBackground(row % 2 == 0 ? new Color(245, 245, 245) : Color.WHITE);
                    }
                }
                return c;
            }
        };
        studentTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        studentTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                selectedRow = studentTable.getSelectedRow();
                if (selectedRow != -1) {
                    populateFields();
                }
            }
        });
        studentTable.setRowHeight(24);
        studentTable.setFillsViewportHeight(true);
        studentTable.getTableHeader().setReorderingAllowed(false);
        studentTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        // Set custom renderer to change background color for CGPA below 2.0
        studentTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                try {
                    double cgpa = Double.parseDouble(table.getValueAt(row, 6).toString());
                    if (cgpa < 2.0) {
                        c.setBackground(new Color(255, 204, 204)); // Softer red
                    } else if (!isSelected) {
                        c.setBackground(row % 2 == 0 ? new Color(245, 245, 245) : Color.WHITE);
                    }
                } catch (Exception e) {
                    if (!isSelected) {
                        c.setBackground(row % 2 == 0 ? new Color(245, 245, 245) : Color.WHITE);
                    }
                }
                return c;
            }
        });

        // Create input fields with tooltips
        nameField = new JTextField(20);
        nameField.setToolTipText("Enter full name (First Last)");
        emailField = new JTextField(20);
        emailField.setToolTipText("Enter email address");
        dobField = new JTextField(20);
        dobField.setToolTipText("Format: YYYY-MM-DD");
        gpaField = new JTextField(20);
        gpaField.setToolTipText("GPA (0.0 - 4.0)");

        // Create year combo box for adding students
        String[] years = utils.YEARS;
        yearComboBox = new JComboBox<>(years);
        yearComboBox.setToolTipText("Select year");

        // Create year filter combo box
        String[] yearFilters = utils.YEARS;
        yearFilterComboBox = new JComboBox<>(yearFilters);
        yearFilterComboBox.setToolTipText("Filter by year");
        yearFilterComboBox.addActionListener(e -> filterStudents());

        // Create department filter combo box
        String[] departmentFilters = utils.DEPARTMENTS;
        departmentComboBox = new JComboBox<>(departmentFilters);
        departmentComboBox.setToolTipText("Filter by department");
        departmentComboBox.addActionListener(e -> filterStudents());
        updateDepartmentCombo = new JComboBox<>(departmentFilters);
        updateDepartmentCombo.setToolTipText("Select department");

        // Initialize new filter components with tooltips
        rollFilterField = new JTextField(20);
        rollFilterField.setToolTipText("Filter by roll number");
        rollFilterField.setPreferredSize(new Dimension(80, 25));
        rollFilterField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                filterStudents();
            }
        });

        idFilterField = new JTextField(20);
        idFilterField.setToolTipText("Filter by student ID");
        idFilterField.setPreferredSize(new Dimension(100, 25));
        idFilterField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                filterStudents();
            }
        });

        cgpaFilterField = new JTextField(20);
        cgpaFilterField.setToolTipText("Filter by minimum CGPA");
        cgpaFilterField.setPreferredSize(new Dimension(80, 25));
        cgpaFilterField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                filterStudents();
            }
        });

        clearFiltersButton = new JButton("Reset Filters");
        utils.styleButton(clearFiltersButton);
        clearFiltersButton.setToolTipText("Clear all filters");

        // Create buttons with tooltips
        updateButton = new JButton("Update Student");
        updateButton.setToolTipText("Update selected student");
        deleteButton = new JButton("Delete Student");
        deleteButton.setToolTipText("Delete selected student");
        clearButton = new JButton("Clear Fields");
        clearButton.setToolTipText("Clear input fields");
        backButton = new JButton("Back");
        backButton.setToolTipText("Return to Administration Dashboard");
        utils.styleButton(backButton);
        utils.styleButton(updateButton);
        utils.styleButton(deleteButton);
        utils.styleButton(clearButton);

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

        centerPanel.setBorder(BorderFactory.createEmptyBorder(0, 32, 20, 32));

        // Table panel
        JScrollPane scrollPane = new JScrollPane(studentTable);
        scrollPane.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(10, 10, 10, 10),
                BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)), "Student List", 0, 0, new Font("Arial", Font.BOLD, 16), new Color(52, 73, 94))
        ));
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
        filterPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(24, 32, 24, 32),
                BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)), "Filter:", 0, 0, new Font("Arial", Font.BOLD, 16), new Color(52, 73, 94))
        ));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 10, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // First row - existing filters
        gbc.gridx = 0; gbc.gridy = 0;
        filterPanel.add(new JLabel("Year:"), gbc);
        gbc.gridx = 1;
        filterPanel.add(yearFilterComboBox, gbc);

        gbc.gridx = 2;
        filterPanel.add(new JLabel("Department:"), gbc);
        gbc.gridx = 3;
        filterPanel.add(departmentComboBox, gbc);

        // Second row - new filters
        gbc.gridx = 4;
        filterPanel.add(new JLabel("Roll:"), gbc);
        gbc.gridx = 5;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0.2;
        filterPanel.add(rollFilterField, gbc);

        gbc.gridx = 6;
        gbc.weightx = 0;
        filterPanel.add(new JLabel("ID:"), gbc);
        gbc.gridx = 7;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0.2;
        filterPanel.add(idFilterField, gbc);

        gbc.gridx = 8;
        gbc.weightx = 0;
        filterPanel.add(new JLabel("Min CGPA:"), gbc);
        gbc.gridx = 9;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0.2;
        filterPanel.add(cgpaFilterField, gbc);

        // Clear filters button
        gbc.gridx = 10;
        filterPanel.add(clearFiltersButton, gbc);

        gbc.gridx = 11; gbc.gridheight = 1;
        filterPanel.add(filteredCountLabel, gbc);

        return filterPanel;
    }

    private JPanel createInputPanel() {

        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(10, 10, 0, 0),
                BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)), "Edit Student Information", 0, 0, new Font("Arial", Font.BOLD, 16), new Color(52, 73, 94))
        ));
        inputPanel.setPreferredSize(new Dimension(320, 450));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Personal Information Section
        JLabel personalLabel = new JLabel("Personal Information");
        personalLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
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
        academicLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
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

        // year field
        gbc.gridx = 0; gbc.gridy = 6;
        inputPanel.add(new JLabel("Year:"), gbc);
        gbc.gridx = 1;
        yearComboBox.setPreferredSize(new Dimension(180, 25));
        inputPanel.add(yearComboBox, gbc);

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
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 20));
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
            this.dispose();
            new AdministrationForm();
        });

        // Clear filters button action
        clearFiltersButton.addActionListener(e -> clearAllFilters());
    }

    private void clearAllFilters() {
        yearFilterComboBox.setSelectedIndex(0);
        departmentComboBox.setSelectedIndex(0);
        rollFilterField.setText("");
        idFilterField.setText("");
        cgpaFilterField.setText("");
        filterStudents();
    }

    private void updateStudent() {
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a student to update.",
                    "Selection Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (validateFields()) {
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to update this student?",
                    "Confirm Update", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    // Get the student ID from the displayed table (column index 2)
                    String studentId = null;
                    try {
                        studentId = tableModel.getValueAt(selectedRow, 2).toString();
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this, "Error retrieving student ID: " + ex.getMessage(),
                                "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // Update the actual account in the accounts map
                    Account account = null;
                    try {
                        account = accounts.get(studentId);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this, "Error accessing account: " + ex.getMessage(),
                                "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    if (account instanceof StudentAccount) {
                        StudentAccount student = (StudentAccount) account;

                        // Create a new StudentAccount with updated information
                        String[] nameParts = nameField.getText().trim().split(" ", 2);
                        String firstName = nameParts[0];
                        String lastName = nameParts.length > 1 ? nameParts[1] : "";
                        double gpa = 0.0;
                        try {
                            gpa = Double.parseDouble(gpaField.getText().trim());
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(this, "Invalid GPA: " + ex.getMessage(),
                                    "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        StudentAccount updatedStudent = new StudentAccount(
                                student.getID(),
                                student.getPassword(),
                                emailField.getText().trim(),
                                firstName,
                                lastName,
                                student.getGender(),
                                dobField.getText().trim(), // DOB
                                yearComboBox.getSelectedItem().toString(),
                                student.getRoll(),
                                updateDepartmentCombo.getSelectedItem().toString(), // Department
                                student.getStatus(),
                                gpa
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
            try {
                String studentId = null;
                try {
                    studentId = tableModel.getValueAt(selectedRow, 2).toString();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Error retrieving student ID: " + ex.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                accounts.remove(studentId);
                loadStudentsFromAccounts();
                clearFields();
                selectedRow = -1;
                JOptionPane.showMessageDialog(this, "Student deleted successfully!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error deleting student: " + ex.getMessage(),
                        "Delete Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void clearFields() {
        nameField.setText("");
        emailField.setText("");
        dobField.setText("");
        updateDepartmentCombo.setSelectedIndex(0);
        gpaField.setText("");
        yearComboBox.setSelectedIndex(0);
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
            yearComboBox.setSelectedItem(tableModel.getValueAt(selectedRow, 7).toString()); // year
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
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Unexpected error: " + e.getMessage(),
                    "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

    // Enhanced filtering logic with new filters
    private void filterStudents() {
        try {
            String selectedYear = yearFilterComboBox.getSelectedItem().toString();
            String selectedDept = departmentComboBox.getSelectedItem().toString();
            String rollFilter = rollFilterField.getText().trim();
            String idFilter = idFilterField.getText().trim().toLowerCase();
            String cgpaFilter = cgpaFilterField.getText().trim();

            tableModel.setRowCount(0);
            filteredStudentCount = 0;

            for (int i = 0; i < allStudentsModel.getRowCount(); i++) {
                String studentYear = allStudentsModel.getValueAt(i, 7) != null ?
                        allStudentsModel.getValueAt(i, 7).toString() : "";
                String studentDept = allStudentsModel.getValueAt(i, 5) != null ?
                        allStudentsModel.getValueAt(i, 5).toString() : "";
                String studentRoll = allStudentsModel.getValueAt(i, 0) != null ?
                        allStudentsModel.getValueAt(i, 0).toString() : "";
                String studentId = allStudentsModel.getValueAt(i, 2) != null ?
                        allStudentsModel.getValueAt(i, 2).toString().toLowerCase() : "";
                String studentGpa = allStudentsModel.getValueAt(i, 6) != null ?
                        allStudentsModel.getValueAt(i, 6).toString() : "";

                boolean yearMatch = selectedYear.equals("Select") || selectedYear.equals(studentYear);
                boolean deptMatch = selectedDept.equals("Select") || selectedDept.equals(studentDept);
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
                    } catch (Exception e) {
                        cgpaMatch = true;
                    }
                }

                if (yearMatch && deptMatch && rollMatch && idMatch && cgpaMatch) {
                    Vector<Object> rowData = new Vector<>();
                    for (int j = 0; j < allStudentsModel.getColumnCount(); j++) {
                        rowData.add(allStudentsModel.getValueAt(i, j));
                    }
                    tableModel.addRow(rowData);
                    filteredStudentCount++;
                }
            }
            filteredCountLabel.setText("Students: " + filteredStudentCount + " / " + totalStudents + " (filtered/total)");
            studentTable.clearSelection();
            selectedRow = -1;
            clearFields();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error filtering students: " + e.getMessage(),
                    "Filter Error", JOptionPane.ERROR_MESSAGE);
        }
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
                row.add(student.getYear());
                allStudentsModel.addRow(row);
                totalStudents++;
            }
        }
        filterStudents();
    }

}