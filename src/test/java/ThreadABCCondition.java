import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadABCCondition {

    public static void main(String[] args) {
        ReentrantLock lock = new ReentrantLock();

        Condition a = lock.newCondition();
        Condition b = lock.newCondition();
        Condition c = lock.newCondition();

        new Thread(() -> {
            try {
                lock.lock();
                for (int i = 0; i < 10; i++){
                    System.out.print(Thread.currentThread().getName());
                    b.signal();
                    a.await();
                }
                b.signal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println("\nreleas lock" + Thread.currentThread().getName());
                lock.unlock();
            }
        },"A").start();

        new Thread(() -> {
            try {
                lock.lock();
                for (int i = 0; i < 10; i++){
                    System.out.print(Thread.currentThread().getName());
                    c.signal();
                    b.await();
                }
                c.signal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println("releas lock" + Thread.currentThread().getName());

                lock.unlock();
            }
        },"B").start();

        new Thread(() -> {
            try {
                lock.lock();
                for (int i = 0; i < 10; i++){
                    System.out.print(Thread.currentThread().getName());
                    a.signal();
                    c.await();
                }
                a.signal();
            } catch (InterruptedException e) {

                e.printStackTrace();
            } finally {
                System.out.println("releas lock" + Thread.currentThread().getName());

                lock.unlock();
            }
        },"C").start();
    }
}
