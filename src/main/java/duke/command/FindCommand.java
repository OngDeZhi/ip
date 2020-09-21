package duke.command;

import duke.exception.DukeException;
import duke.storage.Storage;
import duke.task.Task;
import duke.task.TaskList;
import duke.ui.Ui;

public class FindCommand extends Command {
    private final String keyword;
    public FindCommand(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public void execute(TaskList taskList, Storage storage, Ui ui) throws DukeException {
        int findTaskCount = 0;
        for (Task task : taskList.getTaskList()) {
            String description = task.getDescription();
            if (description.contains(keyword)) {
                if (findTaskCount == 0) {
                    ui.printMessage("Here are the matching tasks in your list: ");
                }

                findTaskCount++;
                ui.printMessage("\t" + findTaskCount + ". " + task.toString());
            }
        }

        if (findTaskCount == 0) {
            ui.printMessage("Uhh.. no task matches the keyword..");
        }
    }
}