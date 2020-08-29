import java.util.ArrayList;
import java.util.Scanner;

public class Duke {
    private static int pendingTaskCount = 0;
    private static final int REQUIRED_NUMBER_OF_ARGUMENT_FOR_TODO = 2;
    private static final int REQUIRED_NUMBER_OF_ARGUMENT_FOR_EVENT = 4;
    private static final int REQUIRED_NUMBER_OF_ARGUMENT_FOR_DEADLINE = 4;
    private static final String HORIZONTAL_LINE =
            "____________________________________________________________";

    private static void addTask(ArrayList<Task> taskList, Task newTask) {
        pendingTaskCount++;
        taskList.add(newTask);
        System.out.println(" Gotcha! I've added this task: ");
        System.out.println("\t" + newTask.toString());
        System.out.println(" There's currently " + taskList.size() + " task(s) in the list." );
    }

    private static String[] parseUserInput(String userInput) {
        ArrayList<String> inputArguments = new ArrayList<>();
        String[] splitInput = userInput.split(" ");
        String userCommand = splitInput[0];
        inputArguments.add(userCommand);

        // If applicable, build the arguments: description and command option (at/by) information
        String commandOption = "";
        StringBuilder sbArgument = new StringBuilder();
        for (int i = 1; i < splitInput.length; i++) {
            if (commandOption.isEmpty() && (splitInput[i].equals("/by") || splitInput[i].equals("/at"))) {
                String description = sbArgument.toString().stripTrailing();
                commandOption = splitInput[i];
                inputArguments.add(description);
                inputArguments.add(commandOption);
                sbArgument.setLength(0);
                continue;
            }
            sbArgument.append(splitInput[i]).append(" ");
        }

        // Depending on user input, it could be description or task index or at/by information or empty
        String lastArgument = sbArgument.toString().stripTrailing();
        inputArguments.add(lastArgument);
        return inputArguments.toArray(new String[0]);
    }

    public static void main(String[] args) {
        System.out.println(HORIZONTAL_LINE);
        System.out.println(" Hello! I'm Duke");
        System.out.println(" What can I do for you?");
        System.out.println(HORIZONTAL_LINE);

        ArrayList<Task> taskList = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print(System.lineSeparator() + ">> ");
            String userInput = scanner.nextLine();
            System.out.println(HORIZONTAL_LINE);

            String[] inputArguments = parseUserInput(userInput);
            String userCommand = inputArguments[0];
            String description = inputArguments[1];
            switch (userCommand) {
            case "bye":
                System.out.println(" Bye-bye. Hope to see you again soon!");
                System.out.println(HORIZONTAL_LINE);
                return;
            case "list":
                if (taskList.size() == 0) {
                    System.out.println(" Uhh.. It's empty..");
                } else {
                    System.out.println(" Here are the tasks in your list: ");
                    for (int i = 0; i < taskList.size(); i++) {
                        Task task = taskList.get(i);
                        System.out.println("\t" + (i + 1) + ". " + task.toString());
                    }
                }
                break;
            case "done":
                int doneTaskIndex = Integer.parseInt(description) - 1;
                if (doneTaskIndex >= taskList.size() || doneTaskIndex <= -1) {
                    doneTaskIndex++;
                    System.out.println(" Uhh... There's no task numbered: " + doneTaskIndex);
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
            case "todo":
                if (inputArguments.length == REQUIRED_NUMBER_OF_ARGUMENT_FOR_TODO) {
                    if (!description.isBlank()) {
                        Todo newTodo = new Todo(description);
                        addTask(taskList, newTodo);
                        break;
                    }
                }

                System.out.println(" Failed to add new todo: check syntax and no blanks!");
                break;
            case "deadline":
                if (inputArguments.length == REQUIRED_NUMBER_OF_ARGUMENT_FOR_DEADLINE) {
                    String byOption = inputArguments[2];
                    String byInformation = inputArguments[3];
                    if (!description.isBlank() && byOption.equals("/by") && !byInformation.isBlank()) {
                        Deadline newDeadline = new Deadline(description, byInformation);
                        addTask(taskList, newDeadline);
                        break;
                    }
                }

                System.out.println(" Failed to add new deadline: check syntax and no blanks!");
                break;
            case "event":
                if (inputArguments.length == REQUIRED_NUMBER_OF_ARGUMENT_FOR_EVENT) {
                    String atOption = inputArguments[2];
                    String atInformation = inputArguments[3];
                    if (!description.isBlank() && atOption.equals("/at") && !atInformation.isBlank()) {
                        Event newEvent = new Event(description, atInformation);
                        addTask(taskList, newEvent);
                        break;
                    }
                }

                System.out.println(" Failed to add new event: check syntax and no blanks!");
                break;
            default:
                System.out.println(" Command \"" + userCommand + "\" is not recognized by Duke :(");
                break;
            }
            System.out.println(HORIZONTAL_LINE);
        }
    }
}