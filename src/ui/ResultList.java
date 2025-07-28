package ui;

import launcher.Main;
import model.*;
import util.utils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Map;
import java.util.Vector;

public class ResultList extends JFrame {

    private JTable resultTable;
    private DefaultTableModel tableModel;
    private JComboBox<String> resultCodeComboBox;
    private JTextField idFilterField, cgpaFilterField;
    private JButton clearFiltersButton, backButton;
    private JLabel filteredCountLabel;
    private JLabel departmentLabel, yearLabel;
    private JPanel infoPanel;

    Map<String, Result> resultMap;

    public ResultList() {

        this.resultMap = Main.getResultMap();
        setTitle("Exam Results");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(0, 0));

        // Title label
        JLabel titleLabel = new JLabel("Exam Results", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(24, 0, 12, 0));
        titleLabel.setForeground(new Color(44, 62, 80));
        add(titleLabel, BorderLayout.NORTH);

        // Table columns
        String[] columnNames = {"Roll", "Name", "ID", "Marks", "Correct", "Incorrect", "GPA"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        resultTable = new JTable(tableModel);
        resultTable.setRowHeight(26);
        resultTable.setFont(new Font("Arial", Font.PLAIN, 15));
        resultTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 16));
        resultTable.setFillsViewportHeight(true);
        resultTable.getTableHeader().setReorderingAllowed(false);
        JScrollPane scrollPane = new JScrollPane(resultTable);
        scrollPane.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(10, 10, 10, 10),
                BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)), "Student Results", 0, 0, new Font("Arial", Font.BOLD, 16), new Color(52, 73, 94))
        ));
        scrollPane.setPreferredSize(new Dimension(900, 400));
        JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
        verticalScrollBar.setUnitIncrement(resultTable.getRowHeight());
        scrollPane.getVerticalScrollBar().setUnitIncrement(resultTable.getRowHeight());
        scrollPane.setWheelScrollingEnabled(true);

        // Filter panel
        JPanel filterPanel = new JPanel(new GridBagLayout());
        filterPanel.setBackground(new Color(245, 247, 250));
        filterPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(16, 16, 8, 16),
                BorderFactory.createTitledBorder("Filter Results")));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 12, 5, 12);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridy = 0;
        gbc.gridx = 0;
        filterPanel.add(new JLabel("Result Code:"), gbc);
        gbc.gridx++;
        resultCodeComboBox = new JComboBox<>();
        resultCodeComboBox.setFont(new Font("Arial", Font.PLAIN, 15));
        resultCodeComboBox.addItem("All");
        resultCodeComboBox.removeAllItems();
        resultCodeComboBox.addItem("All");
        java.util.Set<String> uniqueResultCodes = new java.util.HashSet<>();
        for (Result result : Main.getResultMap().values()) {
            if (result.getQuestionCode() != null && !result.getQuestionCode().isEmpty()) {
                uniqueResultCodes.add(result.getQuestionCode());
            }
        }
        for (String code : uniqueResultCodes) {
            resultCodeComboBox.addItem(code);
        }
        filterPanel.add(resultCodeComboBox, gbc);
        gbc.gridx++;
        filterPanel.add(new JLabel("ID:"), gbc);
        gbc.gridx++;
        idFilterField = new JTextField(10);
        idFilterField.setFont(new Font("Arial", Font.PLAIN, 15));
        filterPanel.add(idFilterField, gbc);
        gbc.gridx++;
        filterPanel.add(new JLabel("Min CGPA:"), gbc);
        gbc.gridx++;
        cgpaFilterField = new JTextField(6);
        cgpaFilterField.setFont(new Font("Arial", Font.PLAIN, 15));
        filterPanel.add(cgpaFilterField, gbc);
        gbc.gridx++;
        filteredCountLabel = new JLabel();
        filteredCountLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        filterPanel.add(filteredCountLabel, gbc);

        // Info panel for department, year, total MCQ
        infoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 24, 6));
        infoPanel.setBackground(new Color(255, 255, 255));
        infoPanel.setPreferredSize(new Dimension(852, 50));
        infoPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220)),
                BorderFactory.createEmptyBorder(8, 24, 8, 24)));
        departmentLabel = new JLabel();
        departmentLabel.setFont(new Font("Arial", Font.BOLD, 15));
        yearLabel = new JLabel();
        yearLabel.setFont(new Font("Arial", Font.BOLD, 15));
        infoPanel.add(departmentLabel);
        infoPanel.add(yearLabel);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(new Color(245, 247, 250));
        clearFiltersButton = new JButton("Clear Filter");
        backButton = new JButton("Back");
        utils.styleButton(clearFiltersButton);
        utils.styleButton(backButton);
        buttonPanel.add(clearFiltersButton);
        buttonPanel.add(backButton);

        // Main content panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(new Color(245, 247, 250));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 24, 0));
        mainPanel.add(filterPanel);
        mainPanel.add(infoPanel);
        mainPanel.add(scrollPane);

        add(mainPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Listeners
        resultCodeComboBox.addActionListener(e -> filterResults());
        idFilterField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                filterResults();
            }
        });
        cgpaFilterField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                filterResults();
            }
        });
        clearFiltersButton.addActionListener(e -> clearAllFilters());
        backButton.addActionListener(e -> {
            this.dispose();
            new AdministrationForm();
        });

        // Initial load
        filterResults();
        setVisible(true);
    }

    private void clearAllFilters() {
        resultCodeComboBox.setSelectedIndex(0);
        idFilterField.setText("");
        cgpaFilterField.setText("");
        filterResults();
    }

    private void filterResults() {
        String selectedResultCode = (String) resultCodeComboBox.getSelectedItem();
        String idFilter = idFilterField.getText().trim().toLowerCase();
        String cgpaFilter = cgpaFilterField.getText().trim();
        double minCgpa = 0.0;
        try {
            if (!cgpaFilter.isEmpty()) minCgpa = Double.parseDouble(cgpaFilter);
        } catch (NumberFormatException e) {
            minCgpa = 0.0;
        }

        tableModel.setRowCount(0);
        int total = 0, filtered = 0;

        // Show department, year, totalMcq only if a specific result code is selected
        if (!selectedResultCode.equals("All")) {
            for (Result result : resultMap.values()) {
                if (selectedResultCode.equals(result.getQuestionCode())) {
                    departmentLabel.setText("Department: " + (result.getDepartment() != null ? result.getDepartment() : "") +
                            ",   Question Code: " + (result.getQuestionCode() != null ? result.getQuestionCode() : ""));
                    yearLabel.setText(" Year: " + (result.getYear() != null ? result.getYear() : "") +
                            ",   Total MCQ: " + result.getTotalQuestions() +
                            ",   Exam: " + (result.getQuestionName() != null ? result.getQuestionName() : ""));
                    break;
                }
            }
        } else {
            departmentLabel.setText("");
            yearLabel.setText("");
        }

        for (Result result : resultMap.values()) {
            String code = result.getQuestionCode();
            total += result.getCgpas().size();
            if (!selectedResultCode.equals("All") && (code == null || !code.equals(selectedResultCode))) {
                continue;
            }
            // For each student in this result
            for (int i = 0; i < result.getIDs().size(); i++) {
                String studentId = result.getIDs().get(i);
                String name = result.getNames().get(i);
                int roll = result.getRolls().get(i);
                double mark = result.getMarks().get(i);
                double cgpa = result.getCgpas().get(i);
                int correct = 0, incorrect = 0;
                try {
                    correct = result.getCorrect().get(i);
                    incorrect = result.getIncorrect().get(i);
                } catch (Exception ignored) {
                }

                if (!idFilter.isEmpty() && !studentId.toLowerCase().contains(idFilter)) continue;
                if (!cgpaFilter.isEmpty() && cgpa < minCgpa) continue;

                Vector<Object> row = new Vector<>();
                row.add(roll);
                row.add(name);
                row.add(studentId);
                row.add(mark);
                row.add(correct);
                row.add(incorrect);
                row.add(cgpa);
                tableModel.addRow(row);
                filtered++;
            }
        }
        filteredCountLabel.setText("Results: " + filtered + " / " + total + " (filtered/total)");
        // Set row background color for GPA < 2.0
        resultTable.setDefaultRenderer(Object.class, new javax.swing.table.DefaultTableCellRenderer() {
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
        resultTable.repaint();
    }
}