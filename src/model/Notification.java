package model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Notification {

    private final String note;
    private final SimpleDateFormat date;

    public Notification(String note) {
        this.note = note;
        this.date = new SimpleDateFormat("dd/MM, HH:mm:ss");
    }

    public String getNote() {
        return note;
    }
    public String getDate() {
        return date.format(new Date());
    }
}
