package br.com.vanessaancken.threads;

import java.util.ArrayList;
import java.util.List;

public class ExampleHShowingConcurrencyInThreadsWithSynchronizedWithArrayList {

    public static void main(String[] args) throws InterruptedException {

        // ArrayList is unsynchronized. Even if you use synchronized modifier it wont work

        NumberWithArrayListAndSynchronyzed numberList = new NumberWithArrayListAndSynchronyzed();

        for (int i = 0; i < 10; i++) {
            new Thread(new AddNumberToArrayListTaskWithSynchonized(numberList)).start();
        }

        Thread.sleep(2000);

        for (int i = 0; i < 10000; i++) {
            System.out.println(i + " + " + numberList.getNumber(i));
        }
    }
}

class AddNumberToArrayListTaskWithSynchonized implements Runnable {

    NumberWithArrayListAndSynchronyzed numberList;

    public AddNumberToArrayListTaskWithSynchonized(NumberWithArrayListAndSynchronyzed numberList){
        this.numberList = numberList;
    }

    @Override
    public void run() {
        for (int i = 0; i < 1000; i++) {
            numberList.add(Thread.currentThread().getName() + " - " + i);
        }
    }
}

class NumberWithArrayListAndSynchronyzed {

    private List<String> numbers = new ArrayList<>();
    private int index = 0;

    public synchronized void add(String item){
        this.numbers.add(item);
        this.index++;
    }

    public String getNumber(int element){
        return numbers.get(element);
    }
}