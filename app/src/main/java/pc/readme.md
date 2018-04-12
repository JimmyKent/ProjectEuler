注：零长度的byte数组对象创建起来将比任何对象都经济――查看编译后的字节码：
生成零长度的byte[]对象只需3条操作码，而Object lock = new Object()则需要7行操作码。



https://blog.csdn.net/ns_code/article/details/17225469

 2、notify（）

     public final native void notify() throws IllegalMonitorStateException

        该方法也要在同步方法或同步块中调用，即在调用前，线程也必须要获得该对象的对象级别锁，的如果调用notify（）时没有持有适当的锁，也会抛出IllegalMonitorStateException。

     该方法用来通知那些可能等待该对象的对象锁的其他线程。如果有多个线程等待，则线程规划器任意挑选出其中一个wait（）状态的线程来发出通知，并使它等待获取该对象的对象锁（notify后，当前线程不会马上释放该对象锁，wait所在的线程并不能马上获取该对象锁，要等到程序退出synchronized代码块后，当前线程才会释放锁，wait所在的线程也才可以获取该对象锁），但不惊动其他同样在等待被该对象notify的线程们。当第一个获得了该对象锁的wait线程运行完毕以后，它会释放掉该对象锁，此时如果该对象没有再次使用notify语句，则即便该对象已经空闲，其他wait状态等待的线程由于没有得到该对象的通知，会继续阻塞在wait状态，直到这个对象发出一个notify或notifyAll。这里需要注意：它们等待的是被notify或notifyAll，而不是锁。这与下面的notifyAll（）方法执行后的情况不同。



notify 只唤醒一个,其他的没有得到锁的会继续等待

notifyAll 会唤醒所有,然后不需要再调用notify了


https://monkeysayhi.github.io/2017/10/08/Java%E5%AE%9E%E7%8E%B0%E7%94%9F%E4%BA%A7%E8%80%85-%E6%B6%88%E8%B4%B9%E8%80%85%E6%A8%A1%E5%9E%8B/

实现三的并发瓶颈很明显，因为在锁 BUFFER_LOCK 看来，任何消费者线程与生产者线程都是一样的。换句话说，同一时刻，最多只允许有一个线程（生产者或消费者，二选一）操作缓冲区 buffer。

而实际上，如果缓冲区是一个队列的话，“生产者将产品入队”与“消费者将产品出队”两个操作之间没有同步关系，可以在队首出队的同时，在队尾入队。理想性能可提升至实现三的两倍。

去掉这个瓶颈
那么思路就简单了：需要两个锁 CONSUME_LOCK与PRODUCE_LOCK，CONSUME_LOCK控制消费者线程并发出队，PRODUCE_LOCK控制生产者线程并发入队；相应需要两个条件变量NOT_EMPTY与NOT_FULL，NOT_EMPTY负责控制消费者线程的状态（阻塞、运行），NOT_FULL负责控制生产者线程的状态（阻塞、运行）。以此让优化消费者与消费者（或生产者与生产者）之间是串行的；消费者与生产者之间是并行的。


线程间的状态转换
1、新建(New)
新创建了一个线程对象，还未调用start()方法。

Thread thread = new Thread();
2、就绪（Ready to run）
线程对象创建后，其他线程(比如main线程）调用了该对象的start()方法。该状态的线程位于可运行线程池中，等待被线程调度选中 获取cpu 的使用权 。

3、运行中（Running）
可运行状态(runnable)的线程获得了cpu 时间片（timeslice） ，执行程序代码。

4、睡眠（Sleeping）
也可以称作 TIMED_WAITING（有等待时间的等待状态）。

线程主动调用 这几个方法：1. Thread.sleep方法 2. Object的wait方法，带有时间 3. Thread.join方法，带有时间，4. LockSupport的parkNanos方法，带有时间。

5、阻塞（Blocked）
阻塞状态是指线程因为某种原因放弃了cpu 使用权，暂时停止运行。直到线程进入可运行(runnable)状态，才有机会再次获得cpu timeslice 转到运行(running)状态。阻塞的情况分两种：

同步阻塞：运行(running)的线程进入了一个synchronized方法，若该同步锁被别的线程占用，则JVM会把该线程放入锁池(lock pool)中。
其他阻塞：运行(running)的线程发出了I/O请求时，JVM会把该线程置为阻塞状态。当I/O处理完毕时，线程重新转入可运行(runnable)状态。
6、等待（Waiting）
运行中（Running）的线程执行了4个方法中的任意方法： 1. Object的wait方法，并且没有使用timeout参数; 2. Thread的join方法，没有使用timeout参数 3. LockSupport的park方法，4:Conditon的await方法。

7、死亡(dead)
线程run()、main() 方法执行结束，或者因异常退出了run()方法，则该线程结束生命周期。死亡的线程不可再次复生。

作者：FX_SKY
链接：https://www.jianshu.com/p/dbbcceb6bc2a
來源：简书
著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。