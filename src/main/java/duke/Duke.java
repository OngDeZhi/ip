package duke;

import duke.command.Command;
import duke.exception.DukeException;
import duke.parser.Parser;
import duke.storage.Storage;
import duke.task.TaskList;
import duke.ui.Ui;

import java.io.File;

/**
 * Represents the entry point of the task tracking application.
 */
public class Duke {
    private static final String FILE_DIRECTORY = "data" + File.separator;
    private static final String FILE_PATH = "data" + File.separator + "duke.txt";

    private final Ui ui;
    private final Parser parser;
    private final Storage storage;
    private final TaskList taskList;

    /**
     * Create a new Duke object and load the saved task(s) from the storage file.
     */
    public Duke() {
        ui = new Ui();
        parser = new Parser();
        storage = new Storage(FILE_PATH, FILE_DIRECTORY);
        taskList = storage.readFromStorage(parser, ui);
    }

    /**
     * Runs Duke until the <code>bye</code> command is entered by the user.
     */
    public void run() {
        ui.printWelcomeMessage();

        boolean shouldExit = false;
        while (!shouldExit) {
            String userInput = ui.readUserInput();
            try {
                Command dukeCommand = parser.processUserInput(userInput);
                dukeCommand.execute(taskList, storage, ui);
                shouldExit = Command.getShouldExit();
            } catch (DukeException exception) {
                ui.printErrorMessage(exception.getMessage());
            } finally {
                ui.printHorizontalLine();
            }
        }
    }

    public static void main(String[] args) {
        new Duke().run();
    }
}