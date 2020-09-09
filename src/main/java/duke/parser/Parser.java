package duke.parser;

import duke.command.AddCommand;
import duke.command.ByeCommand;
import duke.command.Command;
import duke.command.DoneCommand;
import duke.command.ListCommand;
import duke.exception.DukeException;
import duke.task.Deadline;
import duke.task.Event;
import duke.task.Todo;

import java.util.ArrayList;
import java.util.Scanner;

public class Parser {
    private static final String COMMAND_BYE = "bye";
    private static final String COMMAND_LIST = "list";
    private static final String COMMAND_DONE = "done";
    private static final String COMMAND_TODO = "todo";
    private static final String COMMAND_DEADLINE = "deadline";
    private static final String COMMAND_EVENT = "event";

    private static final String REQUIRED_DEADLINE_OPTION = "/by";
    private static final String REQUIRED_EVENT_OPTION = "/at";

    private static final int REQUIRED_BYE_ARGUMENT_COUNT = 1;
    private static final int REQUIRED_LIST_ARGUMENT_COUNT = 1;
    private static final int REQUIRED_DONE_ARGUMENT_COUNT = 2;
    private static final int REQUIRED_TODO_ARGUMENT_COUNT = 2;
    private static final int REQUIRED_DEADLINE_ARGUMENT_COUNT = 4;
    private static final int REQUIRED_EVENT_ARGUMENT_COUNT = 4;

    private static final Scanner CONSOLE = new Scanner(System.in);

    public static Command readUserInput() throws DukeException {
        String userInput = CONSOLE.nextLine();
        String[] inputArguments = parseUserInput(userInput);
        return generateCommand(inputArguments);
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

    private static Command generateCommand(String[] inputArguments) throws DukeException {
        String userCommand = inputArguments[0];
        String description = inputArguments[1];
        switch (userCommand) {
        case COMMAND_BYE:
            validateCommand(REQUIRED_BYE_ARGUMENT_COUNT, inputArguments);
            return new ByeCommand();
        case COMMAND_LIST:
            validateCommand(REQUIRED_LIST_ARGUMENT_COUNT, inputArguments);
            return new ListCommand();
        case COMMAND_DONE:
            validateCommand(REQUIRED_DONE_ARGUMENT_COUNT, inputArguments);
            return new DoneCommand(description);
        case COMMAND_TODO:
            validateCommand(REQUIRED_TODO_ARGUMENT_COUNT, inputArguments);
            Todo newTodo = new Todo(description);
            return new AddCommand(newTodo);
        case COMMAND_DEADLINE:
            validateCommand(REQUIRED_DEADLINE_ARGUMENT_COUNT, inputArguments);
            String byInformation = inputArguments[3];
            Deadline newDeadline = new Deadline(description, byInformation);
            return new AddCommand(newDeadline);
        case COMMAND_EVENT:
            validateCommand(REQUIRED_EVENT_ARGUMENT_COUNT, inputArguments);
            String atInformation = inputArguments[3];
            Event newEvent = new Event(description, atInformation);
            return new AddCommand(newEvent);
        default:
            throw new DukeException(" Command \"" + userCommand + "\" is not recognized by Duke :(");
        }
    }

    private static void validateCommand(int requiredArgumentCount, String[] inputArguments) throws DukeException {
        String userCommand = inputArguments[0];
        String description = inputArguments[1];
        int argumentCount = inputArguments.length;
        if (userCommand.equals(COMMAND_BYE) || userCommand.equals(COMMAND_LIST)) {
            argumentCount--;
        }

        if (argumentCount > requiredArgumentCount) {
            throw new DukeException(" There's too many arguments for \"" + userCommand + "\".");
        } else if (argumentCount < requiredArgumentCount) {
            throw new DukeException(" There's too little arguments for \"" + userCommand + "\".");
        }

        boolean isValidDescription;
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
                throw new DukeException(" The task number of \"done\" cannot be empty.");
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