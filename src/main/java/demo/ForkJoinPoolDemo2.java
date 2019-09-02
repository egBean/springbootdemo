package demo;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.stream.LongStream;

public class ForkJoinPoolDemo2 {
    public static void main(String[] args) {
        long[] numbers = LongStream.rangeClosed(1,100).toArray();
        System.out.println(new ForkJoinPool().invoke(new ForkJoinSumCalculator(numbers)));;
    }
}

class ForkJoinSumCalculator extends RecursiveTask<Long>{

    private final long[] numbers;
    private final int start;
    private final int end;

    private final static int LIMIT = 10_000;

    public ForkJoinSumCalculator(long[] numbers){
        this(numbers,0,numbers.length);
    }

    private ForkJoinSumCalculator(long[] numbers,int start,int end){
        this.numbers = numbers;
        this.start = start;
        this.end = end;
    }
    @Override
    protected Long compute() {
        if((end-start)<LIMIT){
            return calculate();
        }
        ForkJoinSumCalculator left = new ForkJoinSumCalculator(numbers,start,start+end/2);
        left.fork();
        ForkJoinSumCalculator right = new ForkJoinSumCalculator(numbers,start+end/2,end);
        Long rightResult = right.compute();
        Long leftResult = left.join();
        return rightResult+leftResult;
    }

    private long calculate(){
        long result = 0;
        for(int i = start;i<end;i++){
            result += numbers[i];
        }
        return result;
    }
}
