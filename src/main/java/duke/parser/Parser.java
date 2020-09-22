package duke.parser;

import duke.command.AddCommand;
import duke.command.ByeCommand;
import duke.command.Command;
import duke.command.DeleteCommand;
import duke.command.DoneCommand;
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

/**
 * Represents the class to handle the parsing and validation of user input
 * and returns a Command object.
 */

public class Parser {
    private static final String COMMAND_BYE = "bye";
    private static final String COMMAND_LIST = "list";
    private static final String COMMAND_DONE = "done";
    private static final String COMMAND_DELETE = "delete";
    private static final String COMMAND_DUE = "due";
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
    private static final int REQUIRED_TODO_ARGUMENT_COUNT = 2;
    private static final int REQUIRED_DEADLINE_ARGUMENT_COUNT = 4;
    private static final int REQUIRED_EVENT_ARGUMENT_COUNT = 4;

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    /**
     * Convert the string representation of the Task object read from the storage file
     * into a Task object to be returned.
     *
     * @param taskInString the string representation of the Task object
     * @return a Task object generated from the its string representation, or
     *         {@code null} if the string representation is corrupted or invalid
     */
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

    /**
     * Process the user input and return the appropriate Command object.
     *
     * @param userInput the input supplied by the user
     * @return a Command object representing the command to execute
     * @throws DukeException if the user provided an invalid input such that it cannot be used
     *                       to generate a Command object
     */
    public Command processUserInput(String userInput) throws DukeException {
        String[] inputArguments = parseUserInput(userInput);
        return generateCommand(inputArguments);
    }

    /**
     *  Parse the user input and return a String array containing the arguments retrieved
     *  from the user input, where the argument for the last 3 index are only present in
     *  the array if they were supplied by the user:
     *  <ul>
     *      <li>{@code String[0]} contain the command.</li>
     *      <li>{@code String[1]} contain the description.</li>
     *      <li>{@code String[2]} contain the command option (e.g. "/by" or "/at").</li>
     *      <li>{@code String[3]} contain the date and time information.</li>
     *  </ul>
     *
     * @param userInput the raw input entered by the user
     * @return the list of arguments retrieved from the user input
     * @throws DukeException if the user has provided an input that cannot be parsed
     */
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

    /**
     * Generate and return the Command object based on the argument list.
     *
     * @param inputArguments the argument list generated from the user input
     * @return a Command object representing the command to execute
     * @throws DukeException if the command failed the validation process or is not recognized by Duke
     */
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

    /**
     * Validate the command based on the required argument count and the argument list.
     *
     * @param requiredArgumentCount the number of arguments required for the command
     * @param inputArguments the argument list generated from the user input
     * @throws DukeException if either the command has insufficient arguments or it has at least one
     *                       invalid argument (e.g. blanks or provided a String that cannot be parsed
     *                       into a Integer)
     */
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

    /**
     * Ensure that the command has meet the required argument count.
     *
     * @param requiredArgumentCount the number of arguments required for the command
     * @param inputArguments the argument list generated from the user input
     * @throws DukeException if the user has provided too many or too little arguments for the command
     */
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

    /**
     * Validate commands that require only one argument
     *
     * @param inputArguments the argument list generated from the user input
     * @throws DukeException if the user has provided a description when it is not needed
     */
    private void validateOneArgumentCommand(String[] inputArguments) throws DukeException {
        String userCommand = inputArguments[0];
        String description = inputArguments[1];

        boolean isValidDescription = !description.isBlank();
        if (isValidDescription) {
            throw new DukeException(" There's no need for a description for \"" + userCommand + "\".");
        }
    }

    /**
     * Validate commands that require two arguments.
     *
     * @param inputArguments the argument list generated from the user input
     * @throws DukeException if the user provided empty description or task number for the command
     */
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
        }
    }

    /**
     * Validate commands that require four arguments.
     *
     * @param inputArguments the argument list generated from the user input
     * @throws DukeException if the user did not provide any description, date time information, or
     *                       used the wrong command option (e.g. "/at" for deadline instead of "/by")
     */
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

    /**
     * Returns a LocalDateTime object that is parsed from the user input.
     *
     * @param commandDateTime the raw input entered by the user for the datetime argument
     * @return a LocalDateTime object representing the date and time
     * @throws DukeException if the user provided a date and time input that is not in
     *                       the format: YYYY-MM-DD HH:MM
     */
    private LocalDateTime parseDateTimeInput(String commandDateTime) throws DukeException {
        try {
            return LocalDateTime.parse(commandDateTime, DATE_TIME_FORMAT);
        } catch (DateTimeParseException exception) {
            throw new DukeException(" Ensure date and time format is: YYYY-MM-DD HH:MM.");
        }
    }

    /**
     * Returns a LocalDate object that is parsed from the user input.
     *
     * @param commandDate the raw input entered by the user for the date argument
     * @return a LocalDate object representing the date
     * @throws DukeException if the user provided a date input that is not in the format: YYYY-MM-DD
     */
    private LocalDate parseDateInput(String commandDate) throws DukeException {
        try {
            return LocalDate.parse(commandDate, DATE_FORMAT);
        } catch (DateTimeParseException exception) {
            throw new DukeException(" Ensure date format is: YYYY-MM-DD.");
        }
    }
}