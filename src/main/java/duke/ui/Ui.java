package duke.ui;

import java.util.Scanner;

/**
 * Represents the class to handle all input and output of Duke.
 */

public class Ui {
    private static final Scanner CONSOLE = new Scanner(System.in);
    private static final String HORIZONTAL_LINE =
            "____________________________________________________________";

    /**
     * Prints a single horizontal line for formatting purposes.
     */
    public void printHorizontalLine() {
        System.out.println(HORIZONTAL_LINE);
    }

    /**
     * Prints a message to the user.
     *
     * @param message the specified message to print
     */
    public void printMessage(String message) {
        System.out.println(" " + message);
    }


    /**
     * Prints the error message.
     *
     * @param errorMessage the specified error information
     */
    public void printErrorMessage(String errorMessage) {
        System.out.println(" ☹ OOPS!!!" + errorMessage);
    }

    /**
      * Prints the input prompt and prompt the user for input.
     *
      * @return the raw input entered by the user
     */
    public String readUserInput() {
        System.out.print(System.lineSeparator() + " >> ");
        String userInput = CONSOLE.nextLine();
        printHorizontalLine();
        return userInput;
    }

    /**
     * Prints the welcome message to the user.
     */
    public void printWelcomeMessage() {
        printHorizontalLine();
        printMessage("Hello! I'm Duke.");
        printMessage("How can I help you today?");
        printHorizontalLine();
    }
}