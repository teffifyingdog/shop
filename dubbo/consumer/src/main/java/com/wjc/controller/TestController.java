package com.wjc.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.wjc.service.api.IStuService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @Reference
    private IStuService stuService;

    @RequestMapping("/test")
    public String test(int id) throws InterruptedException {
        return stuService.selectById(id);
    }
}
