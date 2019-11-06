package com.wjc;

import java.util.Arrays;

/**
 * 有两个int类型有序数组，编写一个算法，将两个有序的数组合并为一个有序数组？
 * 比如：
 * int[] a = {1,6,17,29};
 * Int[] b = {2,3,5,18,20,25,28};
 * 合并为：{1,2,3,5,6,17,18,20,25,28,29}
 */
public class 归并排序 {
    public static void main(String[] args) {
        int[] a = {1, 6, 17, 29};
        int[] b = {2, 3, 5, 18, 20, 25, 28};

        int[] c = new int[a.length + b.length];
        int x = 0;
        int y = 0;
        int z = 0;
        for (; z < c.length; z++) {
            if (x < a.length && ( y == b.length || a[x] < b[y]) ) {
                c[z] = a[x];
                x++;
            } else {
                c[z] = b[y];
                y++;
            }
        }
        System.out.println(Arrays.toString(c));
    }

}
