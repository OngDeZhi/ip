import java.util.ArrayList;
import java.util.Scanner;

public class Duke {
    private static int pendingTaskCount = 0;
    private static boolean shouldExit = false;

    private static final String COMMAND_BYE = "bye";
    private static final String COMMAND_LIST = "list";
    private static final String COMMAND_DONE = "done";
    private static final String COMMAND_TODO = "todo";
    private static final String COMMAND_DEADLINE = "deadline";
    private static final String COMMAND_EVENT = "event";
    private static final String HORIZONTAL_LINE =
            "____________________________________________________________";

    private static final int REQUIRED_TODO_ARGUMENT_COUNT = 2;
    private static final int REQUIRED_DEADLINE_ARGUMENT_COUNT = 4;
    private static final int REQUIRED_EVENT_ARGUMENT_COUNT = 4;

    public static void main(String[] args) {
        greetUser();
        ArrayList<Task> taskList = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        while (!shouldExit) {
            System.out.print(System.lineSeparator() + ">> ");
            String userInput = scanner.nextLine();
            System.out.println(HORIZONTAL_LINE);

            String[] inputArguments = parseUserInput(userInput);
            String userCommand = inputArguments[0];
            switch (userCommand) {
            case COMMAND_BYE:
                byeCommand();
                break;
            case COMMAND_LIST:
                listCommand(taskList);
                break;
            case COMMAND_DONE:
                doneCommand(inputArguments, taskList);
                break;
            case COMMAND_TODO:
                todoCommand(inputArguments, taskList);
                break;
            case COMMAND_DEADLINE:
                deadlineCommand(inputArguments, taskList);
                break;
            case COMMAND_EVENT:
                eventCommand(inputArguments, taskList);
                break;
            default:
                unknownCommand(userCommand);
                break;
            }
            System.out.println(HORIZONTAL_LINE);
        }
    }

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
        StringBuilder sbArgument = new StringBuilder();
        for (int i = 1; i < splitInput.length; i++) {
            boolean isFirstCommandOption = commandOption.isEmpty() && splitInput[i].startsWith("/");
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

    private static void byeCommand() {
        System.out.println(" Bye-bye. Hope to see you again soon!");
        shouldExit = true;
    }

    private static void listCommand(ArrayList<Task> taskList) {
        if (taskList.isEmpty()) {
            System.out.println(" Uhh.. It's empty..");
            return;
        }

        System.out.println(" Here are the tasks in your list: ");
        for (int i = 0; i < taskList.size(); i++) {
            Task task = taskList.get(i);
            System.out.println("\t" + (i + 1) + ". " + task.toString());
        }
    }

    private static void doneCommand(String[] inputArguments, ArrayList<Task> taskList) {
        String doneTaskIndexInString = inputArguments[1];
        int doneTaskIndex = Integer.parseInt(doneTaskIndexInString) - 1;
        boolean isValidTaskIndex = doneTaskIndex < taskList.size() && doneTaskIndex >= 0;
        if (!isValidTaskIndex) {
            doneTaskIndex++;
            System.out.println(" Uhh... There's no task numbered: " + doneTaskIndex);
            return;
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
    }

    private static void todoCommand(String[] inputArguments, ArrayList<Task> taskList) {
        int argumentCount = inputArguments.length;
        if (argumentCount == REQUIRED_TODO_ARGUMENT_COUNT) {
            String description = inputArguments[1];
            boolean isValidDescription = !description.isBlank();
            if (isValidDescription) {
                Todo newTodo = new Todo(description);
                addTask(taskList, newTodo);
                return;
            }
        }

        System.out.println(" Failed to add new todo: check syntax and no blanks!");
    }

    private static void deadlineCommand(String[] inputArguments, ArrayList<Task> taskList) {
        int argumentCount = inputArguments.length;
        if (argumentCount == REQUIRED_DEADLINE_ARGUMENT_COUNT) {
            String description = inputArguments[1];
            String byOption = inputArguments[2];
            String byInformation = inputArguments[3];

            boolean isValidDescription = !description.isBlank();
            boolean isValidOption = byOption.equals("/by");
            boolean isValidOptionInformation = !byInformation.isBlank();
            boolean isValidCommand = isValidDescription && isValidOption && isValidOptionInformation;

            if (isValidCommand) {
                Deadline newDeadline = new Deadline(description, byInformation);
                addTask(taskList, newDeadline);
                return;
            }
        }

        System.out.println(" Failed to add new deadline: check syntax and no blanks!");
    }

    private static void eventCommand(String[] inputArguments, ArrayList<Task> taskList) {
        int argumentCount = inputArguments.length;
        if (argumentCount == REQUIRED_EVENT_ARGUMENT_COUNT) {
            String description = inputArguments[1];
            String atOption = inputArguments[2];
            String atInformation = inputArguments[3];

            boolean isValidDescription = !description.isBlank();
            boolean isValidOption = atOption.equals("/at");
            boolean isValidOptionInformation = !atInformation.isBlank();
            boolean isValidCommand = isValidDescription && isValidOption && isValidOptionInformation;

            if (isValidCommand) {
                Event newEvent = new Event(description, atInformation);
                addTask(taskList, newEvent);
                return;
            }
        }

        System.out.println(" Failed to add new event: check syntax and no blanks!");
    }

    private static void unknownCommand(String userCommand) {
        System.out.println(" Command \"" + userCommand + "\" is not recognized by Duke :(");
    }
}