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
        Scanner sc = new Scanner(System.in);
        int taskLeft = 0;
        while (true) {
            System.out.print(System.lineSeparator() + ">> ");
            String input = sc.nextLine();
            System.out.println(HORIZONTAL_LINE);

            String[] cmdArgs = input.split(" ");
            switch (cmdArgs[0]) {
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
                int index = Integer.parseInt(cmdArgs[1]) - 1;
                if (index >= taskList.size() || index <= -1) {
                    System.out.println("Uhh... There's no task numbered: " + index);
                    break;
                } else if (taskList.get(index).getStatus()) {
                    System.out.println(" Remember? You have completed this task already.");
                } else {
                    taskLeft--;
                    taskList.get(index).markAsDone();
                    if (taskLeft == 0) {
                        System.out.println(" Awesome!! You are all caught up :)");
                    } else {
                        System.out.println(" Awesome!! Just " + taskLeft + " more task(s) to go!");
                    }
                }
                System.out.println("\t" + taskList.get(index).toString());
                break;
            default:
                taskLeft++;
                Task newTask = new Task(input);
                taskList.add(newTask);
                System.out.println(" Added: " + input);
                break;
            }
            System.out.println(HORIZONTAL_LINE);
        }
    }
}