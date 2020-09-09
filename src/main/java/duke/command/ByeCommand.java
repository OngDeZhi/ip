package duke.command;

import duke.task.Task;

import java.util.ArrayList;

public class ByeCommand extends Command {

    @Override
    public void execute(ArrayList<Task> taskList) {
        shouldExit = true;
        System.out.println(" Bye-bye. Hope to see you again soon!");
    }
}