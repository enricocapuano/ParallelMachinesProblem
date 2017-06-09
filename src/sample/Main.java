package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }


    private static final int PROCESSORS_COUNT = 100;

    public static void main(String[] args) {
        ArrayList<ProcessorTask> t = new ArrayList<>();
        int count = 1000;
        Random r = new Random();
        for (int i = 0; i < count; i++) {
            t.add(new ProcessorTask(r.nextInt(500)+1, ProcessorTask.TaskPriority.HIGH));
        }

        ArrayList<Processor> processors = new ArrayList<>();

        LazyTaskDealer lazyTaskDealer = new LazyTaskDealer(t);
        int execTime = lazyTaskDealer.getDesiredExecutionTime(PROCESSORS_COUNT);
        System.out.println("Desired execution time: " + execTime + "\n");

        long startT, endT, stotal, eTotal;

        stotal = System.currentTimeMillis();
        for (int i = 0; i < PROCESSORS_COUNT; i++) {
//            System.out.print("For processor " + (i+1) + ", tasks pool: ");
//            for (ProcessorTask task : lazyTaskDealer.getTasks()) {
//                System.out.print(task.getExecutionTime() + " ");
//            }
//            System.out.println("");
            startT = System.currentTimeMillis();
            processors.add(lazyTaskDealer.dealTasks(execTime));
            endT = System.currentTimeMillis();
            System.out.println("For processor " + (i+1) + " count time = " + (endT - startT));
        }
        eTotal = System.currentTimeMillis();

        System.out.println("Total exec time = " + (eTotal - stotal));

        System.out.println();
//        processors.forEach(System.out::println);
//
//        System.out.print("\nTasks left: ");
//        for (ProcessorTask task : lazyTaskDealer.getTasks()) {
//            System.out.print(task.getExecutionTime() + " ");
//        }
//
//        System.out.println("\n");


        t = new ArrayList<>();
        count += 49000;
        r = new Random();
        for (int i = 0; i < count; i++) {
            t.add(new ProcessorTask(r.nextInt(500)+1, ProcessorTask.TaskPriority.HIGH));
        }

        ParallelTaskDealer taskDealer = new ParallelTaskDealer(t);
        List<ProcessorTask> rest;

        ArrayList<ArrayList<ProcessorTask>> subLists = taskDealer.splitByProcessorsCount(PROCESSORS_COUNT);

        rest = subLists.get(subLists.size()-1);
        subLists.remove(subLists.size()-1);

        //ParallelTaskDealer.printSubLists(subLists, rest);
        taskDealer.evenSubLists(subLists, rest);
        //ParallelTaskDealer.printSubLists(subLists, rest);

        taskDealer.run(subLists, execTime);
    }
}
