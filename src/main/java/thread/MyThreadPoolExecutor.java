package thread;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class MyThreadPoolExecutor{

    private final BlockingQueue<Runnable> workerQueue;

    private final Thread[] workerThreads;

    class Worker extends Thread {
        public Worker(String name) {
            super(name);
        }
        public void run() {
            while (true) {
                try {
                    workerQueue.take().run();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public MyThreadPoolExecutor(int numThreads) {
        if (numThreads <= 0) throw new IllegalArgumentException();
        workerQueue = new LinkedBlockingQueue<>();
        workerThreads = new Thread[numThreads];
        int i = 0;
        for(Thread t : workerThreads) {
            t = new Worker("My Pool Thread" + ++i);
            t.start();
        }
    }

    public void addTask(Runnable r) {
        try {
            workerQueue.put(r);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
