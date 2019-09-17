package demo;

import java.util.Arrays;

public class JiShuSort {

    public static void jiShuSort(int[] arr){
        int maxLength = 0;
        for(int i : arr){
            int tempLength = (i+"").length();
            if(tempLength>maxLength) maxLength = tempLength;
        }

        //声明一个桶，也就是二维数组
        int[][] buckets = new int[10][arr.length];
        //声明一个记录每个桶存了多少个数的数据。
        int[] count = new int[10];
        for( int i = 0 ; i<maxLength;i++){
            //存入
            for( int j = 0;j<arr.length;j++){
                int temp = (arr[j]/(int)(Math.pow(10,i)))%10;
                buckets[temp][count[temp]] = arr[j];
                count[temp]++;
            }
            //取出
            int index = 0;
            for(int k =0;k<10;k++){
                if(count[k]>0){
                    int[] tempBucket = buckets[k];
                    for(int m = 0 ; m <count[k];m++){
                        arr[index++] = tempBucket[m];
                    }
                    count[k]=0;
                }

            }
        }
        System.out.println(Arrays.toString(arr));
    }

    public static void main(String[] args) {
        jiShuSort(new int[]{43,523,3,24,523,876,9999,1,530});
        jiShuSort(new int[]{53,3,542,748,14,214});
    }
}
