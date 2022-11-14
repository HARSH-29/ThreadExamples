package threadexamples.producer_consumer_queue_example;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

class Producer {
    private final BlockingQueue<String> queue;
    private final List<String> strings;

    Producer(BlockingQueue<String> queue, List<String> strings) {
        this.queue = queue;
        this.strings = strings;
    }

    public void produce() throws InterruptedException {
        for (String str: strings) {
            queue.put(str);
        }
    }
}

class Consumer {
    private final BlockingQueue<String> queue;
    private final String id;

    Consumer(BlockingQueue<String> queue, String id) {
        this.queue = queue;
        this.id = id;
    }

    public void consume() throws InterruptedException {
        while(true) {
            String queueItem = queue.take();
            System.out.printf("Consumed %s by %s\n", queueItem, id);
        }
    }
}


public class Main {
    public static void main(String[] args) {
        BlockingQueue<String> queue = new ArrayBlockingQueue<String>(5);
        Producer producer = new Producer(queue, List.of("harsh", "gupta", "aman", "verma", "rakesh", "sharma",
                "suman", "singhania", "monicoa", "singhal", "naina", "mittal", "radhika", "madan", "alia", "bhatt"));
        Consumer consumer1 = new Consumer(queue, "1");
        Consumer consumer2 = new Consumer(queue, "2");

        Thread producerThread = new Thread(() -> {
            try {
                producer.produce();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        Thread consumerThread1 = new Thread(() -> {
            try {
                consumer1.consume();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        Thread consumerThread2 = new Thread(() -> {
            try {
                consumer2.consume();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        try {
            producerThread.start();
            consumerThread1.start();
            consumerThread2.start();

            producerThread.join();
            consumerThread1.join();
            consumerThread2.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
