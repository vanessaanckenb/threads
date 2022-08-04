package br.com.vanessaancken.threads;

public class ExampleCUsingThreadsWithRunnableAndLambdas {

    public static void main(String[] args) {
        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                System.out.println("Thread 1: " + i);
            }
        });

        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                System.out.println("Thread 2: " + i);
            }
        });

        Thread thread3 = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                System.out.println("Thread 3: " + i);
            }
        });


        thread1.start();
        thread2.start();
        thread3.start();
    }
}