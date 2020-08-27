import java.util.ArrayList;
import java.util.Scanner;

public class Duke {
    private static final String HORIZONTAL_LINE =
            "____________________________________________________________";

    public static void main(String[] args) {
        System.out.println(HORIZONTAL_LINE);
        System.out.println(" Hello! I'm Duke");
        System.out.println(" What can I do for you?");
        System.out.println(HORIZONTAL_LINE);

        ArrayList<Task> taskList = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        int pendingTaskCount = 0;
        while (true) {
            System.out.print(System.lineSeparator() + ">> ");
            String userInput = scanner.nextLine();
            System.out.println(HORIZONTAL_LINE);

            String[] inputArguments = userInput.split(" ");
            switch (inputArguments[0]) {
            case "bye":
                System.out.println(" Bye-bye. Hope to see you again soon!");
                System.out.println(HORIZONTAL_LINE);
                return;
            case "list":
                System.out.println("Here are the tasks in your list: ");
                for (int i = 0; i < taskList.size(); i++) {
                    Task task = taskList.get(i);
                    System.out.println(" " + (i + 1) + ". " + task.toString());
                }
                break;
            case "done":
                int doneTaskIndex = Integer.parseInt(inputArguments[1]) - 1;
                if (doneTaskIndex >= taskList.size() || doneTaskIndex <= -1) {
                    System.out.println("Uhh... There's no task numbered: " + (doneTaskIndex + 1));
                    break;
                } else if (taskList.get(doneTaskIndex).getStatus()) {
                    System.out.println(" Remember? You have completed this task already.");
                } else {
                    pendingTaskCount--;
                    taskList.get(doneTaskIndex).markAsDone();
                    if (pendingTaskCount == 0) {
                        System.out.println(" Awesome!! You are all caught up :)");
                    } else {
                        System.out.println(" Awesome!! Just " + pendingTaskCount + " more task(s) to go!");
                    }
                }
                System.out.println("\t" + taskList.get(doneTaskIndex).toString());
                break;
            default:
                pendingTaskCount++;
                Task newTask = new Task(userInput);
                taskList.add(newTask);
                System.out.println(" Added: " + userInput);
                break;
            }
            System.out.println(HORIZONTAL_LINE);
        }
    }
}