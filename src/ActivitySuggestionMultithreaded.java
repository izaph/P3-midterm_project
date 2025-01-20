import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ActivitySuggestionMultithreaded {
    public static void suggestActivitiesConcurrently(List<String> moods) {
        ExecutorService executor = Executors.newFixedThreadPool(4); // Pool of 4 threads

        for (String mood : moods) {
            executor.execute(() -> {
                List<String> suggestions = ActivitySuggestion.suggestActivities(mood);
                System.out.println("Suggestions for " + mood + ": " + suggestions);
            });
        }

        executor.shutdown(); // Ensure all threads finish
    }
}