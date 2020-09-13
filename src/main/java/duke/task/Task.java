package duke.task;

public class Task {
    protected String description;
    protected boolean isDone;
    private static int pendingTaskCount = 0;
    private static final String TICK_SYMBOL = "\u2713";
    private static final String CROSS_SYMBOL = "\u2718";

    public Task(String description) {
        this.description = description;
        this.isDone = false;
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

    public String getStatusIcon() {
        return (isDone ? TICK_SYMBOL : CROSS_SYMBOL);
    }

    public static int getPendingTaskCount() {
        return pendingTaskCount;
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
}