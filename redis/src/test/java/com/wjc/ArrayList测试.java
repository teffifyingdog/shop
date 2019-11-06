package com.wjc;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ArrayList测试 {
    public static void main(String[] args){
        List<String> arr=new ArrayList<>();
        for (int i = 0; i < 100000; i++) {
            arr.add(String.valueOf(Math.random() * 10000000));
        }

        arraylistTestSpeed(arr);
    }

    private static void arraylistTestSpeed(List<String> arr) {


        long start = System.currentTimeMillis();
        for (int i = 0; i < arr.size(); i++) {
            System.out.println(arr.get(i));
        }
        long end=System.currentTimeMillis();


        long start2=System.currentTimeMillis();
        Iterator<String> iterator = arr.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
        long end2=System.currentTimeMillis();


        System.out.println("(end-start) = " + (end-start));
        System.out.println("(end-start)2 = " + (end2 - start2));
    }
}
