package waitnotify;

/**
 * 情景: 从主线程发送多个请求, 等多个请求都完成之后, 计算每个请求返回的总和.
 * 多生产者(多线程), 一个消费者(主线程)
 * 直接在add方法加锁就行
 * 多线程是共享资源的, 关键在于资源在同一时刻只能被一个线程访问.
 * wait/notify 是Object的, 有几次wait就应该对应几次notify
 */
public class MultiThreadSync {

    private Object lock = new Object();
    private int count;

    public static void main(String[] args) {
        MultiThreadSync sync =  new MultiThreadSync();
        sync.start();
        //System.out.println(sync.count);
    }

    public void start() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                int i = 1;
                add(i);
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                int i = 2;
                add(i);
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                int i = 3;
                add(i);
            }
        }).start();

    }

    private synchronized void add(int i) {
        System.out.println("i = " + i);
        count += i;
        System.out.println("count = " + count);
    }
}
