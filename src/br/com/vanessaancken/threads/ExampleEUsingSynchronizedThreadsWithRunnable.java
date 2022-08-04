package br.com.vanessaancken.threads;

public class ExampleEUsingSynchronizedThreadsWithRunnable {

    public static void main(String[] args) {

        Bathroom a = new Bathroom();

        new Thread(new TaskOne(a), "Vanessa").start();
        new Thread(new TaskTwo(a), "Camila").start();
        new Thread(new TaskOne(a), "Julia").start();
        new Thread(new TaskTwo(a), "Andrezza").start();
        Thread cleanning = new Thread(new TaskThree(a), "Maid");
        cleanning.setDaemon(true);
        cleanning.start();
    }
}


class Bathroom {

    private boolean isDirty = Boolean.TRUE;

    public void pee() {
        this.action("peeing", 5000);
    }

    public void poop() {
        this.action("pooping", 10000);
    }

    private void action(String action, int duration){
        final var threadName = Thread.currentThread().getName();
        System.out.println(threadName + " is knocking on the door");
        synchronized (this) {
            System.out.println(threadName + " is openning the door");
            while(isDirty){
                waitOutside(threadName);
            }
            System.out.println(threadName + " is " + action + "...");
            try {
                Thread.sleep(duration);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            isDirty = Boolean.TRUE;
            System.out.println(threadName + " is washing hands");
            System.out.println(threadName + " is closing the door");
        }
    }

    private void waitOutside(String threadName) {
        try {
            System.out.println(threadName + " is getting out of the bathroom because it's dirty!");
            this.wait();
            System.out.println(threadName + " is knocking on the door");
            System.out.println(threadName + " is openning the door");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void clean() {
        final var threadName = Thread.currentThread().getName();
        System.out.println(threadName + " is knocking on the door");
        synchronized (this) {
            if(isDirty){
                System.out.println(threadName + " is openning the door");
                System.out.println(threadName + " is cleanning the bathroom.");
                isDirty = Boolean.FALSE;
                this.notifyAll();
            }
            System.out.println(threadName + " is leaving the bathroom, bathroom is clean.");
        }

    }
}


class TaskOne implements Runnable {

    private Bathroom bathroom;

    public TaskOne(Bathroom bathroom){
        this.bathroom = bathroom;
    }

    @Override
    public void run() {
        bathroom.pee();
    }
}


class TaskTwo implements Runnable {

    private Bathroom bathroom;

    public TaskTwo(Bathroom bathroom){
        this.bathroom = bathroom;
    }

    @Override
    public void run() {
        bathroom.poop();
    }
}


class TaskThree implements Runnable {

    private Bathroom bathroom;

    public TaskThree(Bathroom bathroom){
        this.bathroom = bathroom;
    }

    @Override
    public void run() {
        while(true){
            bathroom.clean();
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}