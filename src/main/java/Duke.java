import java.util.ArrayList;
import java.util.Scanner;

public class Duke {
    private static int pendingTaskCount = 0;

    private static final String COMMAND_BYE = "bye";
    private static final String COMMAND_LIST = "list";
    private static final String COMMAND_DONE = "done";
    private static final String COMMAND_TODO = "todo";
    private static final String COMMAND_DEADLINE = "deadline";
    private static final String COMMAND_EVENT = "event";
    private static final String HORIZONTAL_LINE =
            "____________________________________________________________";

    private static final int REQUIRED_NUMBER_OF_ARGUMENT_FOR_TODO = 2;
    private static final int REQUIRED_NUMBER_OF_ARGUMENT_FOR_EVENT = 4;
    private static final int REQUIRED_NUMBER_OF_ARGUMENT_FOR_DEADLINE = 4;

    private static void greetUser() {
        System.out.println(HORIZONTAL_LINE);
        System.out.println(" Hello! I'm Duke");
        System.out.println(" What can I do for you?");
        System.out.println(HORIZONTAL_LINE);
    }

    private static String[] parseUserInput(String userInput) {
        ArrayList<String> inputArguments = new ArrayList<>();
        String[] splitInput = userInput.split(" ");
        String userCommand = splitInput[0];
        inputArguments.add(userCommand);

        // Build arguments (if any): description and command option (at/by) information (only 1 is expected)
        String commandOption = "";
        boolean isFirstCommandOption;
        StringBuilder sbArgument = new StringBuilder();
        for (int i = 1; i < splitInput.length; i++) {
            isFirstCommandOption = commandOption.isEmpty() && splitInput[i].startsWith("/");
            if (isFirstCommandOption) {
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

    private static void addTask(ArrayList<Task> taskList, Task newTask) {
        pendingTaskCount++;
        taskList.add(newTask);
        System.out.println(" Gotcha! I've added this task: ");
        System.out.println("\t" + newTask.toString());
        System.out.println(" There's currently " + taskList.size() + " task(s) in the list." );
    }

    public static void main(String[] args) {
        greetUser();

        ArrayList<Task> taskList = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print(System.lineSeparator() + ">> ");
            String userInput = scanner.nextLine();
            System.out.println(HORIZONTAL_LINE);

            String[] inputArguments = parseUserInput(userInput);
            String userCommand = inputArguments[0];
            String description = inputArguments[1];
            boolean isValidDescription, isValidOption, isValidOptionInformation, isValidCommand;
            switch (userCommand) {
            case COMMAND_BYE:
                System.out.println(" Bye-bye. Hope to see you again soon!");
                System.out.println(HORIZONTAL_LINE);
                return;
            case COMMAND_LIST:
                if (taskList.isEmpty()) {
                    System.out.println(" Uhh.. It's empty..");
                } else {
                    System.out.println(" Here are the tasks in your list: ");
                    for (int i = 0; i < taskList.size(); i++) {
                        Task task = taskList.get(i);
                        System.out.println("\t" + (i + 1) + ". " + task.toString());
                    }
                }
                break;
            case COMMAND_DONE:
                int doneTaskIndex = Integer.parseInt(description) - 1;
                boolean isValidTaskIndex = doneTaskIndex < taskList.size() && doneTaskIndex >= 0;
                if (!isValidTaskIndex) {
                    doneTaskIndex++;
                    System.out.println(" Uhh... There's no task numbered: " + doneTaskIndex);
                    break;
                }

                Task taskToMark = taskList.get(doneTaskIndex);
                if (taskToMark.getStatus()) {
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
            case COMMAND_TODO:
                if (inputArguments.length == REQUIRED_NUMBER_OF_ARGUMENT_FOR_TODO) {
                    isValidDescription = !description.isBlank();
                    if (isValidDescription) {
                        Todo newTodo = new Todo(description);
                        addTask(taskList, newTodo);
                        break;
                    }
                }

                System.out.println(" Failed to add new todo: check syntax and no blanks!");
                break;
            case COMMAND_DEADLINE:
                if (inputArguments.length == REQUIRED_NUMBER_OF_ARGUMENT_FOR_DEADLINE) {
                    String byOption = inputArguments[2];
                    String byInformation = inputArguments[3];

                    isValidDescription = !description.isBlank();
                    isValidOption = byOption.equals("/by");
                    isValidOptionInformation = !byInformation.isBlank();
                    isValidCommand = isValidDescription && isValidOption && isValidOptionInformation;

                    if (isValidCommand) {
                        Deadline newDeadline = new Deadline(description, byInformation);
                        addTask(taskList, newDeadline);
                        break;
                    }
                }

                System.out.println(" Failed to add new deadline: check syntax and no blanks!");
                break;
            case COMMAND_EVENT:
                if (inputArguments.length == REQUIRED_NUMBER_OF_ARGUMENT_FOR_EVENT) {
                    String atOption = inputArguments[2];
                    String atInformation = inputArguments[3];

                    isValidDescription = !description.isBlank();
                    isValidOption = atOption.equals("/at");
                    isValidOptionInformation = !atInformation.isBlank();
                    isValidCommand = isValidDescription && isValidOption && isValidOptionInformation;

                    if (isValidCommand) {
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