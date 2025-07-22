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
    private JLabel departmentLabel,  yearLabel;

    Map<String, Result> resultMap;

    Result result;

    public ResultList() {

        this.resultMap = Main.getResultMap();

        setTitle("Result List");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);

        departmentLabel = new JLabel();
        departmentLabel.setPreferredSize(new Dimension(100, 20));
        yearLabel = new JLabel();
        yearLabel.setPreferredSize(new Dimension(300, 20));

        // Table columns
        String[] columnNames = {"Roll", "Name", "ID", "Marks", "Correct", "Incorrect", "GPA"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        resultTable = new JTable(tableModel);
        resultTable.setRowHeight(24);
        resultTable.setFillsViewportHeight(true);
        resultTable.getTableHeader().setReorderingAllowed(false);
        JScrollPane scrollPane = new JScrollPane(resultTable);

        // Filter panel
        JPanel filterPanel = new JPanel(new GridBagLayout());
        filterPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(16, 16, 16, 16),
                BorderFactory.createTitledBorder("Filter Results")));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.anchor = GridBagConstraints.WEST;

        // Result code filter
        gbc.gridx = 0; gbc.gridy = 0;
        filterPanel.add(new JLabel("Result Code:"), gbc);
        gbc.gridx++;
        resultCodeComboBox = new JComboBox<>();
        resultCodeComboBox.addItem("All");
        // Populate ComboBox with unique result codes from Result objects
        resultCodeComboBox.removeAllItems();
        resultCodeComboBox.addItem("All");
        java.util.Set<String> uniqueResultCodes = new java.util.HashSet<>();
        for (Result result : Main.getResultMap().values()) {
            if (result.getResultCode() != null && !result.getResultCode().isEmpty()) {
                uniqueResultCodes.add(result.getResultCode());
            }
        }
        for (String code : uniqueResultCodes) {
            resultCodeComboBox.addItem(code);
        }
        filterPanel.add(resultCodeComboBox, gbc);

        // ID filter
        gbc.gridx++;
        filterPanel.add(new JLabel("ID:"), gbc);
        gbc.gridx++;
        idFilterField = new JTextField(10);
        filterPanel.add(idFilterField, gbc);

        // Min CGPA filter
        gbc.gridx++;
        filterPanel.add(new JLabel("Min CGPA:"), gbc);
        gbc.gridx++;
        cgpaFilterField = new JTextField(6);
        filterPanel.add(cgpaFilterField, gbc);

        // Filtered count label
        gbc.gridx++;
        filteredCountLabel = new JLabel();
        filterPanel.add(filteredCountLabel, gbc);

        gbc.gridy = 2;
        gbc.gridx = 0;
        filterPanel.add(departmentLabel, gbc);
        gbc.gridx++;
        filterPanel.add(yearLabel, gbc);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        clearFiltersButton = new JButton("Clear Filter");
        backButton = new JButton("Back");
        utils.styleButton(clearFiltersButton);
        utils.styleButton(backButton);
        buttonPanel.add(clearFiltersButton);
        buttonPanel.add(backButton);

        // Layout
        setLayout(new BorderLayout());
        add(filterPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Listeners
        resultCodeComboBox.addActionListener(e -> filterResults());
        idFilterField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) { filterResults(); }
        });
        cgpaFilterField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) { filterResults(); }
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
                if (selectedResultCode.equals(result.getResultCode())) {
                    departmentLabel.setText("Department: " + (result.getDepartment() != null ? result.getDepartment() : ""));
                    yearLabel.setText("Year: " + (result.getYear() != null ? result.getYear() : "") + ", Total MCQ: " + result.getTotalQuestions());
                    break;
                }
            }
        } else {
            departmentLabel.setText("");
            yearLabel.setText("");
        }

        for (Result result : resultMap.values()) {
            String code = result.getResultCode();
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
                } catch (Exception e) {}

                total++;
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