package duke.command;

import duke.exception.DukeException;
import duke.task.Task;

import java.util.ArrayList;

public abstract class Command {
    protected static boolean shouldExit = false;

    public static boolean getShouldExit() {
        return shouldExit;
    }

    public abstract void execute(ArrayList<Task> taskList) throws DukeException;
}