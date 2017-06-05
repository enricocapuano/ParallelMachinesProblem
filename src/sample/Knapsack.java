package sample;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Rafał on 05.06.2017.
 */
class Knapsack {

    private int taskCount;
    private ArrayList<ProcessorTask> tasks;

    public Knapsack(ArrayList<ProcessorTask> tasks) {
        this.taskCount = tasks.size();
        this.tasks = tasks;
    }

    public Processor dealTasks(int desiredExecutionTime) {

        Processor processor = new Processor(new ArrayList<>());

        ArrayList<Integer> pickedTasksIndexes = pickTasks(desiredExecutionTime);

        addTasksToProcessorAndRemoveThem(processor, pickedTasksIndexes);

        return processor;
    }

    private void addTasksToProcessorAndRemoveThem(Processor processor, ArrayList<Integer> pickedTasksIndexes) {
        for (int index : pickedTasksIndexes) {
            processor.addTask(tasks.get(index));
            tasks.remove(index);
        }
    }

    public int getDesiredExecutionTime(int processorsCount) {
        int time = 0;
        for (ProcessorTask task : tasks) {
            time += task.getExecutionTime();
        }

        System.out.println("Total execution time: " + time);

        return (int) Math.ceil((float)time/processorsCount);

    }

    private ArrayList<Integer> pickTasks(int desiredExecutionTime) {
        int[][] pickedTasks = populateArray(tasks.size() + 1, desiredExecutionTime + 1);
        int[][] dynamicMatrix = populateArray(tasks.size() + 1, desiredExecutionTime + 1);

        for (int task = 1; task <= tasks.size(); task++) {
            for (int execTime = 1; execTime <= desiredExecutionTime; execTime++) {
                if(tasks.get(task-1).getExecutionTime() <= execTime) {
                    dynamicMatrix[task][execTime] =
                            Math.max(
                                    tasks.get(task - 1).getExecutionTime() +
                                            dynamicMatrix[task - 1][execTime - tasks.get(task - 1).getExecutionTime()], dynamicMatrix[task - 1][execTime]);
                    if (tasks.get(task - 1).getExecutionTime() +
                            dynamicMatrix[task - 1][execTime - tasks.get(task - 1).getExecutionTime()] > dynamicMatrix[task - 1][execTime]) {
                        pickedTasks[task][execTime] = 1;
                    } else {
                        pickedTasks[task][execTime] = -1;
                    }
                } else {
                    pickedTasks[task][execTime] = -1;
                    dynamicMatrix[task][execTime] = dynamicMatrix[task - 1][execTime];
                }
            }
        }

        return getPickedTasksIndexes(desiredExecutionTime, pickedTasks);
    }

    private ArrayList<Integer> getPickedTasksIndexes(int desiredExecutionTime, int[][] pickedTasks) {
        ArrayList<Integer> pickedTasksIndexes = new ArrayList<>();
        int i = tasks.size();
        int maxWeight = desiredExecutionTime;
        while(i > 0) {
            if(pickedTasks[i][maxWeight] == 1) {
                pickedTasksIndexes.add(--i);
                maxWeight -= tasks.get(i).getExecutionTime();
            } else {
                i--;
            }
        }
        return pickedTasksIndexes;
    }

    private int[][] populateArray(int... sizes) {
        int[][] array = new int[sizes[0]][sizes[1]];
        for (int i = 0; i < sizes[0]; i++) {
            for (int j = 0; j < sizes[1]; j++) {
                array[0][1] = 0;
            }
        }
        return array;
    }

    public ArrayList<ProcessorTask> getTasks() {
        return tasks;
    }

    public void setTasks(ArrayList<ProcessorTask> tasks) {
        this.tasks = tasks;
    }

}
