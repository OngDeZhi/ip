package duke.command;

import duke.storage.Storage;
import duke.task.Task;
import duke.task.TaskList;
import duke.ui.Ui;

/**
 * Represents the command to find a task by searching for a keyword in its description.
 */
public class FindCommand extends Command {
    private final String keyword;

    /**
     * Creates a new FindCommand object with the specified keyword.
     *
     * @param keyword the specified keyword to search for in a task description
     */
    public FindCommand(String keyword) {
        this.keyword = keyword;
    }

    /**
     * Finds and prints the task(s) whose description contains the keyword.
     *
     * @param taskList the task list
     * @param storage the storage file for storing tasks
     * @param ui the user interface to output information during command execution
     */
    @Override
    public void execute(TaskList taskList, Storage storage, Ui ui) {
        int findTaskCount = 0;
        for (Task task : taskList.getTaskList()) {
            String description = task.getDescription();
            if (!description.contains(keyword)) {
                continue;
            }

            if (findTaskCount == 0) {
                ui.printMessage("Here are the matching tasks in your list: ");
            }

            findTaskCount++;
            ui.printMessage("\t" + findTaskCount + ". " + task.toString());
        }

        if (findTaskCount == 0) {
            ui.printMessage("Uhh.. no task matches the keyword..");
        }
    }
}