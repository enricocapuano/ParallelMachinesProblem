package sample;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by Squier on 06.06.2017.
 */
public class ParallelTaskDealer {

    private ArrayList<ProcessorTask> tasks;


    public ParallelTaskDealer(ArrayList<ProcessorTask> tasks) {
        this.tasks = tasks;
    }

    public ArrayList<Processor> run(ArrayList<ArrayList<ProcessorTask>> dataSets, int desiredExecutionTime) {
        ExecutorService executor = Executors.newFixedThreadPool(dataSets.size());

        long timeStart = System.currentTimeMillis();

        for (int i = 0; i < dataSets.size(); i++) {
            LazyTaskDealer lazyTaskDealer = new LazyTaskDealer(dataSets.get(i));
            lazyTaskDealer.setDesiredExecutionTime(desiredExecutionTime);
            lazyTaskDealer.setId(timeStart);
            executor.submit(lazyTaskDealer);
        }

        return null;

    }

    public ArrayList<ArrayList<ProcessorTask>> splitByProcessorsCount(int processors) {
        ArrayList<ArrayList<ProcessorTask>> pools = new ArrayList<>();
        int subSetCount = (tasks.size() / processors);
        int subSetStart = 0;
        int subSetEnd = subSetCount;

        System.out.println("Size: " + tasks.size() + " Start: " + subSetStart + " End: " + subSetEnd + " Count: " + subSetCount);

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
