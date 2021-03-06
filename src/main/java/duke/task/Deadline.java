package duke.task;

import java.time.LocalDateTime;

/**
 * Represents a Deadline, a task that needs to be completed by a specified date and time.
 */
public class Deadline extends Task {
    protected LocalDateTime byDateTime;

    /**
     * Creates a new Deadline object with the specified description and date and time information.
     *
     * @param description the specified description
     * @param byDateTime the specified date and time information
     */
    public Deadline(String description, LocalDateTime byDateTime) {
        super(description);
        this.byDateTime = byDateTime;
    }

    /**
     * Returns a LocalDateTime object that represents the date and time a Deadline is due.
     *
     * @return a LocalDateTime object
     */
    @Override
    public LocalDateTime getDateTime() {
        return byDateTime;
    }

    /**
     * Returns a string representation of this Deadline object.
     *
     * @return a string representation of this Deadline object
     */
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + byDateTime.format(PRINT_DATE_TIME_FORMAT) + ")";
    }

    /**
     * Returns a string representation of this Deadline object that is formatted for saving in a file.
     *
     * @return a string representation of this Deadline object that is formatted for saving in a file
     */
    @Override
    public String toFileFormatString() {
        return "D" + super.toFileFormatString() + FILE_FORMAT_DELIMITER + byDateTime.toString();
    }
}