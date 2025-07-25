import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class TeacherList extends JFrame {

    private JTable teacherTable;
    private DefaultTableModel tableModel;
    private DefaultTableModel allTeachersModel;
    private JTextField nameField, emailField, dobField;
    private JComboBox<String> departmentComboBox, updateDepartmentCombo;

    // Filter components
    private JTextField idFilterField;
    private JComboBox<String> departmentFilterComboBox;
    private JButton clearFiltersButton;

    private JButton updateButton, deleteButton, clearButton, backButton;
    private int selectedRow = -1;

    static int totalTeachers = 0;
    private int filteredTeacherCount = 0;
    private JLabel filteredCountLabel;

    Map<String, Account> accounts = new HashMap<>();

    public TeacherList() {

        this.accounts = Main.getAccounts();

        initializeComponents();
        setupLayout();
        setupEventListeners();
        loadTeachersFromAccounts();

        setTitle("Teacher Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 700);
        setLocationRelativeTo(null);
        setVisible(true);
    }


    private void initializeComponents() {
        // Create table with column names
        String[] columnNames = {"Name", "ID", "Email", "Date of Birth", "Department"};

        // Create models for all teachers and filtered view
        allTeachersModel = new DefaultTableModel(columnNames, 0) {
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
        filteredCountLabel = new JLabel("Teachers: 0 / 0 (filtered/total)");

        teacherTable = new JTable(tableModel) {

            @Override
            public Component prepareRenderer(javax.swing.table.TableCellRenderer renderer, int row, int column) {

                Component c = super.prepareRenderer(renderer, row, column);
                if (!isRowSelected(row)) {
                    c.setBackground(row % 2 == 0 ? new Color(245, 245, 245) : Color.WHITE);
                } else {
                    c.setBackground(new Color(184, 207, 229));
                }
                return c;
            }
        };
        teacherTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        teacherTable.getSelectionModel().addListSelectionListener(e -> {

            if (!e.getValueIsAdjusting()) {
                selectedRow = teacherTable.getSelectedRow();
                if (selectedRow != -1) {
                    populateFields();
                }
            }
        });
        teacherTable.setRowHeight(24);
        teacherTable.setFont(new Font("Arial", Font.PLAIN, 15));
        teacherTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 16));
        teacherTable.setFillsViewportHeight(true);
        teacherTable.getTableHeader().setReorderingAllowed(false);
        teacherTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        // Create input fields
        nameField = new JTextField(20);
        nameField.setToolTipText("Enter full name (First Last)");
        emailField = new JTextField(20);
        emailField.setToolTipText("Enter email address");
        dobField = new JTextField(20);
        dobField.setToolTipText("Format: YYYY-MM-DD");

        // Create department combo boxes
        String[] departmentFilters = utils.DEPARTMENTS;
        departmentComboBox = new JComboBox<>(departmentFilters);
        departmentComboBox.setToolTipText("Select department");
        updateDepartmentCombo = new JComboBox<>(departmentFilters);
        updateDepartmentCombo.setToolTipText("Select department");

        // Initialize filter components
        departmentFilterComboBox = new JComboBox<>(departmentFilters);
        departmentFilterComboBox.setToolTipText("Filter by department");
        departmentFilterComboBox.addActionListener(e -> filterTeachers());

        idFilterField = new JTextField(20);
        idFilterField.setToolTipText("Filter by teacher ID");
        idFilterField.setPreferredSize(new Dimension(120, 25));
        idFilterField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                filterTeachers();
            }
        });

        clearFiltersButton = new JButton("Reset Filters");
        utils.styleButton(clearFiltersButton);
        clearFiltersButton.setToolTipText("Clear all filters");

        // Create buttons
        updateButton = new JButton("Update Teacher");
        updateButton.setToolTipText("Update selected teacher");
        deleteButton = new JButton("Delete Teacher");
        deleteButton.setToolTipText("Delete selected teacher");
        clearButton = new JButton("Clear Fields");
        clearButton.setToolTipText("Clear input fields");
        backButton = new JButton("Back");
        backButton.setToolTipText("Return to Administration Dashboard");

        utils.styleButton(backButton);
        utils.styleButton(clearButton);
        utils.styleButton(updateButton);
        utils.styleButton(deleteButton);
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
        JPanel filterPanel = createFilterPanel();
        topPanel.add(filterPanel, BorderLayout.CENTER);

        mainPanel.add(topPanel, BorderLayout.NORTH);

        // Create center panel with table and input form
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBorder(BorderFactory.createEmptyBorder(0, 32, 20, 10));

        // Table panel
        JScrollPane scrollPane = new JScrollPane(teacherTable);
        scrollPane.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(10, 10, 10, 10),
                BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)), "Teacher Information", 0, 0, new Font("Arial", Font.BOLD, 16), new Color(52, 73, 94))
        ));
        scrollPane.setPreferredSize(new Dimension(700, 400));

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

    private JPanel createFilterPanel() {

        JPanel filterPanel = new JPanel(new GridBagLayout());
        filterPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(24, 32, 24, 32),
                BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)), "Filters:", 0, 0, new Font("Arial", Font.BOLD, 16), new Color(52, 73, 94))
        ));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Department filter
        gbc.gridx = 0;
        gbc.gridy = 0;
        filterPanel.add(new JLabel("Department:"), gbc);
        gbc.gridx = 1;
        filterPanel.add(departmentFilterComboBox, gbc);

        // ID filter
        gbc.gridx = 2;
        filterPanel.add(new JLabel("Teacher ID:"), gbc);
        gbc.gridx = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0.2;
        filterPanel.add(idFilterField, gbc);

        // Clear filters button
        gbc.gridx = 4;
        filterPanel.add(clearFiltersButton, gbc);

        // Add filtered count label
        gbc.gridx = 5;
        filterPanel.add(filteredCountLabel, gbc);

        return filterPanel;
    }

    private JPanel createInputPanel() {

        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(10, 10, 0, 20),
                BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)), "Edit Information", 0, 0, new Font("Arial", Font.BOLD, 16), new Color(52, 73, 94))
        ));
        inputPanel.setPreferredSize(new Dimension(320, 400));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Personal Information Section
        JLabel personalLabel = new JLabel("Personal Information");
        personalLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
        personalLabel.setForeground(new Color(70, 130, 180));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        inputPanel.add(personalLabel, gbc);

        // Name field
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 1;
        inputPanel.add(new JLabel("Full Name:"), gbc);
        gbc.gridx = 1;
        nameField.setPreferredSize(new Dimension(220, 25));
        inputPanel.add(nameField, gbc);

        // Email field
        gbc.gridx = 0;
        gbc.gridy = 2;
        inputPanel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        emailField.setPreferredSize(new Dimension(220, 25));
        inputPanel.add(emailField, gbc);

        // DOB field
        gbc.gridx = 0;
        gbc.gridy = 3;
        inputPanel.add(new JLabel("Date of Birth:"), gbc);
        gbc.gridx = 1;
        dobField.setPreferredSize(new Dimension(220, 25));
        inputPanel.add(dobField, gbc);

        // Academic Information Section
        JLabel academicLabel = new JLabel("Academic Information");
        academicLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
        academicLabel.setForeground(new Color(70, 130, 220));
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(15, 8, 8, 8);
        inputPanel.add(academicLabel, gbc);

        // Department field
        gbc.gridwidth = 1;
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.gridx = 0;
        gbc.gridy = 5;
        inputPanel.add(new JLabel("Department:"), gbc);
        gbc.gridx = 1;
        updateDepartmentCombo.setPreferredSize(new Dimension(220, 25));
        inputPanel.add(updateDepartmentCombo, gbc);

        // Add some spacing at the bottom
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
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

        updateButton.addActionListener(e -> updateTeacher());
        deleteButton.addActionListener(e -> deleteTeacher());
        clearButton.addActionListener(e -> clearFields());
        backButton.addActionListener(e -> {
            this.dispose();
            new AdministrationForm();
        });

        // Clear filters button action
        clearFiltersButton.addActionListener(e -> clearAllFilters());
    }

    private void clearAllFilters() {

        departmentFilterComboBox.setSelectedIndex(0);
        idFilterField.setText("");
        filterTeachers();
    }

    private void updateTeacher() {

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a teacher to update.",
                    "Selection Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (validateFields()) {
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to update this teacher?",
                    "Confirm Update", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    String teacherId = null;
                    try {
                        teacherId = tableModel.getValueAt(selectedRow, 1).toString();
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this, "Error retrieving teacher ID: " + ex.getMessage(),
                                "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    Account account = null;
                    try {
                        account = accounts.get(teacherId);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this, "Error accessing account: " + ex.getMessage(),
                                "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    if (account instanceof TeacherAccount) {
                        TeacherAccount teacher = (TeacherAccount) account;
                        String[] nameParts = nameField.getText().trim().split(" ", 2);
                        String firstName = nameParts[0];
                        String lastName = nameParts.length > 1 ? nameParts[1] : "";
                        TeacherAccount updatedTeacher = new TeacherAccount(
                                teacher.getID(),
                                teacher.getPassword(),
                                emailField.getText().trim(),
                                firstName,
                                lastName,
                                teacher.getGender(),
                                dobField.getText().trim(),
                                updateDepartmentCombo.getSelectedItem().toString(),
                                teacher.getStatus()
                        );
                        accounts.put(teacherId, updatedTeacher);
                    }
                    loadTeachersFromAccounts();
                    clearFields();
                    teacherTable.clearSelection();
                    selectedRow = -1;
                    JOptionPane.showMessageDialog(this, "Teacher updated successfully!");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Error updating teacher: " + ex.getMessage(),
                            "Update Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private void deleteTeacher() {

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a teacher to delete.",
                    "Selection Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete this teacher?",
                "Confirm Delete", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                String teacherId = null;
                try {
                    teacherId = tableModel.getValueAt(selectedRow, 1).toString();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Error retrieving teacher ID: " + ex.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                accounts.remove(teacherId);
                loadTeachersFromAccounts();
                clearFields();
                selectedRow = -1;
                JOptionPane.showMessageDialog(this, "Teacher deleted successfully!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error deleting teacher: " + ex.getMessage(),
                        "Delete Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void clearFields() {

        nameField.setText("");
        emailField.setText("");
        dobField.setText("");
        updateDepartmentCombo.setSelectedIndex(0);
        teacherTable.clearSelection();
        selectedRow = -1;
    }

    private void populateFields() {

        if (selectedRow != -1) {
            nameField.setText(tableModel.getValueAt(selectedRow, 0).toString());
            emailField.setText(tableModel.getValueAt(selectedRow, 2).toString());
            dobField.setText(tableModel.getValueAt(selectedRow, 3).toString());
            updateDepartmentCombo.setSelectedItem(tableModel.getValueAt(selectedRow, 4).toString());
        }
    }

    private boolean validateFields() {

        if (nameField.getText().trim().isEmpty() ||
                emailField.getText().trim().isEmpty() ||
                dobField.getText().trim().isEmpty() ||
                updateDepartmentCombo.getSelectedItem().equals("Select")) {

            JOptionPane.showMessageDialog(this, "Please fill in all fields.",
                    "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

    private void filterTeachers() {

        try {
            String selectedDept = departmentFilterComboBox.getSelectedItem().toString();
            String idFilter = idFilterField.getText().trim().toLowerCase();
            tableModel.setRowCount(0);
            filteredTeacherCount = 0;
            for (int i = 0; i < allTeachersModel.getRowCount(); i++) {
                String teacherDept = allTeachersModel.getValueAt(i, 4) != null ?
                        allTeachersModel.getValueAt(i, 4).toString() : "";
                String teacherId = allTeachersModel.getValueAt(i, 1) != null ?
                        allTeachersModel.getValueAt(i, 1).toString().toLowerCase() : "";
                String teacherName = allTeachersModel.getValueAt(i, 0) != null ?
                        allTeachersModel.getValueAt(i, 0).toString() : "";
                boolean deptMatch = selectedDept.equals("Select") || selectedDept.equals(teacherDept);
                boolean idMatch = idFilter.isEmpty() || teacherId.contains(idFilter);
                if (deptMatch && idMatch) {
                    Vector<Object> rowData = new Vector<>();
                    for (int j = 0; j < allTeachersModel.getColumnCount(); j++) {
                        rowData.add(allTeachersModel.getValueAt(i, j));
                    }
                    tableModel.addRow(rowData);
                    filteredTeacherCount++;
                }
            }
            filteredCountLabel.setText("Teachers: " + filteredTeacherCount + " / " + totalTeachers + " (filtered/total)");
            teacherTable.clearSelection();
            selectedRow = -1;
            clearFields();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error filtering teachers: " + e.getMessage(),
                    "Filter Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadTeachersFromAccounts() {

        allTeachersModel.setRowCount(0);
        totalTeachers = 0;

        for (Account acc : accounts.values()) {
            if (acc.getStatus().equals("Teacher")) {
                TeacherAccount teacher = (TeacherAccount) acc;
                Vector<Object> row = new Vector<>();
                row.add(teacher.getFirstName() + " " + teacher.getLastName());
                row.add(teacher.getID());
                row.add(teacher.getEmail());
                row.add(teacher.getDob());
                row.add(teacher.getDepartment());
                allTeachersModel.addRow(row);
                totalTeachers++;
            }
        }
        filterTeachers();
    }

}