package duke.command;

import duke.exception.DukeException;
import duke.task.Task;

import java.util.ArrayList;

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
    public void execute(ArrayList<Task> taskList) throws DukeException {
        try {
            Task doneTask = taskList.get(doneTaskIndex);
            if (doneTask.getIsDone()) {
                System.out.println(" Remember? You have completed this task already.");
                System.out.println("\t" + taskList.get(doneTaskIndex).toString());
                return;
            }

            taskList.get(doneTaskIndex).setIsDone(true);
            writeToDukeStorage(taskList);
            int pendingTaskCount = Task.getPendingTaskCount();

            if (pendingTaskCount == 0) {
                System.out.println(" Awesome!! You are all caught up :)");
            } else {
                System.out.println(" Awesome!! Just " + pendingTaskCount + " more task(s) to go!");
            }
            System.out.println("\t" + taskList.get(doneTaskIndex).toString());
        } catch (IndexOutOfBoundsException exception) {
            throw new DukeException(" \"" + (doneTaskIndex + 1) + "\" is not a valid task number.");
        }
    }
}