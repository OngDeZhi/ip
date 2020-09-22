package duke.command;

import duke.exception.DukeException;
import duke.storage.Storage;
import duke.task.Task;
import duke.task.TaskList;
import duke.ui.Ui;

/**
 * Represents the command to mark a task as done in the list.
 */
public class DoneCommand extends Command {
    private final int doneTaskIndex;

    /**
     * Create a new DoneCommand object with the specified task index, which will
     * be validated first.
     *
     * @param doneTaskIndexString the specified index of the task to be mark as done
     * @throws DukeException if the user provided a string that cannot be parsed into a integer
     */
    public DoneCommand(String doneTaskIndexString) throws DukeException {
        try {
            doneTaskIndex = Integer.parseInt(doneTaskIndexString) - 1;
        } catch (NumberFormatException exception) {
            throw new DukeException(" \"" + doneTaskIndexString + "\" is not a number.");
        }
    }

    /**
     * Mark the task as done and write the updated task list to the storage file.
     *
     * @param taskList the task list
     * @param storage the storage file for storing tasks
     * @param ui the user interface to output information during command execution
     * @throws DukeException if the user provided a task number that is not found in the list
     */
    @Override
    public void execute(TaskList taskList, Storage storage, Ui ui) throws DukeException {
        try {
            Task doneTask = taskList.getTask(doneTaskIndex);
            if (doneTask.getIsDone()) {
                ui.printMessage("Remember? You have completed this task already.");
                ui.printMessage("\t" + taskList.getTask(doneTaskIndex).toString());
                return;
            }

            taskList.getTask(doneTaskIndex).setIsDone(true);
            storage.writeToStorage(taskList);

            int pendingTaskCount = Task.getPendingTaskCount();
            if (pendingTaskCount == 0) {
                ui.printMessage("Awesome!! You are all caught up :)");
            } else {
                ui.printMessage("Awesome!! Just " + pendingTaskCount + " more task(s) to go!");
            }
            ui.printMessage("\t" + taskList.getTask(doneTaskIndex).toString());
        } catch (IndexOutOfBoundsException exception) {
            throw new DukeException(" \"" + (doneTaskIndex + 1) + "\" is not a valid task number.");
        }
    }
}