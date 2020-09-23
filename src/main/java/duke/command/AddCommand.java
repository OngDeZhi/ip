package duke.command;

import duke.exception.DukeException;
import duke.storage.Storage;
import duke.task.Task;
import duke.task.TaskList;
import duke.ui.Ui;

/**
 * Represents the command to add a new task to the list.
 */
public class AddCommand extends Command {
    private final Task newTask;

    /**
     * Creates a new AddCommand object with the specified task to be added.
     *
     * @param newTask the specified new task to be added
     */
    public AddCommand(Task newTask) {
        this.newTask = newTask;
    }

    /**
     * Adds a new task to the task list and write the updated task list to the storage file.
     *
     * @param taskList the task list
     * @param storage the storage file for storing tasks
     * @param ui the user interface to output information during command execution
     * @throws DukeException if Duke encounters error while writing to the file
     */
    @Override
    public void execute(TaskList taskList, Storage storage, Ui ui) throws DukeException {
        taskList.addTask(newTask);
        storage.writeToStorage(taskList);
        Task.incrementPendingTaskCount();

        ui.printMessage("Gotcha! I've added this task: ");
        ui.printMessage("\t" + newTask.toString());
        ui.printMessage("There's currently " + taskList.getSize() + " task(s) in the list.");
    }
}