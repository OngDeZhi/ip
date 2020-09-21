package duke.command;

import duke.storage.Storage;
import duke.task.TaskList;
import duke.ui.Ui;

public class ByeCommand extends Command {

    @Override
    public void execute(TaskList taskList, Storage storage, Ui ui) {
        shouldExit = true;
        ui.printMessage("Bye-bye. Hope to see you again soon!");
    }
}