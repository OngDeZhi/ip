package duke.command;

import duke.storage.Storage;
import duke.task.TaskList;
import duke.ui.Ui;

/**
 * Represents the command to exit Duke.
 */
public class ByeCommand extends Command {

    /**
     * Prints the goodbye message to the user and also set {@code shouldExit} to {@code true}.
     *
     * @param taskList the task list
     * @param storage the storage file for storing tasks
     * @param ui the user interface to output information during command execution
     */
    @Override
    public void execute(TaskList taskList, Storage storage, Ui ui) {
        shouldExit = true;
        ui.printMessage("Bye-bye. Hope to see you again soon!");
    }
}