package br.com.vanessaancken.threads;

public class ExampleFShowingConcurrencyInThreadsWithoutSynchronizedWithArray {

    public static void main(String[] args) throws InterruptedException {
        NumberWithArray numbers = new NumberWithArray();
        for (int i = 0; i < 10; i++) {
            new Thread(new AddNumberToArrayTask(numbers)).start();
        }

        Thread.sleep(2000);

        for (int i = 0; i < 1000; i++) {
            System.out.println(i + " + " + numbers.getNumber(i));
        }
    }
}


class AddNumberToArrayTask implements Runnable {

    NumberWithArray numbers;

    public AddNumberToArrayTask(NumberWithArray numberList){
        this.numbers = numberList;
    }

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            numbers.add(Thread.currentThread().getName() + " - " + i);
        }
    }
}


class NumberWithArray {

    private String[] numbers = new String[10000];
    private int index = 0;

    public void add(String item){
        this.numbers[index] = item;
        this.index++;
    }

    public String getNumber(int element){
        return numbers[element];
    }
}