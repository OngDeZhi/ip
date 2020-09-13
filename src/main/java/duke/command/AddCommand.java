package duke.command;

import duke.exception.DukeException;
import duke.task.Task;

import java.util.ArrayList;

public class AddCommand extends Command {
    private final Task newTask;

    public AddCommand(Task newTask) {
        this.newTask = newTask;
    }

    @Override
    public void execute(ArrayList<Task> taskList) throws DukeException {
        taskList.add(newTask);
        Task.incrementPendingTaskCount();
        writeToDukeStorage(taskList);

        System.out.println(" Gotcha! I've added this task: ");
        System.out.println("\t" + newTask.toString());
        System.out.println(" There's currently " + taskList.size() + " task(s) in the list.");
    }
}