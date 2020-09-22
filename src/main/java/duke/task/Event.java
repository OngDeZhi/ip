package duke.task;

import java.time.LocalDateTime;

/**
 * Represents a Event, a task that will occur at a specified date and time.
 */
public class Event extends Task {
    protected LocalDateTime atDateTime;

    /**
     * Create a new Event object with the specified description and date and time information.
     *
     * @param description the specified description
     * @param atDateTime the specified date and time information
     */

    public Event(String description, LocalDateTime atDateTime) {
        super(description);
        this.atDateTime = atDateTime;
    }

    /**
     * Return a LocalDateTime object that represents the date and time a Event will occur.
     *
     * @return a LocalDateTime object
     */
    @Override
    public LocalDateTime getDateTime() {
        return atDateTime;
    }

    /**
     * Return a string representation of this {@code Event} object.
     *
     * @return a string representation of this {@code Event} object
     */
    @Override
    public String toString() {
        return "[E]" + super.toString() + " (at: " + atDateTime.format(PRINT_DATE_TIME_FORMAT) + ")";
    }

    /**
     * Return a string representation of this {@code Event} object formatted for saving in a file.
     *
     * @return a string representation of this {@code Event} object formatted for saving in a file
     */
    @Override
    public String toFileFormatString() {
        return "E" + super.toFileFormatString() + FILE_FORMAT_DELIMITER + atDateTime.toString();
    }
}