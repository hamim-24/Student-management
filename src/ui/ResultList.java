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
        
        // Check if resultMap is null or empty
        if (resultMap == null || resultMap.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Result data not available", "Error", JOptionPane.ERROR_MESSAGE);
            new AdministrationForm();
            return;
        }
        
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
        
        try {
            java.util.Set<String> uniqueResultCodes = new java.util.HashSet<>();
            for (Result result : resultMap.values()) {
                if (result != null && result.getQuestionCode() != null && !result.getQuestionCode().isEmpty()) {
                    uniqueResultCodes.add(result.getQuestionCode());
                }
            }
            for (String code : uniqueResultCodes) {
                resultCodeComboBox.addItem(code);
            }
        } catch (Exception ex) {
            System.err.println("Error loading result codes: " + ex.getMessage());
            JOptionPane.showMessageDialog(this, "Error loading result codes: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
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
        try {
            if (resultCodeComboBox.getItemCount() > 0) {
                resultCodeComboBox.setSelectedIndex(0);
            }
            idFilterField.setText("");
            cgpaFilterField.setText("");
            filterResults();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error clearing filters: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            System.err.println("Exception in clearAllFilters: " + ex.getMessage());
        }
    }

    private void filterResults() {
        try {
            if (resultMap == null || resultMap.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No result data available", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String selectedResultCode = (String) resultCodeComboBox.getSelectedItem();
            if (selectedResultCode == null) {
                selectedResultCode = "All";
            }
            
            String idFilter = idFilterField.getText().trim().toLowerCase();
            String cgpaFilter = cgpaFilterField.getText().trim();
            double minCgpa = 0.0;
            
            try {
                if (!cgpaFilter.isEmpty()) {
                    minCgpa = Double.parseDouble(cgpaFilter);
                    if (minCgpa < 0.0 || minCgpa > 4.0) {
                        JOptionPane.showMessageDialog(this, "CGPA must be between 0.0 and 4.0", "Invalid Input", JOptionPane.WARNING_MESSAGE);
                        minCgpa = 0.0;
                    }
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid CGPA format. Please enter a valid number.", "Invalid Input", JOptionPane.WARNING_MESSAGE);
                minCgpa = 0.0;
            }

            tableModel.setRowCount(0);
            int total = 0, filtered = 0;

            // Show department, year, totalMcq only if a specific result code is selected
            if (!selectedResultCode.equals("All")) {
                for (Result result : resultMap.values()) {
                    if (result == null) continue;
                    if (selectedResultCode.equals(result.getQuestionCode())) {
                        departmentLabel.setText("Department: " + (result.getDepartment() != null ? result.getDepartment() : "") +
                                ",  Code: " + (result.getQuestionCode() != null ? result.getQuestionCode() : ""));
                        yearLabel.setText("Year: " + (result.getYear() != null ? result.getYear() : "") +
                                ",  Total MCQ: " + result.getTotalQuestions() +
                                ",  Exam: " + (result.getQuestionName() != null ? result.getQuestionName() : "") +
                                ",  Session: " + (result.getSession() != null ? result.getSession() : "") +
                                ",  Course ID: " + (result.getCourseId() != null ? result.getCourseId() : ""));
                        break;
                    }
                }
            } else {
                departmentLabel.setText("");
                yearLabel.setText("");
            }

            for (Result result : resultMap.values()) {
                if (result == null) continue;
                String code = result.getQuestionCode();
                if (code == null) continue;
                
                // Validate that all lists have the same size
                int listSize = result.getCgpas().size();
                if (result.getIDs().size() != listSize || 
                    result.getNames().size() != listSize || 
                    result.getRolls().size() != listSize || 
                    result.getMarks().size() != listSize) {
                    System.err.println("Data inconsistency detected for result code: " + code);
                    continue;
                }
                
                total += listSize;
                if (!selectedResultCode.equals("All") && !code.equals(selectedResultCode)) {
                    continue;
                }
                
                // For each student in this result
                for (int i = 0; i < listSize; i++) {
                    try {
                        String studentId = result.getIDs().get(i);
                        String name = result.getNames().get(i);
                        Integer roll = result.getRolls().get(i);
                        Double mark = result.getMarks().get(i);
                        Double cgpa = result.getCgpas().get(i);
                        
                        // Validate data
                        if (studentId == null || name == null || roll == null || mark == null || cgpa == null) {
                            System.err.println("Null data found for student at index " + i + " in result code: " + code);
                            continue;
                        }
                        
                        int correct = 0, incorrect = 0;
                        
                        try {
                            if (i < result.getCorrect().size() && i < result.getIncorrect().size()) {
                                Integer correctVal = result.getCorrect().get(i);
                                Integer incorrectVal = result.getIncorrect().get(i);
                                correct = correctVal != null ? correctVal : 0;
                                incorrect = incorrectVal != null ? incorrectVal : 0;
                            }
                        } catch (Exception ignored) {
                            // Use default values if correct/incorrect data is missing
                            correct = 0;
                            incorrect = 0;
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
                    } catch (IndexOutOfBoundsException e) {
                        System.err.println("Index out of bounds for student at index " + i + " in result code: " + code);
                        continue;
                    } catch (Exception e) {
                        System.err.println("Error processing student at index " + i + " in result code: " + code + ": " + e.getMessage());
                        continue;
                    }
                }
            }
            
            filteredCountLabel.setText("Results: " + filtered + " / " + total + " (filtered/total)");
            
            // Show message if no results found
            if (filtered == 0) {
                JOptionPane.showMessageDialog(this, "No results found matching the current filters.", "No Results", JOptionPane.INFORMATION_MESSAGE);
            }
            
            // Set row background color for GPA < 2.0
            resultTable.setDefaultRenderer(Object.class, new javax.swing.table.DefaultTableCellRenderer() {
                @Override
                public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                    Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                    try {
                        if (column == 6 && value != null) {
                            double cgpa = Double.parseDouble(value.toString());
                            if (cgpa < 2.0) {
                                c.setBackground(new Color(255, 204, 204)); // Softer red
                            } else if (!isSelected) {
                                c.setBackground(row % 2 == 0 ? new Color(245, 245, 245) : Color.WHITE);
                            }
                        } else if (!isSelected) {
                            c.setBackground(row % 2 == 0 ? new Color(245, 245, 245) : Color.WHITE);
                        }
                    } catch (NumberFormatException e) {
                        if (!isSelected) {
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
            
        } catch (NullPointerException ex) {
            JOptionPane.showMessageDialog(this, "Null pointer error while filtering results: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            System.err.println("NullPointerException in filterResults: " + ex.getMessage());
        } catch (IndexOutOfBoundsException ex) {
            JOptionPane.showMessageDialog(this, "Index out of bounds error while filtering results: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            System.err.println("IndexOutOfBoundsException in filterResults: " + ex.getMessage());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error filtering results: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            System.err.println("Exception in filterResults: " + ex.getMessage());
        }
    }
}