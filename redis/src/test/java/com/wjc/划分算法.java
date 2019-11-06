package com.wjc;

import java.text.SimpleDateFormat;
import java.util.Date;

public class 划分算法 {
    public static void main(String[] args){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(format.format(new Date()).toString().endsWith(":00:00"));
    }
}
