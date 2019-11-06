package com.wjc.provider.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.wjc.service.api.IStuService;


@Service
public class StuServiceImpl implements IStuService {
    @Override
    public String selectById(int id) throws InterruptedException {
        Thread.sleep(3000);
        if (id==1) {
            System.out.println("1");
            return "1";
        }
        else{
            System.out.println("0");
            return "0";
        }
    }
}
