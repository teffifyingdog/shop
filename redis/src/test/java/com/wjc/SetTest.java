package com.wjc;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.TreeSet;

public class SetTest {
    public static void main(String[] args){
        HashSet<Object> hashSet = new HashSet<>();
        TreeSet<Object> treeSet = new TreeSet<>();
        LinkedHashSet<Object> linkedHashSet = new LinkedHashSet<>();
        hashSet.add("1");
        hashSet.add("1");
        for (Object o : hashSet) {
            System.out.println("o = " + o);
        }
    }
}
