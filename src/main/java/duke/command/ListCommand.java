package duke.command;

import duke.task.Task;

import java.util.ArrayList;

public class ListCommand extends Command {

    @Override
    public void execute(ArrayList<Task> taskList) {
        if (taskList.isEmpty()) {
            System.out.println(" Uhh.. It's empty..");
            return;
        }

        System.out.println(" Here are the tasks in your list: ");
        for (int i = 0; i < taskList.size(); i++) {
            Task task = taskList.get(i);
            System.out.println("\t" + (i + 1) + ". " + task.toString());
        }
    }
}