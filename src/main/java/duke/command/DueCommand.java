package duke.command;

import duke.storage.Storage;
import duke.task.Task;
import duke.task.TaskList;
import duke.ui.Ui;

import java.time.LocalDate;

/**
 * Represents the command to search for deadlines and events due on a specific day.
 */
public class DueCommand extends Command {
    private final LocalDate dueDate;

    /**
     * Create a new DueCommand object with the specified due date.
     *
     * @param dueDate the specified due date
     */
    public DueCommand(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    /**
     * List all deadlines and events that are due on that specific day.
     *
     * @param taskList the task list
     * @param storage the storage file for storing tasks
     * @param ui the user interface to output information during command execution
     */
    @Override
    public void execute(TaskList taskList, Storage storage, Ui ui) {
        int dueTaskCount = 0;
        for (Task task : taskList.getTaskList()) {
            if (task.getDateTime() == null) {
                continue;
            }

            LocalDate taskDueDate = task.getDateTime().toLocalDate();
            if (!taskDueDate.equals(dueDate)) {
                continue;
            }

            if (dueTaskCount == 0) {
                ui.printMessage("Here is a list of tasks due for that day: ");
            }

            dueTaskCount++;
            ui.printMessage("\t" + dueTaskCount + ". " + task.toString());
        }

        if (dueTaskCount == 0) {
            ui.printMessage("Nothing is due on that day! :)");
        }
    }
}