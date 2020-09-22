package duke.exception;

/**
 * Represents the custom exception class to handle all Duke-related exceptions.
 */
public class DukeException extends Exception {

    /**
     * Create a new DukeException object with the specified exception message.
     *
     * @param exceptionMessage the specified exception message.
     */
    public DukeException(String exceptionMessage) {
        super(exceptionMessage);
    }
}