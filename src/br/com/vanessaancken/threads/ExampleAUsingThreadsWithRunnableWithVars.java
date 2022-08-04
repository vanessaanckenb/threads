package br.com.vanessaancken.threads;

public class ExampleAUsingThreadsWithRunnableWithVars {

    public static void main(String[] args) {
        Thread thread1 = new Thread(new PrintNumbersImplementingRunnable());
        Thread thread2 = new Thread(new PrintNumbersImplementingRunnable());
        Thread thread3 = new Thread(new PrintNumbersImplementingRunnable());

        thread1.start();
        thread2.start();
        thread3.start();
    }
}

class PrintNumbersImplementingRunnable implements Runnable {

    public void run () {
        Thread currentThread = Thread.currentThread();
        for (int i = 0; i < 10; i++) {
            System.out.println(currentThread.getName() + ": " + i);
        }
    }
}