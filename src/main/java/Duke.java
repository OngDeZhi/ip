import java.util.Scanner;

public class Duke {
    private static final String HORIZONTAL_LINE =
            "____________________________________________________________";

    public static void main(String[] args) {
        System.out.println(HORIZONTAL_LINE);
        System.out.println(" Hello! I'm Duke");
        System.out.println(" What can I do for you?");
        System.out.println(HORIZONTAL_LINE);

        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.print(System.lineSeparator() + ">> ");
            String input = sc.nextLine();
            System.out.println(HORIZONTAL_LINE);

            if (input.equals("bye")) {
                System.out.println(" Bye-bye. Hope to see you again soon!");
                System.out.println(HORIZONTAL_LINE);
                return;
            } else {
                System.out.println(" " + input);
            }
            System.out.println(HORIZONTAL_LINE);
        }
    }
}