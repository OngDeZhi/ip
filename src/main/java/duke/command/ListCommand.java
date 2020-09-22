package duke.command;

import duke.storage.Storage;
import duke.task.Task;
import duke.task.TaskList;
import duke.ui.Ui;

/**
 * Represents the command to list all tasks that are currently in the list.
 */
public class ListCommand extends Command {

    /**
     * List all tasks that are currently in the list.
     *
     * @param taskList the task list
     * @param storage the storage file for storing tasks
     * @param ui the user interface to output information during command execution
     */
    @Override
    public void execute(TaskList taskList, Storage storage, Ui ui) {
        if (taskList.getSize() == 0) {
            ui.printMessage("Uhh.. It's empty..");
            return;
        }

        ui.printMessage("Here are the tasks in your list: ");
        for (int i = 0; i < taskList.getSize(); i++) {
            Task task = taskList.getTask(i);
            ui.printMessage("\t" + (i + 1) + ". " + task.toString());
        }
    }
}