package duke.task;

/**
 * Represents a Todo, a task that needs to be done.
 */
public class Todo extends Task {

    /**
     * Creates a new Todo object with the specified description.
     *
     * @param description the specified description
     */
    public Todo(String description) {
        super(description);
    }

    /**
     * Returns a string representation of this Todo object.
     *
     * @return a string representation of this Todo object
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }

    /**
     * Returns a string representation of this Todo object that is formatted for saving in a file.
     *
     * @return a string representation of this Todo object that is formatted for saving in a file
     */
    @Override
    public String toFileFormatString() {
        return "T" + super.toFileFormatString();
    }
}