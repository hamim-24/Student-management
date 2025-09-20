package util;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class Utils {

    public static final String promotion = "PROMOTION";
    public static Map<String, Boolean> EXAM_CODE = new HashMap<>();
    public static String PUBLISHED_STATUS = "No Exam is running";
    public static Boolean COURSE = false;
    
    /**
     * Check if course enrollment is currently enabled
     * @return true if enrollment is enabled, false otherwise
     */
    public static boolean isCourseEnrollmentEnabled() {
        return COURSE;
    }
    
    /**
     * Enable or disable course enrollment
     * @param enabled true to enable, false to disable
     */
    public static void setCourseEnrollment(boolean enabled) {
        COURSE = enabled;
    }

    public static final String DEFAULT_FILE_PATH = "No Path Selected";

    public static final String[] DEPARTMENTS = {
            "Select", "CSE", "EEE", "BBA", "Civil"
    };

    public static final String[] MONTHS = {
            "01", "02", "03", "04", "05", "06",
            "07", "08", "09", "10", "11", "12"
    };

    public static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Za-z0-9+_.-]+@([A-Za-z0-9-]+\\.)+[A-Za-z]{2,}$"
    );

    public static final String[] YEARS = {
            "Select", "1st Year", "2nd Year", "3rd Year", "4th Year", "Graduated"
    };


    public static JScrollPane ScrollPanel(StringBuilder result) {
        JTextArea textArea = new JTextArea(result.toString());
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        textArea.setBackground(new Color(245, 247, 250));
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(400, 250));
        return scrollPane;
    }
    public static String[] session() {
        String[] sessions = new String[11];
        sessions[0] = "Select";
        int currentYear = LocalDate.now().getYear();

        for (int i = 1; i < 11; i++) {
            sessions[i] = String.format("%d-%d", currentYear - i, currentYear - i + 1);
        }

        return sessions;
    }

    public static void styleButton(JButton button) {

        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setBackground(new Color(52, 152, 219));
        button.setForeground(Color.BLACK);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(41, 128, 185)),
                BorderFactory.createEmptyBorder(8, 18, 8, 18)));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.addMouseListener(new java.awt.event.MouseAdapter() {

            public void mouseEntered(java.awt.event.MouseEvent evt) {

                button.setBackground(new Color(41, 128, 185));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {

                button.setBackground(new Color(52, 152, 219));
            }
        });
    }

    public static JLabel setHeader(JLabel headerLabel) {
        headerLabel.setFont(new Font("Arial", Font.BOLD, 28));
        headerLabel.setBorder(BorderFactory.createEmptyBorder(24, 0, 24, 0));
        headerLabel.setForeground(new Color(44, 62, 80));
        return headerLabel;
    }
}