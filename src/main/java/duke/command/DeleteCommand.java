package duke.command;

import duke.exception.DukeException;
import duke.storage.Storage;
import duke.task.Task;
import duke.task.TaskList;
import duke.ui.Ui;

public class DeleteCommand extends Command {
    private final int deleteTaskIndex;

    public DeleteCommand(String deleteTaskIndexString) throws DukeException {
        try {
            deleteTaskIndex = Integer.parseInt(deleteTaskIndexString) - 1;
        } catch (NumberFormatException exception) {
            throw new DukeException(" \"" + deleteTaskIndexString + "\" is not a number.");
        }
    }

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