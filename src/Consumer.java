import java.util.ArrayList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Consumer implements Runnable {
    private Condition stackEmptyCondition;
    private ArrayList<String> stack;
    private Integer size;
    private ReentrantLock lock;
    private Condition stackFullCondition;

    public Consumer(ArrayList<String> stack, Integer size, ReentrantLock lock, Condition stackFullCondition, Condition stackEmptyCondition) {
        this.size = size;
        this.stack = stack;
        this.lock = lock;
        this.stackFullCondition = stackFullCondition;
        this.stackEmptyCondition = stackEmptyCondition;
    }

    @Override
    public void run() {
        while (true) {


            try {
                lock.lock();
                while (stack.size() == 0) {
                    System.out.println("Consumer stopped consuming");
                    stackEmptyCondition.await();
                    System.out.println("Consumer started consuming");

                }

                String msg = stack.get(stack.size() - 1);
                stack.remove(stack.size() - 1);
                System.out.println("Consumed " + msg);
                stackFullCondition.signalAll();
                Thread.sleep(100);

            } catch (Exception e) {
                System.out.println("Catch" + e);
            } finally{
                stackFullCondition.signalAll();
                lock.unlock();
            }

        }
    }
}