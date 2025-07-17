import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class StudentManagementSystem extends JFrame {
    private JTextField nameField, rollField, marksField, classField, phoneField;
    private JTable studentTable;
    private DefaultTableModel tableModel;
    private List<Student> students;
    private int selectedRow = -1;

    public StudentManagementSystem() {
        students = new ArrayList<>();
        initializeGUI();
    }

    private void initializeGUI() {
        setTitle("Student Information Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create main panels
        JPanel inputPanel = createInputPanel();
        JPanel buttonPanel = createButtonPanel();
        JPanel tablePanel = createTablePanel();

        // Add panels to frame
        add(inputPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        add(tablePanel, BorderLayout.SOUTH);

        setSize(800, 600);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JPanel createInputPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Student Information"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Name field
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Name:"), gbc);
        gbc.gridx = 1;
        nameField = new JTextField(15);
        panel.add(nameField, gbc);

        // Roll field
        gbc.gridx = 2; gbc.gridy = 0;
        panel.add(new JLabel("Roll No:"), gbc);
        gbc.gridx = 3;
        rollField = new JTextField(15);
        panel.add(rollField, gbc);

        // Marks field
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Marks:"), gbc);
        gbc.gridx = 1;
        marksField = new JTextField(15);
        panel.add(marksField, gbc);

        // Class field
        gbc.gridx = 2; gbc.gridy = 1;
        panel.add(new JLabel("Class:"), gbc);
        gbc.gridx = 3;
        classField = new JTextField(15);
        panel.add(classField, gbc);

        // Phone field
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("Phone:"), gbc);
        gbc.gridx = 1;
        phoneField = new JTextField(15);
        panel.add(phoneField, gbc);

        return panel;
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout());

        JButton addButton = new JButton("Add Student");
        JButton updateButton = new JButton("Update Student");
        JButton deleteButton = new JButton("Delete Student");
        JButton clearButton = new JButton("Clear Fields");

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addStudent();
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateStudent();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteStudent();
            }
        });

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearFields();
            }
        });

        panel.add(addButton);
        panel.add(updateButton);
        panel.add(deleteButton);
        panel.add(clearButton);

        return panel;
    }

    private JPanel createTablePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Student List"));

        String[] columnNames = {"Name", "Roll No", "Marks", "Class", "Phone"};
        tableModel = new DefaultTableModel(columnNames, 0);
        studentTable = new JTable(tableModel);
        studentTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Add selection listener
        studentTable.getSelectionModel().addListSelectionListener(e -> {
            selectedRow = studentTable.getSelectedRow();
            if (selectedRow != -1) {
                populateFields(selectedRow);
            }
        });

        JScrollPane scrollPane = new JScrollPane(studentTable);
        scrollPane.setPreferredSize(new Dimension(750, 250));
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private void addStudent() {
        if (validateFields()) {
            Student student = new Student(
                    nameField.getText().trim(),
                    rollField.getText().trim(),
                    Double.parseDouble(marksField.getText().trim()),
                    classField.getText().trim(),
                    phoneField.getText().trim()
            );

            // Check if roll number already exists
            for (Student s : students) {
                if (s.getRollNo().equals(student.getRollNo())) {
                    JOptionPane.showMessageDialog(this, "Roll number already exists!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            students.add(student);
            updateTable();
            clearFields();
            JOptionPane.showMessageDialog(this, "Student added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void updateStudent() {
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a student to update!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (validateFields()) {
            Student student = students.get(selectedRow);
            String newRollNo = rollField.getText().trim();

            // Check if new roll number conflicts with existing ones (excluding current student)
            for (int i = 0; i < students.size(); i++) {
                if (i != selectedRow && students.get(i).getRollNo().equals(newRollNo)) {
                    JOptionPane.showMessageDialog(this, "Roll number already exists!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            student.setName(nameField.getText().trim());
            student.setRollNo(newRollNo);
            student.setMarks(Double.parseDouble(marksField.getText().trim()));
            student.setClassName(classField.getText().trim());
            student.setPhone(phoneField.getText().trim());

            updateTable();
            clearFields();
            JOptionPane.showMessageDialog(this, "Student updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void deleteStudent() {
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a student to delete!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this student?",
                "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            students.remove(selectedRow);
            updateTable();
            clearFields();
            selectedRow = -1;
            JOptionPane.showMessageDialog(this, "Student deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void clearFields() {
        nameField.setText("");
        rollField.setText("");
        marksField.setText("");
        classField.setText("");
        phoneField.setText("");
        selectedRow = -1;
        studentTable.clearSelection();
    }

    private void populateFields(int row) {
        if (row >= 0 && row < students.size()) {
            Student student = students.get(row);
            nameField.setText(student.getName());
            rollField.setText(student.getRollNo());
            marksField.setText(String.valueOf(student.getMarks()));
            classField.setText(student.getClassName());
            phoneField.setText(student.getPhone());
        }
    }

    private boolean validateFields() {
        if (nameField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Name cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (rollField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Roll number cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        try {
            double marks = Double.parseDouble(marksField.getText().trim());
            if (marks < 0 || marks > 100) {
                JOptionPane.showMessageDialog(this, "Marks should be between 0 and 100!", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter valid marks!", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (classField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Class cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

    private void updateTable() {
        tableModel.setRowCount(0);
        for (Student student : students) {
            Object[] row = {
                    student.getName(),
                    student.getRollNo(),
                    student.getMarks(),
                    student.getClassName(),
                    student.getPhone()
            };
            tableModel.addRow(row);
        }
    }

    public static void main(String[] args) {

            new StudentManagementSystem();

    }
}

// Student class to represent student data
class Student {
    private String name;
    private String rollNo;
    private double marks;
    private String className;
    private String phone;

    public Student(String name, String rollNo, double marks, String className, String phone) {
        this.name = name;
        this.rollNo = rollNo;
        this.marks = marks;
        this.className = className;
        this.phone = phone;
    }

    // Getters
    public String getName() { return name; }
    public String getRollNo() { return rollNo; }
    public double getMarks() { return marks; }
    public String getClassName() { return className; }
    public String getPhone() { return phone; }

    // Setters
    public void setName(String name) { this.name = name; }
    public void setRollNo(String rollNo) { this.rollNo = rollNo; }
    public void setMarks(double marks) { this.marks = marks; }
    public void setClassName(String className) { this.className = className; }
    public void setPhone(String phone) { this.phone = phone; }
}