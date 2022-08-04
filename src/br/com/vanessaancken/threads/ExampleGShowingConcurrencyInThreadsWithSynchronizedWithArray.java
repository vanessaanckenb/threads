package br.com.vanessaancken.threads;

public class ExampleGShowingConcurrencyInThreadsWithSynchronizedWithArray {

    public static void main(String[] args) throws InterruptedException {
        NumberWithArrayAndSynchronyzed numberList = new NumberWithArrayAndSynchronyzed();
        for (int i = 0; i < 10; i++) {
            new Thread(new AddNumberToArrayTaskWithSynchonized(numberList)).start();
        }

        Thread.sleep(2000);

        for (int i = 0; i < 1000; i++) {
            System.out.println(i + " + " + numberList.getNumber(i));
        }
    }
}

class AddNumberToArrayTaskWithSynchonized implements Runnable {

    NumberWithArrayAndSynchronyzed numberList;

    public AddNumberToArrayTaskWithSynchonized(NumberWithArrayAndSynchronyzed numberList){
        this.numberList = numberList;
    }

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            numberList.add(Thread.currentThread().getName() + " - " + i);
        }
    }
}

class NumberWithArrayAndSynchronyzed {

    private String[] numbers = new String[10000];
    private int index = 0;

    public synchronized void add(String item){
        this.numbers[index] = item;
        this.index++;
        System.out.println(index + " - " + this.numbers.length);
    }

    public String getNumber(int element){
        return numbers[element];
    }
}