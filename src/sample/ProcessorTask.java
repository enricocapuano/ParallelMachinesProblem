package sample;

/**
 * Created by Rafał on 05.06.2017.
 */
public class ProcessorTask implements Comparable {

    public enum TaskPriority {
        HIGH, LOW;
    }

    private int executionTime;
    private TaskPriority priority;

    public ProcessorTask(int executionTime, TaskPriority priority) {
        this.executionTime = executionTime;
        this.priority = priority;
    }

    @Override
    public int compareTo(Object o) {
        if(o != null) {
            ProcessorTask p = (ProcessorTask) o;
            if(p.getExecutionTime() > executionTime) return -1;
            else if(p.getExecutionTime() == executionTime) return 0;
            else return 1;
        }
        return -1;
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
