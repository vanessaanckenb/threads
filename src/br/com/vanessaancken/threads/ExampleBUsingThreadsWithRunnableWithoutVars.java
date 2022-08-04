package br.com.vanessaancken.threads;

public class ExampleBUsingThreadsWithRunnableWithoutVars {

    public static void main(String[] args) {
        new Thread(new PrintNumbersImplementingRunnableB()).start();
        new Thread(new PrintNumbersImplementingRunnableB()).start();
        new Thread(new PrintNumbersImplementingRunnableB()).start();
    }
}

class PrintNumbersImplementingRunnableB implements Runnable {

    public void run () {
        Thread currentThread = Thread.currentThread();
        for (int i = 0; i < 10; i++) {
            System.out.println(currentThread.getName() + ": " + i);
        }
    }
}