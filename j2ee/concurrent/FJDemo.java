package concurrent;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;

public class FJDemo {

    public static void main(String[] args) throws Exception {
        ForkJoinPool forkJoinPool = new ForkJoinPool();

        Future<Long> result = forkJoinPool.submit(new MTask(0, 1000001));
        System.out.println(result.get());

        forkJoinPool.shutdown();
    }

}

class MTask extends RecursiveTask<Long> {

    static final int THRESHOLD = 1000;

    private int begin, end;
    public MTask(int begin, int end) {
        this.begin = begin;
        this.end = end;
    }

    @Override
    protected Long compute() {
        Long startTime=System.currentTimeMillis();
        long sum = 0;
        if ((end - begin) <= THRESHOLD) {

            for (int i = begin; i < end; i++)
                sum += i;
        } else {
            int mid = (begin + end) / 2;
            MTask left = new MTask(begin, mid);
            left.fork();
            MTask right = new MTask(mid + 1, end);
            right.fork();

            Long lr = left.join();
            System.out.println(begin + "-" + mid + ":" + lr);
            Long rr = right.join();
            System.out.println((mid + 1) + "-" + end + ":" + rr);

            sum = lr + rr;
        }
        Long endTime=System.currentTimeMillis();
        System.out.println("mm:"+(endTime-startTime));
        return sum;
    }
}
