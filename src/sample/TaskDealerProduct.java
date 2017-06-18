package sample;

import java.util.ArrayList;

/**
 * Created by rafal on 12.06.17.
 */
public class TaskDealerProduct {

    private Processor processor;
    private ArrayList<ProcessorTask> tasksLeft;
    private long execTime;

    public TaskDealerProduct(Processor processor, ArrayList<ProcessorTask> tasksLeft, long execTime) {
        this.processor = processor;
        this.tasksLeft = tasksLeft;
        this.execTime = execTime;
    }

    public long getExecTime() {
        return execTime;
    }

    public void setExecTime(long execTime) {
        this.execTime = execTime;
    }

    public Processor getProcessor() {
        return processor;
    }

    public void setProcessor(Processor processor) {
        this.processor = processor;
    }

    public ArrayList<ProcessorTask> getTasksLeft() {
        return tasksLeft;
    }

    public void setTasksLeft(ArrayList<ProcessorTask> tasksLeft) {
        this.tasksLeft = tasksLeft;
    }
}
