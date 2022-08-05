package br.com.vanessaancken.threads;

public class ExampleJDeadlockExample {

    // this example block threads, jvm will never stop untill you stop it.

    public static void main(String[] args) {
        TransactionManager tx = new TransactionManager();
        PoolConnection pool = new PoolConnection();
        new Thread(new DataTask(pool, tx)).start();
        new Thread(new ProcedureDataTask(pool, tx)).start();

    }
}

class DataTask implements Runnable {

    private PoolConnection pool;
    private TransactionManager tx;

    public DataTask(PoolConnection pool, TransactionManager tx) {
        this.pool = pool;
        this.tx = tx;
    }

    @Override
    public void run(){
        synchronized (pool) {
            System.out.println("Got pool key...");
            pool.getConnection();
            synchronized (tx) {
                System.out.println("Got tx key...");
                tx.begin();
            }
        }
    }
}


class ProcedureDataTask implements Runnable {

    private PoolConnection pool;
    private TransactionManager tx;

    public ProcedureDataTask(PoolConnection pool, TransactionManager tx) {
        this.pool = pool;
        this.tx = tx;
    }

    @Override
    public void run() {
        synchronized (tx) {
            System.out.println("Got tx key...");
            tx.begin();
            synchronized (pool) {
                System.out.println("Got pool key...");
                pool.getConnection();
            }
        }

    }
}


class TransactionManager {

    public void begin() {
        System.out.println("Starting transaction...");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}


class PoolConnection {

    public String getConnection() {
        System.out.println("Starting connection...");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "connection";
    }
}