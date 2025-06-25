import java.util.*;

public class FitnessTracker {
    private static List<Workout> workouts = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("\n🏋️ FITNESS TRACKER - Minimalist Workout Logger\n");
        boolean running = true;
        while (running) {
            System.out.println("\nChoose an option:");
            System.out.println("1. Add Workout");
            System.out.println("2. View Workouts");
            System.out.println("3. Exit");
            System.out.print("> ");
            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    addWorkout();
                    break;
                case "2":
                    viewWorkouts();
                    break;
                case "3":
                    running = false;
                    System.out.println("Goodbye! Stay fit! 💪");
                    break;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    private static void addWorkout() {
        System.out.print("Date (YYYY-MM-DD): ");
        String date = scanner.nextLine();
        System.out.print("Workout Type (e.g., Cardio, Strength): ");
        String type = scanner.nextLine();
        System.out.print("Duration (minutes): ");
        int duration = Integer.parseInt(scanner.nextLine());
        System.out.print("Notes: ");
        String notes = scanner.nextLine();
        workouts.add(new Workout(date, type, duration, notes));
        System.out.println("Workout added!");
    }

    private static void viewWorkouts() {
        if (workouts.isEmpty()) {
            System.out.println("No workouts logged yet.");
        } else {
            System.out.println("\n--- Workout Log ---");
            for (Workout w : workouts) {
                System.out.println(w);
            }
        }
    }
}
