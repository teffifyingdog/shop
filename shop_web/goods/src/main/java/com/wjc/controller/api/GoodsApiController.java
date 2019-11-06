package com.wjc.controller.api;

import com.wjc.entity.Goods;
import com.wjc.service.api.IGoodsApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/goods/api")
public class GoodsApiController {
    @Autowired
    private IGoodsApiService goodsApiService;

    @RequestMapping("/getGoodsList")
    public List<Goods> goodsList(){
        List<Goods> goodslist = goodsApiService.getGoodsList();
        return goodslist;
    }

    @RequestMapping("/insertAGood")
    public boolean insertAGood(@RequestBody Goods goods){
        int res=goodsApiService.insertAGood(goods);
        return res>0;
    }

    @RequestMapping("/queryGoodsById")
    public Goods queryGoodsById(@RequestParam("gid") Integer id){
        return goodsApiService.queryGoodsById(id);
    }

    @RequestMapping("/queryListByTime")
    @ResponseBody
    List<Goods> queryListByTime(@RequestParam("date") Date date){
        List<Goods> goods = goodsApiService.queryListByTime(date);
        return goods;
    }

}
