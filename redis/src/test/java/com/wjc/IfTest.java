package com.wjc;

public class IfTest {
    public static void main(String[] args){
        long s1 = System.currentTimeMillis();
        for (int i = 0; i < 10000000; i++) {
            double s = Math.random() * 10000;
            double e = Math.random() * 10000;

            if (s-e>0){
                //                System.out.println("t");
            }
            else {
//                System.out.println("f");
            }
        }
        long e1 = System.currentTimeMillis();

        long s2 = System.currentTimeMillis();
        for (int i = 0; i < 10000000; i++) {
            double s = Math.random() * 10000;
            double e = Math.random() * 10000;

            if (s>e) {
//                System.out.println("t");
            }else {
//                System.out.println("f");
            }
        }
        long e2 = System.currentTimeMillis();

        System.out.println("s1-e1 = " + (s1-e1));
        System.out.println("s1-e1 = " + (s2-e2));

    }
}
