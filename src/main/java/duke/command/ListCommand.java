package duke.command;

import duke.storage.Storage;
import duke.task.Task;
import duke.task.TaskList;
import duke.ui.Ui;

public class ListCommand extends Command {

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