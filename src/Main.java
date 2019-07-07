import java.util.ArrayList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Main {
    volatile static ArrayList<String> stack;
    volatile static ReentrantLock lock;
    public static void main(String[] args) {
        stack = new ArrayList<String>();
        Integer size = 100;
        lock = new ReentrantLock();
        Condition stackEmptyCondition = lock.newCondition();
        Condition stackFullCondition = lock.newCondition();
        Producer p = new Producer(stack,size,lock,stackFullCondition,stackEmptyCondition);
        Consumer c = new Consumer(stack,size,lock,stackFullCondition,stackEmptyCondition);
        new Thread(c).start();
        new Thread(p).start();


    }
}
