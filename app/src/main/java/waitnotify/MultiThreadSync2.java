package waitnotify;

import java.util.LinkedList;
import java.util.Queue;

public class MultiThreadSync2 {

    private final Object BUFFER_LOCK = new Object();
    private int taskCount;//有多少个生产者
    private Queue<Integer> buffer = new LinkedList<>();
    private int count;


    public static void main(String[] args) {
        MultiThreadSync2 sync = new MultiThreadSync2();
        sync.taskCount = 5;
        sync.start();
        System.out.println("count " + sync.count);
    }

    public void start() {
        //生产
        for (int i = 0; i < taskCount; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep((long) (Math.random() * 1000));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    synchronized (BUFFER_LOCK) {
                        int j = (int) (Math.random() * 10);
                        System.out.println("produce " + j);
                        buffer.offer(j);
                        BUFFER_LOCK.notify();
                    }
                }
            }).start();
        }

        //消费
        consume();
    }

    private void consume() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (BUFFER_LOCK) {
                    while (taskCount == 0) {
                        try {
                            BUFFER_LOCK.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    try {
                        Thread.sleep(500 + (long) (Math.random() * 500));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    taskCount--;
                    add(buffer.poll());
                    //BUFFER_LOCK.notifyAll();
                }
            }
        }).start();
    }

    private void add(int i) {
        System.out.println("add i = " + i);
        count += i;
        System.out.println("add count = " + count);
    }
}
