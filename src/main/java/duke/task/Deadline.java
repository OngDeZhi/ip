package duke.task;

/**
 * Represents a Deadline, a task that needs to be completed by a specified date and time.
 */
public class Deadline extends Task {
    protected String by;

    /**
     * Create a new Deadline object with the specified description and date and time information.
     *
     * @param description the specified description
     * @param by the specified date and time information
     */
    public Deadline(String description, String by) {
        super(description);
        this.by = by;
    }

    /**
     * Returns a string representation of this {@code Deadline} object.
     *
     * @return a string representation of this {@code Deadline} object
     */
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by + ")";
    }

    /**
     * Returns a string representation of this {@code Deadline} object formatted for saving in a file.
     *
     * @return a string representation of this {@code Deadline} object formatted for saving in a file
     */
    @Override
    public String toFileFormatString() {
        return "D" + super.toFileFormatString() + FILE_FORMAT_DELIMITER + by;
    }
}