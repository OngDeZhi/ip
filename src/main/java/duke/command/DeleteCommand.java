package duke.command;

import duke.exception.DukeException;
import duke.task.Task;

import java.util.ArrayList;

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
    public void execute(ArrayList<Task> taskList) throws DukeException {
        try {
            Task deleteTask = taskList.get(deleteTaskIndex);
            if (!deleteTask.getIsDone()) {
                Task.decrementPendingTaskCount();
            }

            taskList.remove(deleteTaskIndex);
            System.out.println(" Noted! I have removed this task: ");
            System.out.println("\t" + deleteTask.toString());
            System.out.println(" There's currently " + taskList.size() + " task(s) in the list.");
        } catch (IndexOutOfBoundsException exception) {
            throw new DukeException(" \"" + (deleteTaskIndex + 1) + "\" is not a valid task number.");
        }
    }
}
