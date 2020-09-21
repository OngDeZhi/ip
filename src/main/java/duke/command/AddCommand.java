package duke.command;

import duke.exception.DukeException;
import duke.storage.Storage;
import duke.task.Task;
import duke.task.TaskList;
import duke.ui.Ui;

public class AddCommand extends Command {
    private final Task newTask;

    public AddCommand(Task newTask) {
        this.newTask = newTask;
    }

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