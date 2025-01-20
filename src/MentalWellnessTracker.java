import java.io.File;
import java.time.LocalDate;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.io.FileNotFoundException;


public class MentalWellnessTracker {
    public static void main(String[] args) {
        User user = new User();
        String filePath = "mood_logs.txt";
        Scanner scanner = new Scanner(System.in);
        File file = new File(filePath);

        // Display welcome message
        System.out.println("Welcome to Mental Wellness Tracker!");

        // Load logs if the file exists
        if (file.exists()) {
            try {
                user.loadLogsFromFile(filePath);
                if (user.getMoodHistory().isEmpty()) {
                    System.out.println("No logs registered yet.");
                } else {
                    System.out.println("Logs loaded from file!");
                }
            } catch (FileNotFoundException e) {
                System.err.println("File not found: " + e.getMessage());
            } catch (IllegalArgumentException e) {
                System.err.println("Invalid log format: " + e.getMessage());
            } catch (IOException e) {
                System.err.println("Error loading logs: " + e.getMessage());
            }
        } else {
            System.out.println("No existing logs found. Starting fresh.");
            System.out.println("No logs registered yet.");
        }

        // Interactive Menu
        while (true) {
            System.out.println("\nChoose an option:");
            System.out.println("1. View Logs");
            System.out.println("2. Add Log");
            System.out.println("3. Clear Logs");
            System.out.println("4. Activity Suggestion");
            System.out.println("5. Multithreaded Activity Suggestion");
            System.out.println("6. Exit");

            System.out.print("Enter your choice: ");
            int choice = 0;
            try {
                choice = scanner.nextInt();
            } catch (Exception e) {
                System.err.println("Invalid input. Please enter a valid number.");
                scanner.nextLine(); // Consume invalid input
                continue;
            }
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1: // View Logs
                    if (user.getMoodHistory().isEmpty()) {
                        System.out.println("\nWelcome to MentalWellnessTracker! No logs registered yet.");
                    } else {
                        System.out.println("\nCurrent Logs:");
                        for (MoodLog log : user.getMoodHistory()) {
                            System.out.println(log);
                        }
                    }
                    break;

                case 2: // Add Log
                    try {
                        System.out.print("Enter mood: ");
                        String mood = scanner.nextLine();
                        user.validateMood(mood); // Validate the mood using the custom exception

                        System.out.print("Enter activities (comma-separated): ");
                        String activitiesInput = scanner.nextLine();
                        List<String> activities = List.of(activitiesInput.split(","));

                        user.addLog(new MoodLog(LocalDate.now(), mood, activities));
                        System.out.println("Log added.");

                        // Save the updated log to the file immediately
                        user.saveLogsToFile(filePath);
                        System.out.println("Log saved to file.");
                    } catch (InvalidMoodException e) {
                        System.err.println(e.getMessage());
                        break; // Return to the menu after handling the exception
                    } catch (IOException e) {
                        System.err.println("Error saving log to file: " + e.getMessage());
                        break; // Return to the menu after handling the exception
                    }
                    break;

                case 3: // Clear Logs
                    try {
                        user.clearLogs(filePath);
                        System.out.println("All logs cleared!");
                    } catch (IOException e) {
                        System.err.println("Error clearing logs: " + e.getMessage());
                    }
                    break;

                case 4: // Activity Suggestion
                    System.out.print("Enter your current mood: ");
                    String mood = scanner.nextLine();
                    try {
                        user.validateMood(mood);
                        List<String> suggestions = ActivitySuggestion.suggestActivities(mood);
                        System.out.println("Here are some suggestions for your mood (" + mood + "):");
                        for (String suggestion : suggestions) {
                            System.out.println("- " + suggestion);
                        }
                    } catch (InvalidMoodException e) {
                        System.err.println(e.getMessage());
                    }
                    break;
                case 5: // Multithreaded Activity Suggestions
                    System.out.print("Enter moods (comma-separated): ");
                    String moodsInput = scanner.nextLine();
                    List<String> moods = List.of(moodsInput.split(","));
                    ActivitySuggestionMultithreaded.suggestActivitiesConcurrently(moods);
                    break;


                case 6: // Exit
                    try {
                        user.saveLogsToFile(filePath);
                        System.out.println("Logs saved to file. Exiting...");
                    } catch (IOException e) {
                        System.err.println("Error saving logs: " + e.getMessage());
                    }
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

}