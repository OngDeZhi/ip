package duke.task;

import java.util.ArrayList;

public class TaskList {
    private final ArrayList<Task> taskList;

    public TaskList() {
        taskList = new ArrayList<>();
    }

    public int getSize() {
        return taskList.size();
    }

    public Task getTask(int taskIndex) {
        return taskList.get(taskIndex);
    }

    public ArrayList<Task> getTaskList() {
        return taskList;
    }

    public void addTask(Task newTask) {
        taskList.add(newTask);
    }

    public void deleteTask(int deleteTaskIndex) {
        taskList.remove(deleteTaskIndex);
    }
}
