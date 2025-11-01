package metrics;

public interface Metrics {
    void start();
    void stop();
    void increment(String operation);
    void print();
}
