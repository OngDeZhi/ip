package duke.task;

import java.time.LocalDateTime;

public class Deadline extends Task {
    protected LocalDateTime byDateTime;

    public Deadline(String description, LocalDateTime byDateTime) {
        super(description);
        this.byDateTime = byDateTime;
    }

    @Override
    public LocalDateTime getDateTime() {
        return byDateTime;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + byDateTime.format(PRINT_DATE_TIME_FORMAT) + ")";
    }

    @Override
    public String toFileFormatString() {
        return "D" + super.toFileFormatString() + FILE_FORMAT_DELIMITER + byDateTime.toString();
    }
}