import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    @Test
    public void testAddLog() {
        User user = new User();
        MoodLog log = new MoodLog(LocalDate.now(), "Happy", List.of("Celebrate with friends"));
        user.addLog(log);

        List<MoodLog> moodHistory = user.getMoodHistory();
        assertEquals(1, moodHistory.size(), "Mood history should contain one log.");
        assertEquals(log, moodHistory.get(0), "The added log should match the one in the mood history.");
    }

    @Test
    public void testValidateMood_ValidMood() {
        User user = new User();
        assertDoesNotThrow(() -> user.validateMood("Happy"), "Valid mood should not throw an exception.");
    }

    @Test
    public void testValidateMood_InvalidMood() {
        User user = new User();
        Exception exception = assertThrows(InvalidMoodException.class, () -> user.validateMood("Excited"));
        assertEquals("Invalid mood: Excited", exception.getMessage(), "Invalid mood should throw the correct exception.");
    }

    @Test
    public void testClearLogs() {
        User user = new User();
        user.addLog(new MoodLog(LocalDate.now(), "Sad", List.of("Watch a movie")));

        assertFalse(user.getMoodHistory().isEmpty(), "Mood history should not be empty before clearing.");
        user.clearLogs("testFile.txt");
        assertTrue(user.getMoodHistory().isEmpty(), "Mood history should be empty after clearing.");
    }

    @Test
    public void testSaveAndLoadLogs(@TempDir Path tempDir) throws IOException {
        User user = new User();
        MoodLog log1 = new MoodLog(LocalDate.now(), "Happy", List.of("Listen to music"));
        MoodLog log2 = new MoodLog(LocalDate.now(), "Sad", List.of("Watch a movie"));
        user.addLog(log1);
        user.addLog(log2);

        // Save to a temporary file
        File tempFile = tempDir.resolve("temp_mood_logs.txt").toFile();
        user.saveLogsToFile(tempFile.getAbsolutePath());

        // Create a new user instance and load logs from the file
        User newUser = new User();
        newUser.loadLogsFromFile(tempFile.getAbsolutePath());

        // Assert that the loaded logs match the original logs
        assertEquals(2, newUser.getMoodHistory().size(), "Loaded mood history size should match the saved size.");
        assertEquals(log1.toString(), newUser.getMoodHistory().get(0).toString(),
                "The first loaded log should match the original.");
        assertEquals(log2.toString(), newUser.getMoodHistory().get(1).toString(),
                "The second loaded log should match the original.");
    }

}

