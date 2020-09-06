import java.util.ArrayList;
import java.util.Scanner;

public class Duke {
    private static int pendingTaskCount = 0;
    private static boolean shouldExit = false;
    private static final Scanner CONSOLE = new Scanner(System.in);
    private static final ArrayList<Task> taskList = new ArrayList<>();

    private static final String COMMAND_BYE = "bye";
    private static final String COMMAND_LIST = "list";
    private static final String COMMAND_DONE = "done";
    private static final String COMMAND_TODO = "todo";
    private static final String COMMAND_DEADLINE = "deadline";
    private static final String COMMAND_EVENT = "event";

    private static final String REQUIRED_DEADLINE_OPTION = "/by";
    private static final String REQUIRED_EVENT_OPTION = "/at";
    private static final String HORIZONTAL_LINE =
            "____________________________________________________________";

    private static final int REQUIRED_BYE_ARGUMENT_COUNT = 1;
    private static final int REQUIRED_LIST_ARGUMENT_COUNT = 1;
    private static final int REQUIRED_DONE_ARGUMENT_COUNT = 2;
    private static final int REQUIRED_TODO_ARGUMENT_COUNT = 2;
    private static final int REQUIRED_DEADLINE_ARGUMENT_COUNT = 4;
    private static final int REQUIRED_EVENT_ARGUMENT_COUNT = 4;

    public static void main(String[] args) {
        greetUser();
        while (!shouldExit) {
            try {
                String userInput = readUserInput();
                String[] inputArguments = parseUserInput(userInput);
                executeCommand(inputArguments);
            } catch (DukeException exception) {
                System.out.println("☹ OOPS!!!" + exception.getMessage());
            } catch (NumberFormatException exception) {
                System.out.println("☹ OOPS!!! The task index must be an integer.");
            } catch (IndexOutOfBoundsException exception) {
                System.out.println("☹ OOPS!!! Invalid task number received.");
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

    private static String readUserInput() {
        System.out.print(System.lineSeparator() + ">> ");
        String userInput = CONSOLE.nextLine();
        System.out.println(HORIZONTAL_LINE);
        return userInput;
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

        // The last argument could be a description, task index, at/by information or empty string
        String lastArgument = sbArgument.toString().stripTrailing();
        inputArguments.add(lastArgument);
        return inputArguments.toArray(new String[0]);
    }

    private static void executeCommand(String[] inputArguments) throws DukeException {
        String userCommand = inputArguments[0];
        switch (userCommand) {
        case COMMAND_BYE:
            validateCommand(REQUIRED_BYE_ARGUMENT_COUNT, inputArguments);
            byeCommand();
            break;
        case COMMAND_LIST:
            validateCommand(REQUIRED_LIST_ARGUMENT_COUNT, inputArguments);
            listCommand();
            break;
        case COMMAND_DONE:
            validateCommand(REQUIRED_DONE_ARGUMENT_COUNT, inputArguments);
            doneCommand(inputArguments);
            break;
        case COMMAND_TODO:
            validateCommand(REQUIRED_TODO_ARGUMENT_COUNT, inputArguments);
            todoCommand(inputArguments);
            break;
        case COMMAND_DEADLINE:
            validateCommand(REQUIRED_DEADLINE_ARGUMENT_COUNT, inputArguments);
            deadlineCommand(inputArguments);
            break;
        case COMMAND_EVENT:
            validateCommand(REQUIRED_EVENT_ARGUMENT_COUNT, inputArguments);
            eventCommand(inputArguments);
            break;
        default:
            throw new DukeException(" Command \"" + userCommand + "\" is not recognized by Duke :(");
        }
    }

    private static void addTask(Task newTask) {
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

    private static void listCommand() {
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

    private static void doneCommand(String[] inputArguments) {
        String doneTaskIndexString = inputArguments[1];
        int doneTaskIndex = Integer.parseInt(doneTaskIndexString) - 1;

        Task doneTask = taskList.get(doneTaskIndex);
        if (doneTask.getStatus()) {
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

    private static void todoCommand(String[] inputArguments) {
        String description = inputArguments[1];
        Todo newTodo = new Todo(description);
        addTask(newTodo);
    }

    private static void deadlineCommand(String[] inputArguments) {
        String description = inputArguments[1];
        String byInformation = inputArguments[3];
        Deadline newDeadline = new Deadline(description, byInformation);
        addTask(newDeadline);
    }

    private static void eventCommand(String[] inputArguments) {
        String description = inputArguments[1];
        String atInformation = inputArguments[3];
        Event newEvent = new Event(description, atInformation);
        addTask(newEvent);
    }

    private static void validateCommand(int requiredArgumentCount, String[] inputArguments) throws DukeException {
        String userCommand = inputArguments[0];
        String description = inputArguments[1];
        int argumentCount = inputArguments.length;
        boolean isValidDescription;

        if (userCommand.equals(COMMAND_BYE) || userCommand.equals(COMMAND_LIST)) {
            argumentCount--;
        }

        if (argumentCount > requiredArgumentCount) {
            throw new DukeException(" There's too many arguments for \"" + userCommand + "\".");
        } else if (argumentCount < requiredArgumentCount) {
            throw new DukeException(" There's too little arguments for \"" + userCommand + "\".");
        }

        switch(requiredArgumentCount) {
        case 1:
            isValidDescription = !description.isBlank();
            if (isValidDescription) {
                throw new DukeException(" There's no need for a description for \"" + userCommand + "\".");
            }

            break;
        case 2:
            isValidDescription = !description.isBlank();
            if (userCommand.equals(COMMAND_DONE) && !isValidDescription) {
                throw new DukeException(" The task index of \"done\" cannot be empty.");
            } else if (userCommand.equals(COMMAND_TODO) && !isValidDescription) {
                throw new DukeException(" The description of \"todo\" cannot be empty.");
            }

            break;
        case 4:
            String requiredCommandOption = "";
            String commandOption = inputArguments[2];
            String commandOptionInformation = inputArguments[3];

            if (userCommand.equals(COMMAND_DEADLINE)) {
                requiredCommandOption = REQUIRED_DEADLINE_OPTION;
            } else if (userCommand.equals(COMMAND_EVENT)) {
                requiredCommandOption = REQUIRED_EVENT_OPTION;
            }

            isValidDescription = !description.isBlank();
            boolean isValidOption = commandOption.equals(requiredCommandOption);
            boolean isValidOptionInformation = !commandOptionInformation.isBlank();

            if (!isValidDescription) {
                throw new DukeException(" The description of \"" + userCommand + "\" cannot be empty.");
            } else if (!isValidOption) {
                throw new DukeException(" The \"" + requiredCommandOption + "\" option is missing for \""
                        + userCommand + "\".");
            } else if (!isValidOptionInformation) {
                throw new DukeException(" The \"" + requiredCommandOption + "\" information is missing for \""
                        + userCommand + "\".");
            }

            break;
        default:
            break;
        }
    }
}