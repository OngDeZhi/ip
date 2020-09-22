package duke.task;

/**
 * Represents an Event that will occur at a specified date and time.
 */
public class Event extends Task {
    protected String at;

    /**
     * Create a new Event object with the specified description and date and time information.
     *
     * @param description the specified description
     * @param at the specified date and time information
     */
    public Event(String description, String at) {
        super(description);
        this.at = at;
    }

    /**
     * Returns a string representation of this {@code Event} object.
     *
     * @return a string representation of this {@code Event} object
     */
    @Override
    public String toString() {
        return "[E]" + super.toString() + " (at: " + at + ")";
    }

    /**
     * Returns a string representation of this {@code Event} object formatted for saving in a file.
     *
     * @return a string representation of this {@code Event} object formatted for saving in a file
     */
    @Override
    public String toFileFormatString() {
        return "E" + super.toFileFormatString() + FILE_FORMAT_DELIMITER + at;
    }
}