package com.wjc;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class TreeMapTest {
    public static void main(String[] args){
        TreeMap<String, String> treeMap = new TreeMap<>();
        treeMap.put("a","1");
        treeMap.put("d","2");
        treeMap.put("b","3");
        treeMap.put("e","4");
        treeMap.put("c","5");
        Set<Map.Entry<String, String>> entries = treeMap.entrySet();
        for (Map.Entry<String, String> entry : entries) {
            System.out.println("entry.getKey()  = " + entry.getKey() +","+entry.getValue());
        }
    }
}
