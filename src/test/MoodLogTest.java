import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;
import java.util.List;

public class MoodLogTest {

    @Test
    public void testMoodLogConstructor() {
        // Create a MoodLog
        LocalDate date = LocalDate.now();
        MoodLog log = new MoodLog(date, "Happy", List.of("Listen to music"));

        // Assert that the constructor works as expected
        assertEquals(date, log.getDate(), "Date should match the one provided to the constructor.");
        assertEquals("Happy", log.getMood(), "Mood should match the one provided to the constructor.");
        assertEquals(1, log.getActivities().size(), "Activities size should match the provided list.");
        assertTrue(log.getActivities().contains("Listen to music"), "Expected 'Listen to music' in the activities list.");
    }

    @Test
    public void testToString() {
        // Create a MoodLog and check the string output
        LocalDate date = LocalDate.now();
        MoodLog log = new MoodLog(date, "Happy", List.of("Listen to music"));

        String expected = "Date: " + date + ", Mood: Happy, Activities: [Listen to music]";
        assertEquals(expected, log.toString(), "toString() output should match the expected format.");
    }

    @Test
    public void testEmptyActivities() {
        // Create a MoodLog with no activities
        LocalDate date = LocalDate.now();
        MoodLog log = new MoodLog(date, "Relaxed", List.of());

        // Assert that the activities list is empty
        assertNotNull(log.getActivities(), "Activities list should not be null.");
        assertTrue(log.getActivities().isEmpty(), "Activities list should be empty.");
    }

    @Test
    public void testNullActivities() {
        // Create a MoodLog with a null activities list
        LocalDate date = LocalDate.now();
        List<String> nullActivities = null;
        assertThrows(NullPointerException.class, () -> new MoodLog(date, "Sad", nullActivities),
                "Creating a MoodLog with null activities should throw a NullPointerException.");
    }
}
