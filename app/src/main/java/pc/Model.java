package pc;

public interface Model {
    Runnable newRunnableConsumer();
    Runnable newRunnableProducer();
}