package sample;

/**
 * Created by Rafał on 05.06.2017.
 */
public class ProcessorTask {

    public enum TaskPriority {
        HIGH, LOW;
    }

    private int executionTime;
    private TaskPriority priority;

    public ProcessorTask(int executionTime, TaskPriority priority) {
        this.executionTime = executionTime;
        this.priority = priority;
    }

    public int getExecutionTime() {
        return executionTime;
    }

    public void setExecutionTime(int executionTime) {
        this.executionTime = executionTime;
    }

    public TaskPriority getPriority() {
        return priority;
    }

    public void setPriority(TaskPriority priority) {
        this.priority = priority;
    }
}
