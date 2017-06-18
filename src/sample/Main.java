package sample;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class Main {

    private static ArrayList<ProcessorTask> t = new ArrayList<>();
    private static ArrayList<ProcessorTask> t2 = new ArrayList<>();
    private static int execTime;


    private static final int PROCESSORS_COUNT = 4;
    private static final int TASK_POOL_SIZE = 3000;
    private static final int BOUND = 500;

    private static long doLazyDealer() {

        ArrayList<Processor> processors = new ArrayList<>();
        LazyTaskDealer lazyTaskDealer = new LazyTaskDealer(t);
        execTime = lazyTaskDealer.getDesiredExecutionTime(PROCESSORS_COUNT);
        System.out.println("Desired exec time: " + execTime);

        return measureLazyDeal(processors, lazyTaskDealer);
    }

    private static long measureLazyDeal(ArrayList<Processor> processors, LazyTaskDealer lazyTaskDealer) {
        long startT;
        long endT;
        startT = System.currentTimeMillis();
        for (int i = 0; i < PROCESSORS_COUNT; i++) {
            processors.add(lazyTaskDealer.dealTasks(execTime));
        }
        endT = System.currentTimeMillis();

        showDealerResult(processors);

        return endT - startT;
    }

    private static void showDealerResult(ArrayList<Processor> processors) {
        int i = 1;
        for (Processor proc :
                processors) {
            System.out.format("\tproc %d: %d\n", i, proc.getTotalExecutionTime());
            i++;
        }
    }

    private static long doParallelDealer() throws InterruptedException {
        ParallelTaskDealer taskDealer = new ParallelTaskDealer(t2);
        ArrayList<ArrayList<ProcessorTask>> subLists = taskDealer.splitByProcessorsCount(PROCESSORS_COUNT);

        evenLists(taskDealer, subLists);

        List<Future<TaskDealerProduct>> futures = taskDealer.run(subLists, execTime);
        ArrayList<ProcessorTask> leftTasks = new ArrayList<>();
        ArrayList<Processor> processors = new ArrayList<>();
        ArrayList<Long> times = new ArrayList<>();

        getParallelResults(futures, leftTasks, processors, times);

//        showDealerResult(processors);
//        showLeftTasks(leftTasks);

        Collections.sort(leftTasks);
        long time = measureLeftTaskDeal(taskDealer, leftTasks, processors);

        showDealerResult(processors);
        showLeftTasks(leftTasks);

        System.out.println("PROCESSOR DEAL TIME: " + Collections.max(times));
        System.out.println("LEFT TASK DEAL TIME: " + time);

        return Collections.max(times) + time;
    }

    private static long measureLeftTaskDeal(ParallelTaskDealer taskDealer, ArrayList<ProcessorTask> leftTasks, ArrayList<Processor> processors) {
        long start = System.currentTimeMillis();
        taskDealer.dealLeftTasks(leftTasks, processors, execTime);
        long end = System.currentTimeMillis();

        return end - start;
    }

    private static void showLeftTasks(ArrayList<ProcessorTask> leftTasks) {
        System.out.print("LeftTasks: ");
        int totalTime = 0;
        for (ProcessorTask task : leftTasks) {
            totalTime += task.getExecutionTime();
            System.out.printf("%d ", task.getExecutionTime());
        }
        System.out.println("\t = " + totalTime);
    }

    private static void evenLists(ParallelTaskDealer taskDealer, ArrayList<ArrayList<ProcessorTask>> subLists) {
        List<ProcessorTask> rest;
        rest = subLists.get(subLists.size()-1);
        subLists.remove(subLists.size()-1);

        taskDealer.evenSubLists(subLists, rest);
    }

    private static void getParallelResults(List<Future<TaskDealerProduct>> futures, ArrayList<ProcessorTask> leftTasks, ArrayList<Processor> processors, ArrayList<Long> times) {
        futures.forEach(future -> {
            try {
                TaskDealerProduct product = future.get();
                leftTasks.addAll(product.getTasksLeft());
                processors.add(product.getProcessor());
                times.add(product.getExecTime());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        });
    }

    public static void main(String[] args) {
        initTasks(t, t2);

        System.out.format("SINGLE THREAD DEAL TIME:\t%fs.\n", doLazyDealer()/1000.0);
        try {
            System.out.format("PARALLEL DEALER TIME:\t%fs.\n", doParallelDealer()/1000.0);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //System.exit(1);
    }

    private static void initTasks(ArrayList<ProcessorTask> t, ArrayList<ProcessorTask> t2) {
        Random r = new Random();
        for (int i = 0; i < TASK_POOL_SIZE; i++) {
            int eTime = r.nextInt(BOUND)+1;
            t.add(new ProcessorTask(eTime, ProcessorTask.TaskPriority.HIGH));
            t2.add(new ProcessorTask(eTime, ProcessorTask.TaskPriority.HIGH));
        }
    }
}
