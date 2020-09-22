package duke.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Task {
    protected String description;
    protected boolean isDone;

    private static int pendingTaskCount = 0;
    private static final String TICK_SYMBOL = "\u2713";
    private static final String CROSS_SYMBOL = "\u2718";

    protected static final String FILE_FORMAT_DELIMITER = " | ";
    protected static final DateTimeFormatter PRINT_DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("MMM dd yyyy HH:mm");

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public String getDescription() {
        return description;
    }

    public void setIsDone(boolean isDone) {
        this.isDone = isDone;
        if (isDone) {
            decrementPendingTaskCount();
        }
    }

    public boolean getIsDone() {
        return isDone;
    }

    public int getIsDoneInInteger() {
        return (isDone ? 1 : 0);
    }

    public String getStatusIcon() {
        return (isDone ? TICK_SYMBOL : CROSS_SYMBOL);
    }

    public static int getPendingTaskCount() {
        return pendingTaskCount;
    }

    public LocalDateTime getDateTime() {
        return null;
    }

    public static void incrementPendingTaskCount() {
        pendingTaskCount++;
    }

    public static void decrementPendingTaskCount() {
        pendingTaskCount--;
    }

    public String toString() {
        return "[" + getStatusIcon() + "] " + description;
    }

    public String toFileFormatString() {
        return FILE_FORMAT_DELIMITER + getIsDoneInInteger() + FILE_FORMAT_DELIMITER + description;
    }
}