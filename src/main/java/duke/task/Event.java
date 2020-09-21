package duke.task;

import java.time.LocalDateTime;

public class Event extends Task {
    protected LocalDateTime atDateTime;

    public Event(String description, LocalDateTime atDateTime) {
        super(description);
        this.atDateTime = atDateTime;
    }

    @Override
    public LocalDateTime getDateTime() {
        return atDateTime;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (at: " + atDateTime.format(PRINT_DATE_TIME_FORMAT) + ")";
    }

    @Override
    public String toFileFormatString() {
        return "E" + super.toFileFormatString() + FILE_FORMAT_DELIMITER + atDateTime.toString();
    }
}