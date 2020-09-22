package duke.parser;

import duke.command.AddCommand;
import duke.command.ByeCommand;
import duke.command.Command;
import duke.command.DeleteCommand;
import duke.command.DoneCommand;
import duke.command.FindCommand;
import duke.command.DueCommand;
import duke.command.ListCommand;
import duke.exception.DukeException;
import duke.task.Deadline;
import duke.task.Event;
import duke.task.Task;
import duke.task.Todo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

public class Parser {
    private static final String COMMAND_BYE = "bye";
    private static final String COMMAND_LIST = "list";
    private static final String COMMAND_DONE = "done";
    private static final String COMMAND_DELETE = "delete";
    private static final String COMMAND_DUE = "due";
    private static final String COMMAND_FIND = "find";
    private static final String COMMAND_TODO = "todo";
    private static final String COMMAND_DEADLINE = "deadline";
    private static final String COMMAND_EVENT = "event";

    private static final String REQUIRED_DEADLINE_OPTION = "/by";
    private static final String REQUIRED_EVENT_OPTION = "/at";

    private static final int REQUIRED_BYE_ARGUMENT_COUNT = 1;
    private static final int REQUIRED_LIST_ARGUMENT_COUNT = 1;
    private static final int REQUIRED_DONE_ARGUMENT_COUNT = 2;
    private static final int REQUIRED_DELETE_ARGUMENT_COUNT = 2;
    private static final int REQUIRED_DUE_ARGUMENT_COUNT = 2;
    private static final int REQUIRED_FIND_ARGUMENT_COUNT = 2;
    private static final int REQUIRED_TODO_ARGUMENT_COUNT = 2;
    private static final int REQUIRED_DEADLINE_ARGUMENT_COUNT = 4;
    private static final int REQUIRED_EVENT_ARGUMENT_COUNT = 4;

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public Task convertStringToTask(String taskInString) {
        try {
            String[] splitTaskInString = taskInString.split("\\|");
            String taskType = splitTaskInString[0].trim();
            String description = splitTaskInString[2].trim();
            boolean isDone = splitTaskInString[1].trim().equals("1");

            switch (taskType) {
            case "T":
                Todo newTodo = new Todo(description);
                newTodo.setIsDone(isDone);
                return newTodo;
            case "D":
                String byDateTimeString = splitTaskInString[3].trim().replace("T", " ");
                LocalDateTime byDateTime = parseDateTimeInput(byDateTimeString);
                Deadline newDeadline = new Deadline(description, byDateTime);
                newDeadline.setIsDone(isDone);
                return newDeadline;
            case "E":
                String atDateTimeString = splitTaskInString[3].trim().replace("T", " ");
                LocalDateTime atDateTime = parseDateTimeInput(atDateTimeString);
                Event newEvent = new Event(description, atDateTime);
                newEvent.setIsDone(isDone);
                return newEvent;
            default:
                return null;
            }
        } catch (ArrayIndexOutOfBoundsException | DukeException exception) {
            return null;
        }
    }

    public Command processUserInput(String userInput) throws DukeException {
        String[] inputArguments = parseUserInput(userInput);
        return generateCommand(inputArguments);
    }

    private String[] parseUserInput(String userInput) throws DukeException {
        ArrayList<String> inputArguments = new ArrayList<>();
        String[] splitInput = userInput.split(" ");

        try {
            String userCommand = splitInput[0];
            inputArguments.add(userCommand);
        } catch (ArrayIndexOutOfBoundsException exception) {
            throw new DukeException(" Input \"" + userInput + "\" is not usable by Duke :(");
        }

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

    private Command generateCommand(String[] inputArguments) throws DukeException {
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
        case COMMAND_DELETE:
            validateCommand(REQUIRED_DELETE_ARGUMENT_COUNT, inputArguments);
            return new DeleteCommand(description);
        case COMMAND_DUE:
            validateArgumentCount(REQUIRED_DUE_ARGUMENT_COUNT, inputArguments);
            LocalDate dueDate = parseDateInput(description);
            return new DueCommand(dueDate);
        case COMMAND_FIND:
            validateCommand(REQUIRED_FIND_ARGUMENT_COUNT, inputArguments);
            return new FindCommand(description);
        case COMMAND_TODO:
            validateCommand(REQUIRED_TODO_ARGUMENT_COUNT, inputArguments);
            Todo newTodo = new Todo(description);
            return new AddCommand(newTodo);
        case COMMAND_DEADLINE:
            validateCommand(REQUIRED_DEADLINE_ARGUMENT_COUNT, inputArguments);
            LocalDateTime byDateTime = parseDateTimeInput(inputArguments[3]);
            Deadline newDeadline = new Deadline(description, byDateTime);
            return new AddCommand(newDeadline);
        case COMMAND_EVENT:
            validateCommand(REQUIRED_EVENT_ARGUMENT_COUNT, inputArguments);
            LocalDateTime atDateTime = parseDateTimeInput(inputArguments[3]);
            Event newEvent = new Event(description, atDateTime);
            return new AddCommand(newEvent);
        default:
            throw new DukeException(" Command \"" + userCommand + "\" is not recognized by Duke :(");
        }
    }

    private void validateCommand(int requiredArgumentCount, String[] inputArguments) throws DukeException {
        validateArgumentCount(requiredArgumentCount, inputArguments);
        switch(requiredArgumentCount) {
        case 1:
            validateOneArgumentCommand(inputArguments);
            break;
        case 2:
            validateTwoArgumentCommand(inputArguments);
            break;
        case 4:
            validateFourArgumentCommand(inputArguments);
            break;
        default:
            break;
        }
    }

    private void validateArgumentCount(int requiredArgumentCount, String[] inputArguments) throws DukeException {
        String userCommand = inputArguments[0];
        int argumentCount = inputArguments.length;
        if (userCommand.equals(COMMAND_BYE) || userCommand.equals(COMMAND_LIST)) {
            argumentCount--;
        }

        if (argumentCount > requiredArgumentCount) {
            throw new DukeException(" There's too many arguments for \"" + userCommand + "\".");
        } else if (argumentCount < requiredArgumentCount) {
            throw new DukeException(" There's too little arguments for \"" + userCommand + "\".");
        }
    }

    private void validateOneArgumentCommand(String[] inputArguments) throws DukeException {
        String userCommand = inputArguments[0];
        String description = inputArguments[1];

        boolean isValidDescription = !description.isBlank();
        if (isValidDescription) {
            throw new DukeException(" There's no need for a description for \"" + userCommand + "\".");
        }
    }

    private void validateTwoArgumentCommand(String[] inputArguments) throws DukeException {
        String userCommand = inputArguments[0];
        String description = inputArguments[1];

        boolean isValidDescription = !description.isBlank();
        boolean isDoneOrDelete = userCommand.equals(COMMAND_DONE) || userCommand.equals(COMMAND_DELETE);
        if (isDoneOrDelete && !isValidDescription) {
            throw new DukeException(" The task number for \"" + userCommand + "\" cannot be empty.");
        } else if (userCommand.equals(COMMAND_TODO) && !isValidDescription) {
            throw new DukeException(" The description of \"" + userCommand + "\" cannot be empty.");
        } else if (userCommand.equals(COMMAND_DUE) && !isValidDescription) {
            throw new DukeException(" The due date of \"" + userCommand + "\" cannot be empty.");
        } else if (userCommand.equals(COMMAND_FIND) && !isValidDescription) {
            throw new DukeException(" The keyword for \"" + userCommand + "\" cannot be empty.");
        }
    }

    private void validateFourArgumentCommand(String[] inputArguments) throws DukeException {
        String userCommand = inputArguments[0];
        String description = inputArguments[1];
        String commandOption = inputArguments[2];
        String commandDateTime = inputArguments[3];

        String requiredCommandOption = "";
        if (userCommand.equals(COMMAND_DEADLINE)) {
            requiredCommandOption = REQUIRED_DEADLINE_OPTION;
        } else if (userCommand.equals(COMMAND_EVENT)) {
            requiredCommandOption = REQUIRED_EVENT_OPTION;
        }

        boolean isValidDescription = !description.isBlank();
        boolean isValidOption = commandOption.equals(requiredCommandOption);
        boolean isValidDateTime = !commandDateTime.isBlank();

        if (!isValidDescription) {
            throw new DukeException(" The description of \"" + userCommand + "\" cannot be empty.");
        } else if (!isValidOption) {
            throw new DukeException(" The \"" + requiredCommandOption + "\" option is missing for \""
                    + userCommand + "\".");
        } else if (!isValidDateTime) {
            throw new DukeException(" The \"" + requiredCommandOption + "\" information is missing for \""
                    + userCommand + "\".");
        }
    }

    private LocalDateTime parseDateTimeInput(String commandDateTime) throws DukeException {
        try {
            return LocalDateTime.parse(commandDateTime, DATE_TIME_FORMAT);
        } catch (DateTimeParseException exception) {
            throw new DukeException(" Ensure date and time format is: YYYY-MM-DD HH:MM.");
        }
    }

    private LocalDate parseDateInput(String commandDate) throws DukeException {
        try {
            return LocalDate.parse(commandDate, DATE_FORMAT);
        } catch (DateTimeParseException exception) {
            throw new DukeException(" Ensure date format is: YYYY-MM-DD.");
        }
    }
}