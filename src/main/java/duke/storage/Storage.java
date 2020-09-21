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

public class Storage {
    private final String filePath;
    private final String fileDirectory;

    public Storage(String filePath, String fileDirectory) {
        this.filePath = filePath;
        this.fileDirectory = fileDirectory;
    }

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
                ui.printErrorMessage("Duke could not load some of the saved task(s).");
            } else {
                ui.printMessage("Duke successfully loaded all of the saved task(s).");
            }
        } catch (FileNotFoundException exception) {
            ui.printMessage("No storage file was found, let's start afresh then.");
        }

        return taskList;
    }

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