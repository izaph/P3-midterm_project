import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActivitySuggestion {
    private static final Map<String, List<String>> moodToActivities = new HashMap<>();

    static {
        moodToActivities.put("Happy", List.of("Celebrate with friends", "Go for a walk", "Listen to music"));
        moodToActivities.put("Sad", List.of("Watch a movie", "Read a book", "Talk to a friend"));
        moodToActivities.put("Angry", List.of("Try meditation", "Do physical exercise", "Write in a journal"));
        moodToActivities.put("Relaxed", List.of("Do yoga", "Enjoy a hobby", "Take a nap"));
    }

    public static List<String> suggestActivities(String mood) {
        return moodToActivities.getOrDefault(mood, List.of("No suggestions available for this mood."));
    }
}