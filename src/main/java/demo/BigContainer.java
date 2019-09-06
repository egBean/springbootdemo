package demo;

/**
 * 最大容器问题
 */
public class BigContainer {
    public static void main(String[] args) {
        int[] arr = new int[]{1,8,6,2,5,4,8,3,7};
        System.out.println(Solution.maxArea(arr));
        System.out.println(Solution.maxArea2(arr));
    }
}

class Solution {
    //暴力算法
    public static int maxArea(int[] height) {
        int max = 0;
        for(int i = 0 ;i< height.length-1;i++){
            for(int j = i+1;j<height.length;j++){
                int temp = Math.min(height[i],height[j])*(j-i);
                if(temp > max){
                    max = temp;
                }
            }
        }
        return max;
    }

    //双指针算法

    /**
     * 这个算法的核心原理：先计算第一个柱子与其他柱子的最大面积。
     * 先计算第一个柱子与最后一个柱子谁短。
     * @param height
     * @return
     */
    public static int maxArea2(int[] height) {
        int max = 0;
        int start = 0;
        int end = height.length-1;
        //算出最大面积
        while(start<end){
            int temp = Math.min(height[start],height[end])*(end-start);
            if(temp > max){
                max = temp;
            }
            if(height[start]>height[end]){
                end--;
            }else{
                start++;
            }
        }
        return max;
    }
}
