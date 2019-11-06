package com.wjc;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class LinkedListTest {
    public static void main(String[] args){
        LinkedList<String> ll = new LinkedList<>();
        for (int i = 0; i < 100000; i++) {
            ll.add(String.valueOf(Math.random() * 10000000));
        }
        linkedListTestSpeed(ll);
    }

    private static void linkedListTestSpeed(List<String> arr) {


        long start2=System.currentTimeMillis();
        Iterator<String> iterator = arr.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
        long end2=System.currentTimeMillis();

        long start = System.currentTimeMillis();
        for (int i = 0; i < arr.size(); i++) {
            System.out.println(arr.get(i));
        }
        long end=System.currentTimeMillis();


        System.out.println("(end-start) = " + (end-start));
        System.out.println("(end-start)2 = " + (end2 - start2));
    }
}
