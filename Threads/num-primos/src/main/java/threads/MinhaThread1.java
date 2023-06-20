package threads;

public class MinhaThread1 extends Thread {

    @Override
    public void run() {
        System.out.println("Minha Thread: " + Thread.currentThread().getName());
    }

    public static void main(String[] args) {
        Thread t1 = new MinhaThread1();
        Thread t2 = new MinhaThread1();
        t1.start();
        t2.start();
        int totalThreads = Thread.activeCount();
        System.out.println("Total de Threads: " + totalThreads);
    }

}
