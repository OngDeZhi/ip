package duke.command;

import duke.exception.DukeException;
import duke.storage.Storage;
import duke.task.Task;
import duke.task.TaskList;
import duke.ui.Ui;

/**
 * Represents the command to delete a task from the list.
 */
public class DeleteCommand extends Command {
    private final int deleteTaskIndex;

    /**
     * Create a new DeleteCommand object with the specified task index, which will
     * be validated first.
     *
     * @param deleteTaskIndexString the specified index of the task to be deleted
     * @throws DukeException if the user provided a string that cannot be parsed into a integer
     */
    public DeleteCommand(String deleteTaskIndexString) throws DukeException {
        try {
            deleteTaskIndex = Integer.parseInt(deleteTaskIndexString) - 1;
        } catch (NumberFormatException exception) {
            throw new DukeException(" \"" + deleteTaskIndexString + "\" is not a number.");
        }
    }

    /**
     * Delete the task and write the updated task list to the storage file.
     *
     * @param taskList the task list
     * @param storage the storage file for storing tasks
     * @param ui the user interface to output information during command execution
     * @throws DukeException if the user provided a task number that is not found in the list
     */
    @Override
    public void execute(TaskList taskList, Storage storage, Ui ui) throws DukeException {
        try {
            Task deleteTask = taskList.getTask(deleteTaskIndex);
            if (!deleteTask.getIsDone()) {
                Task.decrementPendingTaskCount();
            }

            taskList.deleteTask(deleteTaskIndex);
            storage.writeToStorage(taskList);

            ui.printMessage("Noted! I have removed this task: ");
            ui.printMessage("\t" + deleteTask.toString());
            ui.printMessage("There's currently " + taskList.getSize() + " task(s) in the list.");
        } catch (IndexOutOfBoundsException exception) {
            throw new DukeException(" \"" + (deleteTaskIndex + 1) + "\" is not a valid task number.");
        }
    }
}