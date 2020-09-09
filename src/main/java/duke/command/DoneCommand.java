package duke.command;

import duke.task.Task;

import java.util.ArrayList;

public class DoneCommand extends Command {
    private final String doneTaskIndexString;

    public DoneCommand(String doneTaskIndexString) {
        this.doneTaskIndexString = doneTaskIndexString;
    }

    @Override
    public void execute(ArrayList<Task> taskList) {
        int doneTaskIndex = Integer.parseInt(doneTaskIndexString) - 1;
        Task doneTask = taskList.get(doneTaskIndex);

        if (doneTask.getStatus()) {
            System.out.println(" Remember? You have completed this task already.");
        } else {
            pendingTaskCount--;
            taskList.get(doneTaskIndex).markAsDone();
            if (pendingTaskCount == 0) {
                System.out.println(" Awesome!! You are all caught up :)");
            } else {
                System.out.println(" Awesome!! Just " + pendingTaskCount + " more task(s) to go!");
            }
        }

        System.out.println("\t" + taskList.get(doneTaskIndex).toString());
    }
}