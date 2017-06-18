package sample;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;

/**
 * Created by Squier on 06.06.2017.
 */
public class ParallelTaskDealer {

    private ArrayList<ProcessorTask> tasks;


    public ParallelTaskDealer(ArrayList<ProcessorTask> tasks) {
        this.tasks = tasks;
    }

    public List<Future<TaskDealerProduct>> run(ArrayList<ArrayList<ProcessorTask>> dataSets, int desiredExecutionTime) throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(dataSets.size());
        Collection<LazyTaskDealer> tasks = new ArrayList<>();
        long timeStart = System.currentTimeMillis();

        for (int i = 0; i < dataSets.size(); i++) {
            LazyTaskDealer lazyTaskDealer = new LazyTaskDealer(dataSets.get(i));
            lazyTaskDealer.setDesiredExecutionTime(desiredExecutionTime);
            lazyTaskDealer.setId(timeStart);
            tasks.add(lazyTaskDealer);
        }

        List<Future<TaskDealerProduct>> futures = executor.invokeAll(tasks);
        executor.shutdown();
        return futures;
    }

    public void dealLeftTasks(ArrayList<ProcessorTask> leftTasks, ArrayList<Processor> processors, int desiredExecutionTime) {
        while (leftTasks.size() > 1) {
            assignTaskWhileCan(Collections.min(processors), leftTasks, desiredExecutionTime);
        }
        if(leftTasks.size() == 1) {
            assignTaskWhileCan(Collections.min(processors), leftTasks, desiredExecutionTime);
            while(leftTasks.size() > 1) {
                Collections.min(processors).addTask(leftTasks.get(0));
                leftTasks.remove(0);
            }
        }
    }

    public void assignTaskWhileCan(Processor processor, ArrayList<ProcessorTask> leftTasks, int desiredExecutionTime) {
        ProcessorTask task;
        int i = 1;
        while (processor.getTotalExecutionTime() < desiredExecutionTime && leftTasks.size() > 0 && i <= leftTasks.size()) {
            if((task = leftTasks.get(leftTasks.size() - i)).getExecutionTime() + processor.getTotalExecutionTime() <= desiredExecutionTime) {
                processor.addTask(task);
                leftTasks.remove(leftTasks.size() - i);
            } else {
                i++;
            }
        }
    }

    public ArrayList<ArrayList<ProcessorTask>> splitByProcessorsCount(int processors) {
        ArrayList<ArrayList<ProcessorTask>> pools = new ArrayList<>();
        int subSetCount = (tasks.size() / processors);
        int subSetStart = 0;
        int subSetEnd = subSetCount;

        for (int i = 0; i < processors; i++) {
            pools.add(new ArrayList<>(tasks.subList(subSetStart, subSetEnd)));
            subSetStart = subSetEnd;
            subSetEnd += subSetCount;
        }

        pools.add(new ArrayList<>(tasks.subList(subSetStart, tasks.size())));

        return pools;
    }

    public void evenSubLists(ArrayList<ArrayList<ProcessorTask>> subLists, List<ProcessorTask> rest) {
        for (int i = rest.size() - 1; i >= 0; i--) {
            subLists.get(i % subLists.size()).add(rest.get(i));
            rest.remove(i);
        }
    }

    public static void printSubLists(ArrayList<ArrayList<ProcessorTask>> subLists, List<ProcessorTask> rest) {
        System.out.println();
        int i = 0;
        for (List<ProcessorTask> subList : subLists) {
            System.out.print("SubList " + i++ + ": ");
            for (ProcessorTask task : subList) {
                System.out.print(task.getExecutionTime() + " ");
            }
            System.out.println(" || " + subList.size() + " elements");
        }
        System.out.print("Rest: ");
        for (ProcessorTask task : rest) {
            System.out.print(task.getExecutionTime() + " ");
        }
        System.out.println(" || " + rest.size() + " elements");
    }
}
