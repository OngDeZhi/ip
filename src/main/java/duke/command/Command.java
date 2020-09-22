package duke.command;

import duke.exception.DukeException;
import duke.storage.Storage;
import duke.task.TaskList;
import duke.ui.Ui;

/**
 * Abstract class to represent the various command types.
 */
public abstract class Command {
    protected static boolean shouldExit = false;

    /**
     * Check if Duke should exit.
     *
     * @return {@code true} if Duke should exit, and {@code false} otherwise
     */
    public static boolean getShouldExit() {
        return shouldExit;
    }

    /**
     * Abstract method for the execution of the various command types.
     *
     * @param taskList the task list
     * @param storage the storage file for storing tasks
     * @param ui the user interface to output information during command execution
     * @throws DukeException if an error is encountered during command execution
     */
    public abstract void execute(TaskList taskList, Storage storage, Ui ui) throws DukeException;
}