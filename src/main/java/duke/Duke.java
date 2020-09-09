package duke;

import duke.command.Command;
import duke.exception.DukeException;
import duke.parser.Parser;
import duke.task.Task;

import java.util.ArrayList;

public class Duke {
    private static boolean shouldExit = false;
    private static final ArrayList<Task> taskList = new ArrayList<>();
    private static final String HORIZONTAL_LINE =
            "____________________________________________________________";

    public static void main(String[] args) {
        printWelcomeMessage();
        while (!shouldExit) {
            try {
                Command dukeCommand = Parser.readUserInput();
                dukeCommand.execute(taskList);
                shouldExit = Command.getShouldExit();
            } catch (DukeException exception) {
                printErrorMessage(exception.getMessage());
            }

            System.out.println(HORIZONTAL_LINE);
        }
    }

    private static void printWelcomeMessage() {
        System.out.println(HORIZONTAL_LINE);
        System.out.println(" Hello! I'm Duke.");
        System.out.println(" How can I help you today?");
        System.out.println(HORIZONTAL_LINE);
    }

    private static void printErrorMessage(String errorMessage) {
        System.out.println(" ☹ OOPS!!!" + errorMessage);
    }
}