import java.util.*;

public class Workout {
    private String date;
    private String type;
    private int duration; // in minutes
    private String notes;

    public Workout(String date, String type, int duration, String notes) {
        this.date = date;
        this.type = type;
        this.duration = duration;
        this.notes = notes;
    }

    public String getDate() { return date; }
    public String getType() { return type; }
    public int getDuration() { return duration; }
    public String getNotes() { return notes; }

    @Override
    public String toString() {
        return String.format("%s | %s | %d min | %s", date, type, duration, notes);
    }
}
