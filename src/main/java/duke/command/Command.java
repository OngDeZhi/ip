package duke.command;

import duke.exception.DukeException;
import duke.task.Task;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public abstract class Command {
    protected static boolean shouldExit = false;

    public static boolean getShouldExit() {
        return shouldExit;
    }

    protected static void writeToDukeStorage(ArrayList<Task> taskList) throws DukeException {
        String dukeDirectory = "data" + File.separator;
        new File(dukeDirectory).mkdirs();
        try {
            String filePath = dukeDirectory + "duke.txt";
            FileWriter fileWriter = new FileWriter(filePath);
            for (Task task : taskList) {
                fileWriter.write(task.toFileFormatString() + System.lineSeparator());
            }
            fileWriter.close();
        } catch (IOException exception) {
            throw new DukeException(" Duke couldn't write the task to file :(.");
        }
    }

    public abstract void execute(ArrayList<Task> taskList) throws DukeException;
}