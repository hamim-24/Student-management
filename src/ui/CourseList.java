package ui;

import launcher.Main;
import model.Course;
import model.Notification;
import util.Utils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Map;
import java.util.Vector;

public class CourseList extends JFrame {

    private JTable courseTable;
    private DefaultTableModel tableModel;
    private JTextField courseIdField, courseNameField, creditsField, maxStudentsField;
    private JButton addButton, updateButton, deleteButton, clearButton, backButton, courseButton;
    private int selectedRow = -1;
    JLabel totalCourseLabel, enrollmentStatusLabel;
    
    // Search panel components
    private JTextField courseIdFilterField, courseNameFilterField, creditsFilterField, maxStudentsFilterField;
    private JComboBox<String> departmentFilterComboBox;
    //private JButton clearFiltersButton;
    private JLabel filteredCountLabel;
    private DefaultTableModel allCoursesModel;
    private int filteredCourseCount = 0;

    Map<String, Course> courseMap;
    private int totalCourse;

    public CourseList() {

        this.courseMap = Main.getCourseMap();
        totalCourse = courseMap.size();
        initializeComponents();
        setupLayout();
        setupEventListeners();
        loadCoursesFromMap();
        setupFrame();
    }

    private void setupFrame() {

        setTitle("Course List");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 700);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initializeComponents() {
        // Create table with column names
        String[] columnNames = {"Course ID", "Course Name", "Credits", "Student Capacity", "Current Students"};

        // Create models for all courses and filtered view
        allCoursesModel = new DefaultTableModel(columnNames, 0) {
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
        filteredCountLabel = new JLabel("Courses: 0 / 0");
        filteredCountLabel.setFont(new Font("Arial", Font.BOLD, 12));
        filteredCountLabel.setForeground(new Color(52, 152, 219));

        courseTable = new JTable(tableModel) {
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
        courseTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        courseTable.getTableHeader().setReorderingAllowed(false);
        courseTable.setRowHeight(24);
        courseTable.setFont(new Font("Arial", Font.PLAIN, 15));
        courseTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 16));
        courseTable.setFillsViewportHeight(true);
        courseTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        // Initialize input fields
        courseIdField = new JTextField(15);
        courseNameField = new JTextField(15);
        creditsField = new JTextField(15);
        maxStudentsField = new JTextField(15);
        
        // Initialize search panel components
        courseIdFilterField = new JTextField(15);
        courseIdFilterField.setToolTipText("Filter by Course ID");
        courseIdFilterField.setPreferredSize(new Dimension(100, 25));
        courseIdFilterField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                filterCourses();
            }
        });
        
        courseNameFilterField = new JTextField(15);
        courseNameFilterField.setToolTipText("Filter by Course Name");
        courseNameFilterField.setPreferredSize(new Dimension(150, 25));
        courseNameFilterField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                filterCourses();
            }
        });
        
        creditsFilterField = new JTextField(15);
        creditsFilterField.setToolTipText("Filter by minimum credits");
        creditsFilterField.setPreferredSize(new Dimension(80, 25));
        creditsFilterField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                filterCourses();
            }
        });
        
        maxStudentsFilterField = new JTextField(15);
        maxStudentsFilterField.setToolTipText("Filter by minimum capacity");
        maxStudentsFilterField.setPreferredSize(new Dimension(80, 25));
        maxStudentsFilterField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                filterCourses();
            }
        });
        
        // Department filter combo box
        String[] departmentFilters = Utils.DEPARTMENTS;
        departmentFilterComboBox = new JComboBox<>(departmentFilters);
        departmentFilterComboBox.setToolTipText("Filter by department");
        departmentFilterComboBox.addActionListener(e -> filterCourses());
        
        // Initialize buttons
        addButton = new JButton("Add Course");
        updateButton = new JButton("Update Course");
        deleteButton = new JButton("Delete Course");
        clearButton = new JButton("Clear");
        backButton = new JButton("Back");
        
        // Initialize course enrollment button with current status
        courseButton = new JButton(Utils.isCourseEnrollmentEnabled() ? "Disable Course Enrollment" : "Enable Course Enrollment");

        // Style buttons
        Utils.styleButton(addButton);
        Utils.styleButton(updateButton);
        Utils.styleButton(deleteButton);
        Utils.styleButton(clearButton);
        Utils.styleButton(backButton);
        
        // Style course enrollment button with appropriate color
        Utils.styleButton(courseButton);
        if (Utils.isCourseEnrollmentEnabled()) {
            courseButton.setBackground(new Color(220, 53, 69)); // Red for disable
            courseButton.setToolTipText("Click to disable course enrollment for students");
        } else {
            courseButton.setBackground(new Color(40, 167, 69)); // Green for enable
            courseButton.setToolTipText("Click to enable course enrollment for students");
        }
    }

    private void setupLayout() {

        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(245, 247, 250));

        // Create main panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(new Color(245, 247, 250));

        // Create top panel with search filters
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        // Enhanced filter panel
        JPanel filterPanel = createEnhancedFilterPanel();
        topPanel.add(filterPanel, BorderLayout.CENTER);

        mainPanel.add(topPanel, BorderLayout.NORTH);

        // Create center panel with table and input form
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBorder(BorderFactory.createEmptyBorder(0, 32, 20, 32));

        // Table panel
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(10, 20, 10, 10),
                BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)), "Course List", 0, 0, new Font("Arial", Font.BOLD, 16), new Color(52, 73, 94))
        ));
        tablePanel.setBackground(Color.WHITE);
        tablePanel.add(new JScrollPane(courseTable), BorderLayout.CENTER);

        // Input panel
        JPanel inputPanel = createInputPanel();

        centerPanel.add(tablePanel, BorderLayout.CENTER);
        centerPanel.add(inputPanel, BorderLayout.EAST);

        mainPanel.add(centerPanel, BorderLayout.CENTER);

        add(mainPanel, BorderLayout.CENTER);
        
        // Button panel at bottom
        JPanel buttonPanel = createButtonPanel();
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private JPanel createButtonPanel() {

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(new Color(245, 247, 250));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        buttonPanel.add(courseButton);
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(backButton);
        
        return buttonPanel;
    }

    private JPanel createEnhancedFilterPanel() {
        JPanel filterPanel = new JPanel(new GridBagLayout());
        filterPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(24, 32, 24, 32),
                BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)), "Filter:", 0, 0, new Font("Arial", Font.BOLD, 16), new Color(52, 73, 94))
        ));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 1, 5, 1);
        gbc.anchor = GridBagConstraints.CENTER;

        gbc.gridx = 0; gbc.gridy = 0;
        filterPanel.add(new JLabel("Course ID:"), gbc);
        gbc.gridx++;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0.2;
        filterPanel.add(courseIdFilterField, gbc);

        gbc.gridx++;
        gbc.weightx = 0;
        filterPanel.add(new JLabel("Course Name:"), gbc);
        gbc.gridx++;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0.3;
        filterPanel.add(courseNameFilterField, gbc);

        gbc.gridx++;
        gbc.weightx = 0;
        filterPanel.add(new JLabel("Min Credits:"), gbc);
        gbc.gridx++;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0.2;
        filterPanel.add(creditsFilterField, gbc);

        gbc.gridx++;
        gbc.weightx = 0;
        filterPanel.add(new JLabel("Min Capacity:"), gbc);
        gbc.gridx++;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0.2;
        filterPanel.add(maxStudentsFilterField, gbc);

        gbc.gridx++;
        gbc.weightx = 0;
        filterPanel.add(new JLabel("Department:"), gbc);
        gbc.gridx++;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0.2;
        filterPanel.add(departmentFilterComboBox, gbc);

        gbc.gridx++;
        gbc.gridheight = 1;
        filterPanel.add(filteredCountLabel, gbc);

        return filterPanel;
    }

    private JPanel createInputPanel() {

        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(10, 10, 10, 20),
                BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)), "Edit Information", 0, 0, new Font("Arial", Font.BOLD, 16), new Color(52, 73, 94))
        ));
        inputPanel.setPreferredSize(new Dimension(360, 400));
        inputPanel.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Course Information Section
        JLabel courseLabel = new JLabel("Course Information");
        courseLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
        courseLabel.setForeground(new Color(70, 130, 180));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        inputPanel.add(courseLabel, gbc);

        // Course ID field
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy++;
        inputPanel.add(new JLabel("Course ID:"), gbc);
        gbc.gridx = 1;
        courseIdField.setPreferredSize(new Dimension(220, 25));
        inputPanel.add(courseIdField, gbc);

        // Course Name field
        gbc.gridx = 0;
        gbc.gridy++;
        inputPanel.add(new JLabel("Course Name:"), gbc);
        gbc.gridx = 1;
        courseNameField.setPreferredSize(new Dimension(220, 25));
        inputPanel.add(courseNameField, gbc);

        // Credits field
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy++;
        inputPanel.add(new JLabel("Credits:"), gbc);
        gbc.gridx = 1;
        creditsField.setPreferredSize(new Dimension(220, 25));
        inputPanel.add(creditsField, gbc);

        // Max Students field
        gbc.gridx = 0;
        gbc.gridy++;
        inputPanel.add(new JLabel("Max Students:"), gbc);
        gbc.gridx = 1;
        maxStudentsField.setPreferredSize(new Dimension(220, 25));
        inputPanel.add(maxStudentsField, gbc);

        // total courses
        totalCourseLabel = new JLabel("Total Courses: " + totalCourse);
        totalCourseLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
        totalCourseLabel.setForeground(new Color(70, 130, 220));
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(15, 8, 8, 8);
        inputPanel.add(totalCourseLabel, gbc);

        // enrollment status
        enrollmentStatusLabel = new JLabel("Enrollment Status: " + (Utils.isCourseEnrollmentEnabled() ? "ENABLED" : "DISABLED"));
        enrollmentStatusLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
        enrollmentStatusLabel.setForeground(Utils.isCourseEnrollmentEnabled() ? new Color(40, 167, 69) : new Color(220, 53, 69));
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(8, 8, 8, 8);
        inputPanel.add(enrollmentStatusLabel, gbc);

        // Add some spacing at the bottom
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.weighty = 1.0;
        inputPanel.add(new JLabel(), gbc);

        return inputPanel;
    }

    private void setupEventListeners() {
        // Table selection listener
        courseTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                selectedRow = courseTable.getSelectedRow();
                if (selectedRow >= 0) {
                    populateFields();
                }
            }
        });

        // Button listeners
        courseButton.addActionListener(e -> courseEnrollment());
        addButton.addActionListener(e -> addCourse());
        updateButton.addActionListener(e -> updateCourse());
        deleteButton.addActionListener(e -> deleteCourse());
        clearButton.addActionListener(e -> clearFields());
        backButton.addActionListener(e -> {
            dispose();
            new AdministrationForm();
        });

    }

    private void courseEnrollment() {
        // Toggle the course enrollment status
        Utils.setCourseEnrollment(!Utils.isCourseEnrollmentEnabled());
        
        // Update button text and styling based on current status
        if (Utils.isCourseEnrollmentEnabled()) {
            courseButton.setText("Disable Course Enrollment");
            courseButton.setBackground(new Color(220, 53, 69)); // Red color for disable
            courseButton.setToolTipText("Click to disable course enrollment for students");
            
            // Update status label
            enrollmentStatusLabel.setText("Enrollment Status: ENABLED");
            enrollmentStatusLabel.setForeground(new Color(40, 167, 69));
            
            // Show confirmation message
            JOptionPane.showMessageDialog(this, 
                "Course enrollment has been ENABLED!\n\nStudents can now enroll in courses.", 
                "Course Enrollment Enabled", 
                JOptionPane.INFORMATION_MESSAGE);
            Main.getNotifications().add(new Notification("Course Enrollment Enabled"));
        } else {
            courseButton.setText("Enable Course Enrollment");
            courseButton.setBackground(new Color(40, 167, 69)); // Green color for enable
            courseButton.setToolTipText("Click to enable course enrollment for students");
            
            // Update status label
            enrollmentStatusLabel.setText("Enrollment Status: DISABLED");
            enrollmentStatusLabel.setForeground(new Color(220, 53, 69));
            
            // Show confirmation message
            JOptionPane.showMessageDialog(this, 
                "Course enrollment has been DISABLED!\n\nStudents cannot enroll in courses.", 
                "Course Enrollment Disabled", 
                JOptionPane.WARNING_MESSAGE);
            Main.getNotifications().add(new Notification("Course Enrollment Disabled"));
        }
        
        // Update button styling after text change
        Utils.styleButton(courseButton);
    }

    private void addCourse() {

        try {
            if (validateFields()) {
                String courseId = courseIdField.getText().trim();
                if (courseMap.containsKey(courseId)) {
                    JOptionPane.showMessageDialog(this, "Course ID already exists!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String courseName = courseNameField.getText().trim();
                int credits = Integer.parseInt(creditsField.getText().trim());
                int maxStudents = Integer.parseInt(maxStudentsField.getText().trim());

                Course newCourse = new Course(courseId, courseName, credits, maxStudents);
                courseMap.put(courseId, newCourse);
                loadCoursesFromMap();
                clearFields();
                totalCourseLabel.setText("Total Courses: " + totalCourse);
                JOptionPane.showMessageDialog(this, "Course added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(this, "Please enter valid numbers for credits and max students", "Input Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error adding course: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            System.err.println("Exception in CourseList.addCourse: " + ex.getMessage());
        }
    }

    private void updateCourse() {

        try {
            if (selectedRow < 0) {
                JOptionPane.showMessageDialog(this, "Please select a course to update", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (validateFields()) {
                String courseId = courseIdField.getText().trim();
                String courseName = courseNameField.getText().trim();
                int credits = Integer.parseInt(creditsField.getText().trim());
                int maxStudents = Integer.parseInt(maxStudentsField.getText().trim());

                Course course = courseMap.get(courseId);
                if (course != null) {
                    // Check if reducing max students would affect current enrollments
                    if (maxStudents < course.getCurrentStudents()) {
                        JOptionPane.showMessageDialog(this, 
                            "Cannot reduce max students below current enrollment (" + course.getCurrentStudents() + ")", 
                            "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    
                    course.setCourseName(courseName);
                    course.setCredits(credits);
                    course.setMaxStudents(maxStudents);
                    loadCoursesFromMap();
                    clearFields();
                    JOptionPane.showMessageDialog(this, "Course updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Course not found!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(this, "Please enter valid numbers for credits and max students", "Input Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error updating course: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            System.err.println("Exception in CourseList.updateCourse: " + ex.getMessage());
        }
    }

    private void deleteCourse() {

        try {
            if (selectedRow < 0) {
                JOptionPane.showMessageDialog(this, "Please select a course to delete", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String courseId = (String) tableModel.getValueAt(selectedRow, 0);
            Course course = courseMap.get(courseId);
            
            if (course == null) {
                JOptionPane.showMessageDialog(this, "Course not found!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Check if course has enrolled students
            if (course.getCurrentStudents() > 0) {
                int result = JOptionPane.showConfirmDialog(this, 
                    "Course has " + course.getCurrentStudents() + " enrolled students. Delete anyway?", 
                    "Confirm Delete", JOptionPane.YES_NO_OPTION);
                if (result != JOptionPane.YES_OPTION) {
                    return;
                }
            }
            
            int result = JOptionPane.showConfirmDialog(this, 
                "Are you sure you want to delete course: " + courseId + "?", 
                "Confirm Delete", JOptionPane.YES_NO_OPTION);
            
            if (result == JOptionPane.YES_OPTION) {
                courseMap.remove(courseId);
                loadCoursesFromMap();
                clearFields();
                totalCourseLabel.setText("Total Courses: " + totalCourse);
                JOptionPane.showMessageDialog(this, "Course deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error deleting course: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            System.err.println("Exception in CourseList.deleteCourse: " + ex.getMessage());
        }
    }

    private void clearFields() {
        courseIdField.setText("");
        courseNameField.setText("");
        creditsField.setText("");
        maxStudentsField.setText("");
        courseTable.clearSelection();
        selectedRow = -1;
    }

    private void populateFields() {
        if (selectedRow >= 0) {
            courseIdField.setText((String) tableModel.getValueAt(selectedRow, 0));
            courseNameField.setText((String) tableModel.getValueAt(selectedRow, 1));
            creditsField.setText(tableModel.getValueAt(selectedRow, 2).toString());
            maxStudentsField.setText(tableModel.getValueAt(selectedRow, 3).toString());
        }
    }

    private boolean validateFields() {
        if (courseIdField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Course ID is required", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (courseNameField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Course Name is required", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        try {
            int credits = Integer.parseInt(creditsField.getText().trim());
            if (credits <= 0) {
                JOptionPane.showMessageDialog(this, "Credits must be greater than 0", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Credits must be a valid number", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        try {
            int maxStudents = Integer.parseInt(maxStudentsField.getText().trim());
            if (maxStudents <= 0) {
                JOptionPane.showMessageDialog(this, "Max Students must be greater than 0", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Max Students must be a valid number", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    private void loadCoursesFromMap() {
        allCoursesModel.setRowCount(0);
        totalCourse = 0;
        
        for (Course course : courseMap.values()) {
            Vector<Object> row = new Vector<>();
            row.add(course.getCourseId());
            row.add(course.getCourseName());
            row.add(course.getCredits());
            row.add(course.getMaxStudents());
            row.add(course.getCurrentStudents());
            allCoursesModel.addRow(row);
            totalCourse++;
        }
        filterCourses();
    }

    private void filterCourses() {
        try {
            String courseIdFilter = courseIdFilterField.getText().trim().toLowerCase();
            String courseNameFilter = courseNameFilterField.getText().trim().toLowerCase();
            String creditsFilter = creditsFilterField.getText().trim();
            String maxStudentsFilter = maxStudentsFilterField.getText().trim();
            String selectedDept = departmentFilterComboBox.getSelectedItem().toString();

            tableModel.setRowCount(0);
            filteredCourseCount = 0;

            for (int i = 0; i < allCoursesModel.getRowCount(); i++) {
                String courseId = allCoursesModel.getValueAt(i, 0) != null ?
                        allCoursesModel.getValueAt(i, 0).toString().toLowerCase() : "";
                String courseName = allCoursesModel.getValueAt(i, 1) != null ?
                        allCoursesModel.getValueAt(i, 1).toString().toLowerCase() : "";
                String credits = allCoursesModel.getValueAt(i, 2) != null ?
                        allCoursesModel.getValueAt(i, 2).toString() : "";
                String maxStudents = allCoursesModel.getValueAt(i, 3) != null ?
                        allCoursesModel.getValueAt(i, 3).toString() : "";

                // Determine department from course ID (first 3 characters)
                String courseDept = courseId.length() >= 3 ? courseId.substring(0, 3).toUpperCase() : "";

                boolean idMatch = courseIdFilter.isEmpty() || courseId.contains(courseIdFilter);
                boolean nameMatch = courseNameFilter.isEmpty() || courseName.contains(courseNameFilter);
                boolean deptMatch = selectedDept.equals("Select") || courseDept.equals(selectedDept);

                boolean creditsMatch = true;
                if (!creditsFilter.isEmpty()) {
                    try {
                        int filterCredits = Integer.parseInt(creditsFilter);
                        int courseCredits = Integer.parseInt(credits);
                        creditsMatch = courseCredits >= filterCredits;
                    } catch (NumberFormatException e) {
                        creditsMatch = true; // If invalid number, don't filter
                    }
                }

                boolean capacityMatch = true;
                if (!maxStudentsFilter.isEmpty()) {
                    try {
                        int filterCapacity = Integer.parseInt(maxStudentsFilter);
                        int courseCapacity = Integer.parseInt(maxStudents);
                        capacityMatch = courseCapacity >= filterCapacity;
                    } catch (NumberFormatException e) {
                        capacityMatch = true; // If invalid number, don't filter
                    }
                }

                if (idMatch && nameMatch && deptMatch && creditsMatch && capacityMatch) {
                    Vector<Object> rowData = new Vector<>();
                    for (int j = 0; j < allCoursesModel.getColumnCount(); j++) {
                        rowData.add(allCoursesModel.getValueAt(i, j));
                    }
                    tableModel.addRow(rowData);
                    filteredCourseCount++;
                }
            }
            filteredCountLabel.setText("Courses: " + filteredCourseCount + " / " + totalCourse);
            courseTable.clearSelection();
            selectedRow = -1;
            clearFields();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error filtering courses: " + e.getMessage(),
                    "Filter Error", JOptionPane.ERROR_MESSAGE);
        }
    }
} 