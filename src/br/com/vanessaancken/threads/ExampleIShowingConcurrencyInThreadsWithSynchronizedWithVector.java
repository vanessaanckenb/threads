package br.com.vanessaancken.threads;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class ExampleIShowingConcurrencyInThreadsWithSynchronizedWithVector {

    public static void main(String[] args) throws InterruptedException {

        NumberWithVectorAndSynchronyzed numberList = new NumberWithVectorAndSynchronyzed();

        for (int i = 0; i < 10; i++) {
            new Thread(new AddNumberToVectorTaskWithSynchonized(numberList)).start();
        }

        Thread.sleep(2000);

        for (int i = 0; i < 10000; i++) {
            System.out.println(i + " + " + numberList.getNumber(i));
        }
    }
}

class AddNumberToVectorTaskWithSynchonized implements Runnable {

    NumberWithVectorAndSynchronyzed numberList;

    public AddNumberToVectorTaskWithSynchonized(NumberWithVectorAndSynchronyzed numberList){
        this.numberList = numberList;
    }

    @Override
    public void run() {
        for (int i = 0; i < 1000; i++) {
            numberList.add(Thread.currentThread().getName() + " - " + i);
        }
    }
}

class NumberWithVectorAndSynchronyzed {

    private List<String> numbers = new Vector<>();
    private int index = 0;

    public synchronized void add(String item){
        this.numbers.add(item);
        this.index++;
    }

    public String getNumber(int element){
        return numbers.get(element);
    }
}