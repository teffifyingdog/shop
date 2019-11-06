package com.wjc.controller;

import com.wjc.entity.Hit;
import com.wjc.service.IHitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Controller
@RequestMapping("/hit")
public class HitController {

    @Autowired
    private IHitService hitService;

    // TODO 从线程池中获取线程
    private ExecutorService service = Executors.newFixedThreadPool(100);

    @RequestMapping("/test")
    public void hitTest() {
        // TODO 创建线程测试
        for (int i = 1; i < 100; i++) {
            service.submit(new Runnable() {
                @Override
                public void run() {
                    // TODO 访问数加一
                    hitService.hitTest();
                }
            });
        }
    }

    @RequestMapping("/list")
    public String annotationTest(Model model) {
        List<Hit> hits = hitService.annotationTest();
        model.addAttribute("hits",hits);
        return "hit";
    }
}
