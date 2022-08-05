package br.com.vanessaancken.threads;

public class ExampleGShowingConcurrencyInThreadsWithSynchronizedWithArray {

    public static void main(String[] args) throws InterruptedException {
        NumberWithArrayAndSynchronyzed numberList = new NumberWithArrayAndSynchronyzed();
        for (int i = 0; i < 10; i++) {
            new Thread(new AddNumberToArrayTaskWithSynchonized(numberList)).start();
        }

        new Thread(new PrintNumbersTask(numberList)).start();
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

class PrintNumbersTask implements Runnable {

    NumberWithArrayAndSynchronyzed numbers;

    public PrintNumbersTask(NumberWithArrayAndSynchronyzed numbers){
        this.numbers = numbers;
    }

    @Override
    public void run() {
        if(!numbers.isListFull()) {
            synchronized (numbers) {
                try {
                    System.out.println("Waiting...");
                    numbers.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        for (int i = 0; i < 1000; i++) {
            System.out.println(i + " + " + numbers.getNumber(i));
        }
    }
}

class NumberWithArrayAndSynchronyzed {

    private String[] numbers = new String[1000];
    private int index = 0;

    public synchronized void add(String item){
        this.numbers[index] = item;
        this.index++;

        if(this.numbers.length == index){
            System.out.println("List is full, notify waiting thread to wake up.");
            this.notify();
        }
    }

    public String getNumber(int element){
        return numbers[element];
    }

    public boolean isListFull(){
        return numbers.length == index;
    }

}