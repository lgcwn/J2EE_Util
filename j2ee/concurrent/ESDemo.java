package concurrent;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ESDemo {

    public static void main(String[] args) throws Exception{
        ExecutorService es = Executors.newFixedThreadPool(2);

        Future<Integer> r1 = es.submit(new MC(1, 100));
        Future<Integer> r2 = es.submit(new MC(100, 10000));

        System.out.println(r1.get() + ":" + r2.get());

        es.shutdown();
    }

}

class MC implements Callable<Integer> {

    private int begin, end;
    public MC(int begin, int end) {
        this.begin = begin;
        this.end = end;
    }

    @Override
    public Integer call() throws Exception {
        int sum = 0;
        for (int i = begin; i < end; i++)
            sum += i;
        return sum;
    }
}
