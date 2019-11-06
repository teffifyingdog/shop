package com.wjc.controller;

import com.wjc.entity.Goods;
import com.wjc.feign.GoodsFegin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/seckill")
public class SeckillController {

    @Autowired
    private GoodsFegin goodsFegin;

    @RequestMapping("/queryListByTime")
    @ResponseBody
    public List<Goods> queryListByTime() {
        List<Goods> goods=goodsFegin.queryListByTime(new Date());
        System.out.println("getSPrice="+ goods.get(0).getSeckill().getSPrice());
        return goods;
    }

    @RequestMapping("/getNowTime")
    @ResponseBody
    public Date getNowTime() {
        return new Date();
    }
}
