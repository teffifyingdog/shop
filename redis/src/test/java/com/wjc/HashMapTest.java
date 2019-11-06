package com.wjc;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class HashMapTest {
    public static void main(String[] args){
        HashMap<String,String> hashmap = new HashMap<>();
        hashmap.put("a","1");
        hashmap.put("c","2");
        hashmap.put("d","3");
        hashmap.put("b","4");
        hashmap.put("e","5");
        hashmap.entrySet().iterator();

        for (Map.Entry<String, String> entry : hashmap.entrySet()) {
            System.out.println("entry= " + entry.getKey()+","+entry.getValue());
        }
    }
}
