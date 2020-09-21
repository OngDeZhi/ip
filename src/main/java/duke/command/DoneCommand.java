package duke.command;

import duke.exception.DukeException;
import duke.storage.Storage;
import duke.task.Task;
import duke.task.TaskList;
import duke.ui.Ui;

public class DoneCommand extends Command {
    private final int doneTaskIndex;

    public DoneCommand(String doneTaskIndexString) throws DukeException {
        try {
            doneTaskIndex = Integer.parseInt(doneTaskIndexString) - 1;
        } catch (NumberFormatException exception) {
            throw new DukeException(" \"" + doneTaskIndexString + "\" is not a number.");
        }
    }

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