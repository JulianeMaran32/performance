package threads;

public class MinhaThread extends Thread {

    @Override
    public void run() {
        System.out.println("Minha Thread: " + Thread.currentThread().getName());
    }

    public static void main(String[] args) throws InterruptedException {

        System.out.println("+--------------+");
        System.out.println("| Minha Thread |");
        System.out.println("+--------------+\n");

        var thread = new MinhaThread();
        thread.setName("MinhaThread");
        thread.setPriority(Thread.MAX_PRIORITY);
        System.out.println("Estamos na thread " + Thread.currentThread().getName() + " antes de iniciar uma nova trhead");
        thread.start(); // JVM cria a thread e passa para o S.O.
        Thread.sleep(1000);
        System.out.println("Estamos na thread " + Thread.currentThread().getName() + " depois de iniciar uma nova trhead");
    }

}
