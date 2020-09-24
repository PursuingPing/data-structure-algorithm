package array;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class MyArrayBlockingQueue<E> {

    private final ReentrantLock lock;
    private final Condition notFull;
    private final Condition notEmpty;

    private final Object[] items;

    private int putIndex, takeIndex, count;

    public MyArrayBlockingQueue(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException();
        }
        this.items = new Object[capacity];
        this.lock = new ReentrantLock();
        this.notFull = lock.newCondition();
        this.notEmpty = lock.newCondition();
    }

    public void put(E e) throws InterruptedException {
        if (e == null) throw new IllegalArgumentException();
        lock.lock();
        try {
            while (count == items.length) {
                //队列已满，阻塞
                notFull.await();
            }
            //入队
            items[putIndex] = e;
            if (++putIndex == items.length) {
                putIndex = 0;
            }
            ++count;
            notEmpty.signal();
        } finally {
            lock.unlock();
        }
    }

    public E take() throws InterruptedException {
       lock.lock();
       try {
           while (count == 0) {
               //队列为空，阻塞
               notEmpty.await();
           }
           //出队
           E x = (E)items[takeIndex];
           items[takeIndex] = null;
           if(++takeIndex == items.length) {
               takeIndex = 0;
           }
           --count;
           notFull.signal();
           return x;

       } finally {
           lock.unlock();
       }

    }


}
