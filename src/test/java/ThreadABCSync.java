public class ThreadABCSync {

    //使用Boolean变量对打印顺序进行控制，true表示当前线程打印
    private static boolean startA = true;
    private static boolean startB = false;
    private static boolean startC = false;

    public static void main(String[] args) {
        //作为锁对象
        final Object lock = new Object();

        new Thread(() -> {
            synchronized (lock) {
                for (int i = 0; i < 10 ;) {
                    if (startA) {
                        System.out.print(Thread.currentThread().getName());
                        //下一个打印
                        startA = false;
                        startB = true;
                        startC = false;
                        lock.notifyAll();
                        i++;
                    } else {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        },"A").start();

        new Thread(() -> {
            synchronized (lock) {
                for (int i = 0; i < 10 ;) {
                    if (startB) {
                        System.out.print(Thread.currentThread().getName());
                        //下一个打印
                        startA = false;
                        startB = false;
                        startC = true;
                        lock.notifyAll();
                        i++;
                    } else {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        },"B").start();

        new Thread(() -> {
            synchronized (lock) {
                for (int i = 0; i < 10 ;) {
                    if (startC) {
                        System.out.print(Thread.currentThread().getName());
                        //下一个打印
                        startA = true;
                        startB = false;
                        startC = false;
                        lock.notifyAll();
                        i++;
                    } else {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        },"C").start();

    }
}
