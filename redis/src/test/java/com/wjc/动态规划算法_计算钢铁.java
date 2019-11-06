package com.wjc;

import java.util.Arrays;

/**
 * 一段长度为x的钢条，价格为px(x为正整数)
 *         价格表如下：
 *         长度x    1   2  3   4  5    6    7    8    9    10
 *         价格px  1   5   8   9  10  17   17   20    24   25
 *
 * 现在给定一段长度为n（n不大于10）的钢条，求一个切割方案，使的钢条售出利益最大化（动态规划算法）
 */
public class 动态规划算法_计算钢铁 {
    public static void main(String[] args){
        //多少米对应的价格
        int[] a = {0, 1, 5, 8, 9, 10, 17, 17, 20, 24, 25};

        //最优价格数组
        int[] b=new int[a.length];

        //计算所有的最大出售价格
        for (int i = 0; i < b.length; i++) {
            //计算每米最大出售价格
            for (int j = 0; j <= i; j++) {
                b[i]=Math.max(b[i],b[i-j]+a[j]);
            }
        }

        System.out.println("b = " + Arrays.toString(b));
    }
}
