import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;



public class User {
    private List<MoodLog> moodHistory;
    private static final List<String> validMoods = List.of("Happy", "Sad", "Angry", "Relaxed","Depressed");

    public User() {
        this.moodHistory = new ArrayList<>();
    }

    public void addLog(MoodLog log) {
        moodHistory.add(log);
    }

    public void validateMood(String mood) throws InvalidMoodException {
        if (!validMoods.contains(mood)) {
            throw new InvalidMoodException("Invalid mood: " + mood);
        }
    }

    public List<MoodLog> getMoodHistory() {
        return moodHistory;
    }

    public void saveLogsToFile(String filePath) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (MoodLog log : moodHistory) {
                writer.write(log.getDate() + "," + log.getMood() + "," + String.join(";", log.getActivities()));
                writer.newLine();
            }
        }
    }

    public void loadLogsFromFile(String filePath) throws FileNotFoundException, IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            throw new FileNotFoundException("File not found: " + filePath);
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length != 3) {
                    throw new IllegalArgumentException("Invalid log format: " + line);
                }

                LocalDate date = LocalDate.parse(parts[0]);
                String mood = parts[1];
                List<String> activities = List.of(parts[2].split(";"));
                addLog(new MoodLog(date, mood, activities));
            }
        }
    }

    public void clearLogs(String filePath) throws IOException {
        moodHistory.clear();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
        }
    }
}