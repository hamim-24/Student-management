import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.text.SimpleDateFormat;
import java.util.Date;

public class utils {

    public static Map<String, Boolean> EXAM_CODE = new HashMap<>();
    public static Map<String, Long> EXAM_TIMESTAMPS = new HashMap<>();
    public static String PUBLISHED_STATUS = "No Exam is running";

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
            "Select", "1st Year", "2nd Year", "3rd Year", "4th Year"
    };



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
}