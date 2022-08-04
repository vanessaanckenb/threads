package br.com.vanessaancken.threads;

public class ExampleDUsingThreadsInClassExtendingThreads {

    public static void main(String[] args) {

        /*
         * This is a bad way to use Threads
         * this example is here just for an example
         * every class/object must have its own respondability
         * PrintNumbersExtendingThread has the responsability to print numbers
         * Thread has the responsability to deal with threads
         *
         * */

        PrintNumbersExtendingThread printNumbers1 = new PrintNumbersExtendingThread(1);
        PrintNumbersExtendingThread printNumbers2 = new PrintNumbersExtendingThread(2);
        PrintNumbersExtendingThread printNumbers3 = new PrintNumbersExtendingThread(3);

        printNumbers1.start();
        printNumbers2.start();
        printNumbers3.start();
    }
}

class PrintNumbersExtendingThread extends Thread {

    private int threadNumber;

    public PrintNumbersExtendingThread(int threadNumber){
        this.threadNumber = threadNumber;
    }

    public void run () {
        for (int i = 0; i < 10; i++) {
            System.out.println("Thread " + threadNumber + ": " + i);
        }
    }
}
