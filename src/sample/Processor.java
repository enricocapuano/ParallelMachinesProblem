package sample;

import java.util.ArrayList;

/**
 * Created by Rafał on 05.06.2017.
 */
public class Processor implements Comparable {

    private ArrayList<ProcessorTask> tasks;
    private int totalExecutionTime;


    public Processor(ArrayList<ProcessorTask> tasks) {
        this.tasks = tasks;
    }

    @Override
    public int compareTo(Object o) {
        if(o != null) {
            Processor p = (Processor) o;
            if(p.getTotalExecutionTime() > totalExecutionTime) return -1;
            else if(p.getTotalExecutionTime() == totalExecutionTime) return 0;
            else return 1;
        }
        return 1;
    }

    public int getTotalExecutionTime() {
        totalExecutionTime = 0;
        for (ProcessorTask task : tasks) {
            totalExecutionTime += task.getExecutionTime();
        }
        return totalExecutionTime;
    }

    public void addTask(ProcessorTask task) {
        tasks.add(task);
        totalExecutionTime += task.getExecutionTime();
    }

    public ProcessorTask getTask(int index) {
        return tasks.get(index);
    }

    public ArrayList<ProcessorTask> getTasks() {
        return tasks;
    }

    public void setTasks(ArrayList<ProcessorTask> tasks) {
        this.tasks = tasks;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("Processor{" + "tasks= ");

        for (ProcessorTask task : tasks) {
            builder.append(task.getExecutionTime());
            builder.append(" ");
        }
        builder .append("\b, totalExecutionTime=" + totalExecutionTime + '}');
        return builder.toString();
    }
}
