package duke.ui;

import java.util.Scanner;

public class Ui {
    private static final Scanner CONSOLE = new Scanner(System.in);
    private static final String HORIZONTAL_LINE =
            "____________________________________________________________";

    public void printHorizontalLine() {
        System.out.println(HORIZONTAL_LINE);
    }

    public void printMessage(String message) {
        System.out.println(" " + message);
    }

    public void printErrorMessage(String errorMessage) {
        System.out.println(" ☹ OOPS!!!" + errorMessage);
    }

    public String readUserInput() {
        System.out.print(System.lineSeparator() + " >> ");
        String userInput = CONSOLE.nextLine();
        printHorizontalLine();
        return userInput;
    }

    public void printWelcomeMessage() {
        printHorizontalLine();
        printMessage("Hello! I'm Duke.");
        printMessage("How can I help you today?");
        printHorizontalLine();
    }
}