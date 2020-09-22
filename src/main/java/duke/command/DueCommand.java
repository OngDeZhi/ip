package duke.command;

import duke.storage.Storage;
import duke.task.Task;
import duke.task.TaskList;
import duke.ui.Ui;

import java.time.LocalDate;

public class DueCommand extends Command {
    private final LocalDate dueDate;

    public DueCommand(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    @Override
    public void execute(TaskList taskList, Storage storage, Ui ui) {
        int dueTaskCount = 0;
        for (Task task : taskList.getTaskList()) {
            if (task.getDateTime() == null) {
                continue;
            }

            LocalDate taskDueDate = task.getDateTime().toLocalDate();
            if (taskDueDate.equals(dueDate)) {
                if (dueTaskCount == 0) {
                    ui.printMessage("Here is a list of tasks due for that day: ");
                }

                dueTaskCount++;
                ui.printMessage("\t" + dueTaskCount + ". " + task.toString());
            }
        }

        if (dueTaskCount == 0) {
            ui.printMessage("Nothing is due on that day! :)");
        }
    }
}