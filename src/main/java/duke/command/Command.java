package duke.command;

import duke.exception.DukeException;
import duke.storage.Storage;
import duke.task.TaskList;
import duke.ui.Ui;

public abstract class Command {
    protected static boolean shouldExit = false;

    public static boolean getShouldExit() {
        return shouldExit;
    }

    public abstract void execute(TaskList taskList, Storage storage, Ui ui) throws DukeException;
}