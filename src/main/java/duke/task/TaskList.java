package duke.task;

import java.util.ArrayList;

/**
 * Represents the class to store all the tasks entered by the user.
 */
public class TaskList {
    private final ArrayList<Task> taskList;

    /**
     * Creates a new TaskList object.
     */
    public TaskList() {
        taskList = new ArrayList<>();
    }

    /**
     * Returns the number of tasks currently in the list.
     *
     * @return the number of tasks currently in the list
     */
    public int getSize() {
        return taskList.size();
    }

    /**
     * Retrieves the task specified by the task index.
     *
     * @param taskIndex the specified index of the task to be retrieved from the list
     * @return the task that has been retrieved from the list
     */
    public Task getTask(int taskIndex) {
        return taskList.get(taskIndex);
    }

    /**
     * Returns the task list.
     *
     * @return the task list
     */
    public ArrayList<Task> getTaskList() {
        return taskList;
    }

    /**
     * Adds a new task (Deadline, Event, Todo) to the list.
     *
     * @param newTask the specified new task to be added to the list
     */
    public void addTask(Task newTask) {
        taskList.add(newTask);
    }

    /**
     * Deletes a task from the list.
     *
     * @param deleteTaskIndex the specified task to be deleted from the list
     */
    public void deleteTask(int deleteTaskIndex) {
        taskList.remove(deleteTaskIndex);
    }
}
