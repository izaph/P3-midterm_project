import java.time.LocalDate;
import java.util.List;

public class MoodLog {
    private LocalDate date;
    private String mood;
    private List<String> activities;

    public MoodLog(LocalDate date, String mood, List<String> activities) {
        this.date = date;
        this.mood = mood;
        this.activities = activities;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getMood() {
        return mood;
    }

    public List<String> getActivities() {
        return activities;
    }

    @Override
    public String toString() {
        return "Date: " + date + ", Mood: " + mood + ", Activities: " + activities;
    }
}
