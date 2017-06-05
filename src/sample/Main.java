package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Random;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }


    private static final int PROCESSORS_COUNT = 5;

    public static void main(String[] args) {
        ArrayList<ProcessorTask> t = new ArrayList<>();
        int count = 50;
        Random r = new Random();
        for (int i = 0; i < count; i++) {
            t.add(new ProcessorTask(r.nextInt(500)+1, ProcessorTask.TaskPriority.HIGH));
        }

        ArrayList<Processor> processors = new ArrayList<>();

        Knapsack knapsack = new Knapsack(t);
        int execTime = knapsack.getDesiredExecutionTime(PROCESSORS_COUNT);
        System.out.println("Desired execution time: " + execTime + "\n");

        for (int i = 0; i < PROCESSORS_COUNT; i++) {
            System.out.print("For processor " + (i+1) + ", tasks pool: ");
            for (ProcessorTask task : knapsack.getTasks()) {
                System.out.print(task.getExecutionTime() + " ");
            }
            System.out.println("");

            processors.add(knapsack.dealTasks(execTime));
        }

        System.out.println();
        processors.forEach(System.out::println);

        System.out.print("\nTasks left: ");
        for (ProcessorTask task : knapsack.getTasks()) {
            System.out.print(task.getExecutionTime() + " ");
        }
    }
}
