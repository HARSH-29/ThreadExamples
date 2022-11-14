package threadexamples.shared_memory_example;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

class Task implements Runnable {
    private final String id;
    private final AtomicBoolean sharedBooleanFlag;

    Task(String id, AtomicBoolean sharedBooleanFlag) {
        this.id = id;
        this.sharedBooleanFlag = sharedBooleanFlag;
    }

    public String getId() {
        return this.id;
    }

    @Override
    public void run() {
        System.out.printf("Executing task with id: %s\n", this.getId());
        if (this.sharedBooleanFlag.get()) {
            System.out.printf("Skipping processing for task: %s\n", this.getId());
            return;
        }
        if (this.getId().equals("Task to set boolean")) {
            this.sharedBooleanFlag.compareAndSet(false, true);
        }
        System.out.printf("Processed successfully task: %s\n", this.getId());
    }
}

public class Main {
    public static void main(String[] args) {
        AtomicBoolean sharedState = new AtomicBoolean();
        sharedState.set(false);

        Task task1 = new Task("1", sharedState);
        Task task2 = new Task("2", sharedState);
        Task task3 = new Task("Task to set boolean", sharedState);
        Task task4 = new Task("4", sharedState);
        Task task5 = new Task("5", sharedState);
        Task task6 = new Task("6", sharedState);
        Task task7 = new Task("7", sharedState);
        Task task8 = new Task("8", sharedState);
        Task task9 = new Task("9", sharedState);

        // Runs 2 threads at a time
        ExecutorService pool = Executors.newFixedThreadPool(2);
        pool.execute(task1);
        pool.execute(task2);
        pool.execute(task3);
        pool.execute(task4);
        pool.execute(task5);
        pool.execute(task6);
        pool.execute(task7);
        pool.execute(task8);
        pool.execute(task9);
        pool.shutdown();
    }
}