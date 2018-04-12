package pc.blockqueue;


import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

import pc.AbstractConsumer;
import pc.AbstractProducer;
import pc.Model;
import pc.Task;


/**
 生产日志应放在入队操作之前，消费日志应放在出队操作之后，以保障：
 消费线程中queue.take()返回之后，对应生产线程（生产该task的线程）中queue.put()及之前的行为，对于消费线程来说都是可见的。
 想想为什么呢？因为我们需要借助“queue.put()与queue.take()的偏序关系”。其他实现方案分别借助了条件队列、锁的偏序关系，不存在该问题。
 要解释这个问题，需要读者明白可见性和Happens-Before的概念，篇幅所限，暂时不多解释。

 1
 */
public class BlockingQueueModel implements Model {
    private final BlockingQueue<Task> queue;
    private final AtomicInteger increTaskNo = new AtomicInteger(0);

    public BlockingQueueModel(int cap) {
        // LinkedBlockingQueue 的队列不 init，入队时检查容量；ArrayBlockingQueue 在创建时 init
        this.queue = new LinkedBlockingQueue<>(cap);
    }

    @Override
    public Runnable newRunnableConsumer() {
        return new ConsumerImpl();
    }

    @Override
    public Runnable newRunnableProducer() {
        return new ProducerImpl();
    }

    private class ConsumerImpl extends AbstractConsumer {
        @Override
        public void consume() throws InterruptedException {
            Task task = queue.take();
            // 固定时间范围的消费，模拟相对稳定的服务器处理过程
            Thread.sleep(500 + (long) (Math.random() * 500));
            System.out.println("consume: " + task.no);
        }
    }

    private class ProducerImpl extends AbstractProducer {
        @Override
        public void produce() throws InterruptedException {
            // 不定期生产，模拟随机的用户请求
            Thread.sleep((long) (Math.random() * 1000));
            Task task = new Task(increTaskNo.getAndIncrement());
            System.out.println("produce: " + task.no);
            queue.put(task);
        }
    }

    public static void main(String[] args) {
        Model model = new BlockingQueueModel(3);
        for (int i = 0; i < 2; i++) {
            new Thread(model.newRunnableConsumer()).start();
        }
        for (int i = 0; i < 5; i++) {
            new Thread(model.newRunnableProducer()).start();
        }
    }
}