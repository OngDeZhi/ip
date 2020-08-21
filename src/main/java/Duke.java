import java.util.ArrayList;
import java.util.Scanner;

public class Duke {
    private static final String HORIZONTAL_LINE =
            "____________________________________________________________";

    public static void main(String[] args) {
        System.out.println(HORIZONTAL_LINE);
        System.out.println(" Hello! I'm Duke");
        System.out.println(" What can I do for you?");
        System.out.println(HORIZONTAL_LINE);

        ArrayList<String> taskList = new ArrayList<>();
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.print(System.lineSeparator() + ">> ");
            String input = sc.nextLine();
            System.out.println(HORIZONTAL_LINE);

            if (input.equals("bye")) {
                System.out.println(" Bye-bye. Hope to see you again soon!");
                System.out.println(HORIZONTAL_LINE);
                return;
            } else if (input.equals("list")) {
                for (int i = 0; i < taskList.size(); i++) {
                    System.out.println(" " + (i + 1) + ". " + taskList.get(i));
                }
            } else {
                taskList.add(input);
                System.out.println(" Added: " + input);
            }
            System.out.println(HORIZONTAL_LINE);
        }
    }
}