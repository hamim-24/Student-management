package ui;

import launcher.Main;
import model.Result;
import util.Utils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Vector;

public class ResultList extends JFrame {

    private JTable resultTable;
    private DefaultTableModel tableModel, allResultsModel;
    private JComboBox<String> yearComboBox, departmentComboBox, sessionComboBox, examCodeComboBox;
    private JButton clearFiltersButton, backButton;
    private JLabel filteredCountLabel;

    public ResultList() {

        initializeComponents();
        setupLayout();
        setupEventListeners();
        loadResults();
        setupFrame();
    }

    private void setupFrame() {

        setTitle("Result List");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 700);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initializeComponents() {

        String[] columnNames = {"ID", "Roll", "Marks", "Incorrect", "Correct", "CG"};
        allResultsModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };

        resultTable = new JTable(tableModel) {
            @Override
            public Component prepareRenderer(javax.swing.table.TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);
                try {
                    Object cgObj = getValueAt(row, 5); // CG is column 5
                    if (cgObj != null && !cgObj.toString().isEmpty()) {
                        double cg = Double.parseDouble(cgObj.toString());
                        if (cg < 2.0) {
                            c.setBackground(new Color(255, 204, 204)); // Soft red
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
        resultTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        resultTable.setRowHeight(24);
        resultTable.setFont(new Font("Arial", Font.PLAIN, 15));
        resultTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 16));
        resultTable.setFillsViewportHeight(true);
        resultTable.getTableHeader().setReorderingAllowed(false);
        resultTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        filteredCountLabel = new JLabel("Results: 0 / 0 (filtered/total)");

        yearComboBox = new JComboBox<>(Utils.YEARS);
        yearComboBox.setToolTipText("Filter by year");

        departmentComboBox = new JComboBox<>(Utils.DEPARTMENTS);
        departmentComboBox.setToolTipText("Filter by department");

        // Use the updated session() which includes 'Select' as the first option
        sessionComboBox = new JComboBox<>(Utils.session());
        sessionComboBox.setToolTipText("Filter by session");

        // Collect all unique exam codes from results
        Vector<String> examCodes = new Vector<>();
        examCodes.add("Select");
        for (Result r : Main.getResultList()) {
            String code = r.getExamCode();
            if (!examCodes.contains(code)) examCodes.add(code);
        }
        examCodeComboBox = new JComboBox<>(examCodes);
        examCodeComboBox.setToolTipText("Filter by exam code");

        clearFiltersButton = new JButton("Reset Filters");
        Utils.styleButton(clearFiltersButton);

        backButton = new JButton("Back");
        Utils.styleButton(backButton);
        backButton.setToolTipText("Return to Administration Dashboard");
    }

    private void setupLayout() {

        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.add(Utils.setHeader(new JLabel("Result List", SwingConstants.CENTER)), BorderLayout.NORTH);
        // Filter panel
        JPanel filterPanel = new JPanel(new GridBagLayout());
        filterPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(24, 32, 24, 32),
                BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)), "Filter:", 0, 0, new Font("Arial", Font.BOLD, 16), new Color(52, 73, 94))
        ));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 10, 5);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0; gbc.gridy = 0;
        filterPanel.add(new JLabel("Year:"), gbc);
        gbc.gridx++;
        filterPanel.add(yearComboBox, gbc);

        gbc.gridx++;
        filterPanel.add(new JLabel("Department:"), gbc);
        gbc.gridx++;
        filterPanel.add(departmentComboBox, gbc);

        gbc.gridx++;
        filterPanel.add(new JLabel("Session:"), gbc);
        gbc.gridx++;
        filterPanel.add(sessionComboBox, gbc);

        gbc.gridx++;
        filterPanel.add(new JLabel("Exam Code:"), gbc);
        gbc.gridx++;
        filterPanel.add(examCodeComboBox, gbc);

        gbc.gridx++;
        filterPanel.add(filteredCountLabel, gbc);

        mainPanel.add(filterPanel, BorderLayout.NORTH);

        // Table panel
        JScrollPane scrollPane = new JScrollPane(resultTable);
        scrollPane.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(10, 10, 10, 10),
                BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)), "Result List", 0, 0, new Font("Arial", Font.BOLD, 16), new Color(52, 73, 94))
        ));
        scrollPane.setPreferredSize(new Dimension(900, 400));
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 20));
        buttonPanel.add(backButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private void setupEventListeners() {
        yearComboBox.addActionListener(e -> filterResults());
        departmentComboBox.addActionListener(e -> filterResults());
        sessionComboBox.addActionListener(e -> filterResults());
        examCodeComboBox.addActionListener(e -> filterResults());
        clearFiltersButton.addActionListener(e -> clearAllFilters());
        backButton.addActionListener(e -> {
            this.dispose();
            new AdministrationForm();
        });
    }

    private void clearAllFilters() {
        yearComboBox.setSelectedIndex(0);
        departmentComboBox.setSelectedIndex(0);
        sessionComboBox.setSelectedIndex(0);
        examCodeComboBox.setSelectedIndex(0);
        filterResults();
    }

    private void loadResults() {
        allResultsModel.setRowCount(0);
        for (Result r : Main.getResultList()) {
            Vector<Object> row = new Vector<>();
            row.add(r.getId());
            row.add(r.getRoll());
            row.add(r.getMark());
            row.add(r.getIncorrect());
            row.add(r.getCorrect());
            row.add(r.getCg());
            allResultsModel.addRow(row);
        }
        filterResults();
    }

    private void filterResults() {
        String selectedYear = yearComboBox.getSelectedItem().toString();
        String selectedDept = departmentComboBox.getSelectedItem().toString();
        String selectedSession = sessionComboBox.getSelectedItem().toString();
        String selectedExamCode = examCodeComboBox.getSelectedItem().toString();

        tableModel.setRowCount(0);
        int total = allResultsModel.getRowCount();
        int filtered = 0;

        for (int i = 0; i < total; i++) {
            String year = getValue(allResultsModel, i, 0, "year");
            String dept = getValue(allResultsModel, i, 0, "department");
            String session = getValue(allResultsModel, i, 0, "session");
            String examCode = getValue(allResultsModel, i, 0, "examCode");

            // Get actual Result object for this row
            Result r = Main.getResultList().get(i);

            boolean yearMatch = selectedYear.equals("Select") || selectedYear.equals(r.getYear());
            boolean deptMatch = selectedDept.equals("Select") || selectedDept.equals(r.getDepartment());
            boolean sessionMatch = selectedSession.equals("Select") || selectedSession.equals(r.getSession());
            boolean examCodeMatch = selectedExamCode.equals("Select") || selectedExamCode.equals(r.getExamCode());

            if (yearMatch && deptMatch && sessionMatch && examCodeMatch) {
                Vector<Object> row = new Vector<>();
                row.add(r.getId());
                row.add(r.getRoll());
                row.add(r.getMark());
                row.add(r.getIncorrect());
                row.add(r.getCorrect());
                row.add(r.getCg());
                tableModel.addRow(row);
                filtered++;
            }
        }
        filteredCountLabel.setText("Results: " + filtered + " / " + total + " (filtered/total)");
        resultTable.clearSelection();
    }

    private String getValue(DefaultTableModel model, int row, int col, String type) {
        return "";
    }
}