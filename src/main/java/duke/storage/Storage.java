package duke.storage;

import duke.exception.DukeException;
import duke.parser.Parser;
import duke.task.Task;
import duke.task.TaskList;
import duke.ui.Ui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * Represents the class to handle all of the reading and writing of tasks to and from the storage file.
 */
public class Storage {
    private final String filePath;
    private final String fileDirectory;

    /**
     * Creates a new Storage object with the specified path to the
     * storage file and storage directory.
     *
     * @param filePath the specified path to the storage file
     * @param fileDirectory the specified path to the storage directory
     */
    public Storage(String filePath, String fileDirectory) {
        this.filePath = filePath;
        this.fileDirectory = fileDirectory;
    }

    /**
     * Reads from the storage file for the saved task(s).
     *
     * @param parser the parser converts the strings read from the file into Task objects
     * @param ui the user interface to output information to the user after reading
     * @return the task list read from the storage file
     */

    public TaskList readFromStorage(Parser parser, Ui ui) {
        ui.printHorizontalLine();
        boolean hasNullTask = false;
        TaskList taskList = new TaskList();

        try {
            File dukeFile = new File(filePath);
            Scanner fileReader = new Scanner(dukeFile);
            while (fileReader.hasNextLine()) {
                String taskInString = fileReader.nextLine();
                Task readTask = parser.convertStringToTask(taskInString);

                if (readTask == null) {
                    hasNullTask = true;
                } else {
                    taskList.addTask(readTask);
                    Task.incrementPendingTaskCount();
                }
            }
            fileReader.close();

            if (hasNullTask) {
                ui.printErrorMessage(" Duke could not load some of the saved task(s).");
            } else {
                ui.printMessage("Duke successfully loaded all of the saved task(s).");
            }
        } catch (FileNotFoundException exception) {
            ui.printMessage("No storage file was found, let's start afresh then.");
        }

        return taskList;
    }

    /**
     * Writes to the storage file with the current task list.
     *
     * @param taskList the tasks that are currently in the list
     * @throws DukeException if Duke encounters an error while writing to the file
     */
    public void writeToStorage(TaskList taskList) throws DukeException {
        new File(fileDirectory).mkdirs();
        try {
            FileWriter fileWriter = new FileWriter(filePath);
            for (Task task : taskList.getTaskList()) {
                fileWriter.write(task.toFileFormatString() + System.lineSeparator());
            }
            fileWriter.close();
        } catch (IOException exception) {
            throw new DukeException(" Duke couldn't write the task to file :(.");
        }
    }
}