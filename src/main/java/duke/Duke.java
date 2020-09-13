package duke;

import duke.command.Command;
import duke.exception.DukeException;
import duke.parser.Parser;
import duke.task.Task;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Duke {
    private static final ArrayList<Task> taskList = new ArrayList<>();
    private static final String HORIZONTAL_LINE =
            "____________________________________________________________";

    public static void main(String[] args) {
        printWelcomeMessage();
        readFromDukeStorage();

        boolean shouldExit = false;
        while (!shouldExit) {
            try {
                Command dukeCommand = Parser.readUserInput();
                dukeCommand.execute(taskList);
                shouldExit = Command.getShouldExit();
            } catch (DukeException exception) {
                printErrorMessage(exception.getMessage());
            }

            System.out.println(HORIZONTAL_LINE);
        }
    }

    private static void printWelcomeMessage() {
        System.out.println(HORIZONTAL_LINE);
        System.out.println(" Hello! I'm Duke.");
        System.out.println(" How can I help you today?");
        System.out.println(HORIZONTAL_LINE);
    }

    private static void printErrorMessage(String errorMessage) {
        System.out.println(" ☹ OOPS!!!" + errorMessage);
    }

    private static void readFromDukeStorage() {
        boolean hasNullTask = false;
        String filePath = "data" + File.separator + "duke.txt";

        try {
            File dukeFile = new File(filePath);
            Scanner fileReader = new Scanner(dukeFile);
            while (fileReader.hasNextLine()) {
                String taskInString = fileReader.nextLine();
                Task readTask = Parser.convertStringToTask(taskInString);

                if (readTask == null) {
                    hasNullTask = true;
                } else {
                    taskList.add(readTask);
                    Task.incrementPendingTaskCount();
                }
            }
            fileReader.close();
            
            if (hasNullTask) {
                printErrorMessage(" Duke could not load some of the saved task(s).");
            } else {
                System.out.println(" Duke successfully loaded all of the saved task(s).");
            }
        } catch (FileNotFoundException exception) {
            System.out.println(" No storage file was found, let's start afresh then.");
        }

        System.out.println(HORIZONTAL_LINE);
    }
}