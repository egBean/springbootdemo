package demo;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class Demo1 {

    public static void main(String[] args) {

        /*List<Integer> integers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        int result = 0;
        for (int e : integers) {
            if (e % 2 == 0){
                result += e*2;
            }
        }
        System.out.println(result);
        int sum = integers.stream()
                .filter(e -> e % 2 == 0)
                .mapToInt(e -> e * 2)
                .sum();
        System.out.println(sum);*/
        //        95
        //        144
        //        3
        //        1
        //        此处四个方法， 前2个都含有装箱拆箱操作，所以明显慢了很多。而且iterate方法因为在一开始并未确定集合中数量
        //        所以很难进行分割处理。

        System.out.println(measure(ParallelStreams::sequentialSumIterate,10_000_000));
        System.out.println(measure(ParallelStreams::paraSumIterate,10_000_000));
        System.out.println(measure(ParallelStreams::sequentialSumLongStream,10_000_000));
        System.out.println(measure(ParallelStreams::paraSumLongStream,10_000_000));
    }

    public static long measure(Function<Long,Long> adder,long n){
        long fastest = Long.MAX_VALUE;
        for(int i = 0;i<10;i++){
            long start = System.nanoTime();
            long sum = adder.apply(n);
            long duration = (System.nanoTime()-start)/1_000_000;
            if(duration<fastest){
                fastest = duration;
            }
        }
        return fastest;
    }



}

class ParallelStreams{
    public static long sequentialSumIterate(long n){
        return Stream.iterate(1L,i ->i+1).limit(n).reduce(0L,Long::sum);
    }

    public static long paraSumIterate(long n){
        return Stream.iterate(1L,i ->i+1).limit(n).parallel().reduce(0L,Long::sum);
    }
    public static long sequentialSumLongStream(long n){
        return LongStream.rangeClosed(1,n).reduce(0L,Long::sum);
    }

    public static long paraSumLongStream(long n){
        return LongStream.rangeClosed(1,n).parallel().reduce(0L,Long::sum);
    }

}
