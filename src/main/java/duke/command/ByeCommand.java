package duke.command;

import duke.storage.Storage;
import duke.task.TaskList;
import duke.ui.Ui;

/**
 * Represents the command to exit Duke.
 */
public class ByeCommand extends Command {

    /**
     * Set {@code shouldExit} to {@code true} and prints the goodbye message to the user.
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