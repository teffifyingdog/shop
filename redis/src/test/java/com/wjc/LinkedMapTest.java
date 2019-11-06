package com.wjc;

import org.apache.commons.collections.map.LinkedMap;

import java.util.Iterator;
import java.util.Set;

public class LinkedMapTest {
    public static void main(String[] args){
        LinkedMap linkedMap = new LinkedMap();
        linkedMap.put("a","1");
        linkedMap.put("b","2");
        linkedMap.put("c","3");
        linkedMap.put("d","4");
        linkedMap.put("e","5");
        Set set = linkedMap.entrySet();

        Iterator iterator = set.iterator();
        while (iterator.hasNext()) {
            System.out.println("iterator.next() = " + iterator.next());
        }

    }
}
