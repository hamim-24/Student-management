package ui;

import launcher.Main;
import model.Course;
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
    private JButton addButton, updateButton, deleteButton, clearButton, backButton;
    private int selectedRow = -1;
    JLabel totalCourseLabel;

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

        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

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

        // Initialize buttons
        addButton = new JButton("Add Course");
        updateButton = new JButton("Update Course");
        deleteButton = new JButton("Delete Course");
        clearButton = new JButton("Clear");
        backButton = new JButton("Back");

        // Style buttons
        Utils.styleButton(addButton);
        Utils.styleButton(updateButton);
        Utils.styleButton(deleteButton);
        Utils.styleButton(clearButton);
        Utils.styleButton(backButton);
    }

    private void setupLayout() {

        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(245, 247, 250));

        // Header
        JLabel headerLabel = new JLabel("Course Management", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 28));
        headerLabel.setBorder(BorderFactory.createEmptyBorder(24, 0, 24, 0));
        headerLabel.setForeground(new Color(44, 62, 80));
        add(headerLabel, BorderLayout.NORTH);

        // Main content panel
        JPanel mainPanel = new JPanel(new BorderLayout(20, 20));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(new Color(245, 247, 250));

        // Table panel
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(0, 0, 20, 0),
                BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)), "Course List", 0, 0, new Font("Arial", Font.BOLD, 16), new Color(52, 73, 94))
        ));
        tablePanel.setBackground(Color.WHITE);
        tablePanel.add(new JScrollPane(courseTable), BorderLayout.CENTER);

        // Input panel
        JPanel inputPanel = createInputPanel();

        mainPanel.add(tablePanel, BorderLayout.CENTER);
        mainPanel.add(inputPanel, BorderLayout.EAST);

        add(mainPanel, BorderLayout.CENTER);
        
        // Button panel at bottom
        JPanel buttonPanel = createButtonPanel();
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private JPanel createButtonPanel() {

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(new Color(245, 247, 250));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(backButton);
        
        return buttonPanel;
    }

    private JPanel createInputPanel() {

        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(10, 10, 0, 20),
                BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)), "Edit Information", 0, 0, new Font("Arial", Font.BOLD, 16), new Color(52, 73, 94))
        ));
        inputPanel.setPreferredSize(new Dimension(320, 400));
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
        gbc.insets = new Insets(8, 8, 8, 8);
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
        addButton.addActionListener(e -> addCourse());
        updateButton.addActionListener(e -> updateCourse());
        deleteButton.addActionListener(e -> deleteCourse());
        clearButton.addActionListener(e -> clearFields());
        backButton.addActionListener(e -> {
            dispose();
            new AdministrationForm();
        });
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
                totalCourse++;
                totalCourseLabel.setText("Total Courses: " + totalCourse);
                JOptionPane.showMessageDialog(this, "Course added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(this, "Please enter valid numbers for credits and max students", "Input Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error adding course: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            System.err.println("Exception in addCourse: " + ex.getMessage());
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
            System.err.println("Exception in updateCourse: " + ex.getMessage());
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
                totalCourse--;
                totalCourseLabel.setText("Total Courses: " + totalCourse);
                JOptionPane.showMessageDialog(this, "Course deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error deleting course: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            System.err.println("Exception in deleteCourse: " + ex.getMessage());
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
        tableModel.setRowCount(0);
        for (Course course : courseMap.values()) {
            Vector<Object> row = new Vector<>();
            row.add(course.getCourseId());
            row.add(course.getCourseName());
            row.add(course.getCredits());
            row.add(course.getMaxStudents());
            row.add(course.getCurrentStudents());
            tableModel.addRow(row);
        }
    }
} 