public class Main3 {
    private static final Object lock = new Object();
    private static boolean turn = true; // true - очередь потока 1, false - очередь потока 2

    public static void main(String[] args) {
        // Поток 1 выводит "1"
        Thread thread1 = new Thread(() -> {
            while (true) {
                synchronized (lock) {
                    while (!turn) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                            return;
                        }
                    }
                    System.out.print("1");
                    turn = false;
                    lock.notify();
                }
            }
        });

        // Поток 2 выводит "2"
        Thread thread2 = new Thread(() -> {
            while (true) {
                synchronized (lock) {
                    while (turn) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                            return;
                        }
                    }
                    System.out.print("2");
                    turn = true;
                    lock.notify();
                }
            }
        });

        thread1.start();
        thread2.start();
    }
}
