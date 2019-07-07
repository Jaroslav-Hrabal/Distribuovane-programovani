import java.util.ArrayList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.StampedLock;

public class Producer implements Runnable{
    private Condition stackEmptyCondition;
    private ArrayList<String> stack;
    private Integer size;
    private ReentrantLock lock;
    private Condition stackFullCondition;

    public Producer(ArrayList<String> stack, Integer size, ReentrantLock lock,Condition stackFullCondition,Condition stackEmptyCondition){
        this.size = size;
        this.stack= stack;
        this.lock = lock;
        this.stackFullCondition= stackFullCondition;
        this.stackEmptyCondition = stackEmptyCondition;


    }
    @Override
    public void run() {
        //produce messages
        Integer i = 0;
        while (true) {


            try {
                lock.lock();
                while (stack.size() > size) {
                    System.out.println("Producer stopped producing");
                    stackFullCondition.await();
                    System.out.println("Producer started producing");
                }
                String msg = "message " + i.toString();
                i++;

                stack.add(msg);
                System.out.println("Produced " + msg + " on spot: " + (stack.size()-1));
                stackEmptyCondition.signalAll();
                Thread.sleep(50);
            } catch(Exception e){
                System.out.println("Catch"+e);


        }


    }}}


