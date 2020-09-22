package duke.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents the class to contain the description and done status of a task.
 */
public class Task {
    protected String description;
    protected boolean isDone;

    private static int pendingTaskCount = 0;
    private static final String TICK_SYMBOL = "\u2713";
    private static final String CROSS_SYMBOL = "\u2718";

    protected static final String FILE_FORMAT_DELIMITER = " | ";
    protected static final DateTimeFormatter PRINT_DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("MMM dd yyyy HH:mm");

    /**
     * Create a new Task object with the specified description and done status set to {@code false}.
     *
     * @param description the specified description
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Return the description of the task.
     *
     * @return the description of the task
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set the done status of a task.
     *
     * @param isDone {@code true} if the task is done, and {@code false} if the task is not done
     */
    public void setIsDone(boolean isDone) {
        this.isDone = isDone;
        if (isDone) {
            decrementPendingTaskCount();
        }
    }

    /**
     * Return a boolean to indicate whether the task is done.
     *
     * @return {@code true} if the task is done, and {@code false} if the task is not done
     */
    public boolean getIsDone() {
        return isDone;
    }

    /**
     * Return an integer to indicate whether the task is done.
     *
     * @return 1 if the task is done, and 0 if the task is not done
     */
    public int getIsDoneInInteger() {
        return (isDone ? 1 : 0);
    }

    /**
     * Return a symbol to indicate whether the task is done.
     *
     * @return a tick symbol if the task is done or a cross symbol if the task is not done
     */
    public String getStatusIcon() {
        return (isDone ? TICK_SYMBOL : CROSS_SYMBOL);
    }

    /**
     * Return the number of pending tasks in the list currently.
     *
     * @return the number of pending tasks in the list currently
     */
    public static int getPendingTaskCount() {
        return pendingTaskCount;
    }

    /**
     * Return {@code null} if it has no LocalDateTime object, it is to
     * be overridden by Deadline and Event.
     *
     * @return {@code null}
     */
    public LocalDateTime getDateTime() {
        return null;
    }

    /**
     * Increase the number of pending task by one.
     */
    public static void incrementPendingTaskCount() {
        pendingTaskCount++;
    }

    /**
     * Decrease the number of pending task by one.
     */
    public static void decrementPendingTaskCount() {
        pendingTaskCount--;
    }

    /**
     * Return a string representation of this {@code Task} object.
     *
     * @return a string representation of this {@code Task} object
     */
    public String toString() {
        return "[" + getStatusIcon() + "] " + description;
    }

    /**
     * Return a string representation of this {@code Task} object formatted for saving in a file.
     *
     * @return a string representation of this {@code Task} object formatted for saving in a file
     */
    public String toFileFormatString() {
        return FILE_FORMAT_DELIMITER + getIsDoneInInteger() + FILE_FORMAT_DELIMITER + description;
    }
}