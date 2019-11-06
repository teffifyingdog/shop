package com.wjc;

/**
 * 排序任意随机数组
 */

public class 高级归并算法 {
    public static void main(String[] args){
        //随机数组
        int[] a=new int[10000000];
        for (int i = 0; i < a.length; i++) {
            a[i]= (int) (Math.random()*10000000);
        }

        //创建空的和a数组一样大小的数组
        int[] b=new int[a.length];
        //归并
        long c = System.currentTimeMillis();
        merge(a,b,0,a.length-1);
        long d = System.currentTimeMillis();
        System.out.println(d-c);
//        System.out.println("Arrays.toString(b) = " + Arrays.toString(b));
//        System.out.println("Arrays.toString(a) = " + Arrays.toString(a));

    }

    private static void merge(int[] a, int[] b, int start, int end) {
        //如果递归到只有一个数的时候结束,这个判断比老师的快乐100毫秒左右
        //老师的判断if(start>=end)
        if (start==end-1||start==end)
            return;

        //数组的中间位置，拆分为两个数组
        int middle=(start+end)/2;

        //递归两个拆开的数组
        merge(a,b,start,middle);
        merge(a,b,middle+1,end);

        //排序，将a数组拆成两个数组，归并到b数组
        int x,y,z;
        x = start;
        y = middle + 1;
        z = 0;
        for(; x <= middle || y <= end; z++){
            //进行判断
            if(x <= middle && (y > end || a[x] < a[y])){
                b[z] = a[x];
                x++;
            } else {
                b[z] = a[y];
                y++;
            }
        }
        //将b中排列好的再放会a中，然后两个排列好的a再归并成b
        for(int k = start, s = 0; k <= end; k++, s++){
            a[k] = b[s];
        }

    }
}
