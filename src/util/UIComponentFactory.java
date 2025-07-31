package util;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class UIComponentFactory {

    // Color constants
    public static final Color PRIMARY_COLOR = new Color(44, 62, 80);
    public static final Color SECONDARY_COLOR = new Color(52, 152, 219);
    public static final Color BACKGROUND_COLOR = new Color(245, 247, 250);
    public static final Color WHITE_COLOR = new Color(255, 255, 255);
    public static final Color BORDER_COLOR = new Color(200, 200, 200);
    public static final Color TITLE_COLOR = new Color(52, 73, 94);
    public static final Color ERROR_COLOR = new Color(231, 76, 60);
    public static final Color SUCCESS_COLOR = new Color(46, 204, 113);
    public static final Color WARNING_COLOR = new Color(241, 196, 15);

    /**
     * Creates a styled title label
     */
    public static JLabel createTitleLabel(String text) {
        JLabel titleLabel = new JLabel(text, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(24, 0, 12, 0));
        titleLabel.setForeground(PRIMARY_COLOR);
        return titleLabel;
    }

    /**
     * Creates a styled button with consistent appearance
     */
    public static JButton createStyledButton(String text, String tooltip) {
        JButton button = new JButton(text);
        button.setToolTipText(tooltip);
        utils.styleButton(button);
        return button;
    }

    /**
     * Creates a styled text field with consistent appearance
     */
    public static JTextField createStyledTextField(int columns, String tooltip) {
        JTextField textField = new JTextField(columns);
        textField.setFont(new Font("Arial", Font.PLAIN, 15));
        textField.setToolTipText(tooltip);
        return textField;
    }

    /**
     * Creates a styled combo box with consistent appearance
     */
    public static JComboBox<String> createStyledComboBox(String[] items, String tooltip) {
        JComboBox<String> comboBox = new JComboBox<>(items);
        comboBox.setFont(new Font("Arial", Font.PLAIN, 15));
        comboBox.setToolTipText(tooltip);
        return comboBox;
    }

    /**
     * Creates a styled table with consistent appearance
     */
    public static JTable createStyledTable(DefaultTableModel model) {
        JTable table = new JTable(model);
        table.setRowHeight(24);
        table.setFont(new Font("Arial", Font.PLAIN, 15));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 16));
        table.setFillsViewportHeight(true);
        table.getTableHeader().setReorderingAllowed(false);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        return table;
    }

    /**
     * Creates a styled scroll pane with consistent appearance
     */
    public static JScrollPane createStyledScrollPane(JComponent component, String title) {
        JScrollPane scrollPane = new JScrollPane(component);
        scrollPane.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(10, 10, 10, 10),
                BorderFactory.createTitledBorder(
                    BorderFactory.createLineBorder(BORDER_COLOR), 
                    title, 0, 0, new Font("Arial", Font.BOLD, 16), TITLE_COLOR
                )
        ));
        return scrollPane;
    }

    /**
     * Creates a styled panel with consistent appearance
     */
    public static JPanel createStyledPanel(LayoutManager layout) {
        JPanel panel = new JPanel(layout);
        panel.setBackground(WHITE_COLOR);
        return panel;
    }

    /**
     * Creates a filter panel with consistent styling
     */
    public static JPanel createFilterPanel(String title) {
        JPanel filterPanel = new JPanel(new GridBagLayout());
        filterPanel.setBackground(BACKGROUND_COLOR);
        filterPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(16, 16, 8, 16),
                BorderFactory.createTitledBorder(title)
        ));
        return filterPanel;
    }

    /**
     * Creates a button panel with consistent styling
     */
    public static JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(BACKGROUND_COLOR);
        return buttonPanel;
    }

    /**
     * Creates a non-editable table model
     */
    public static DefaultTableModel createNonEditableTableModel(String[] columnNames) {
        return new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
    }

    /**
     * Creates a styled label with consistent appearance
     */
    public static JLabel createStyledLabel(String text, Font font, Color color) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        label.setForeground(color);
        return label;
    }

    /**
     * Creates a styled label with default appearance
     */
    public static JLabel createStyledLabel(String text) {
        return createStyledLabel(text, new Font("Arial", Font.PLAIN, 15), Color.BLACK);
    }
} 