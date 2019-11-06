package com.wjc.feign;

import com.wjc.entity.Goods;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;

@FeignClient("goods")
public interface GoodsFegin {

    @RequestMapping("/goods/api/getGoodsList")
    List<Goods> goodsList();

    @RequestMapping("/goods/api/insertAGood")
    boolean insert(@RequestBody Goods goods);

    @RequestMapping("/goods/api/queryGoodsById")
    Goods queryGoodsById(@RequestParam("gid") Integer id);

    @RequestMapping("/goods/api/queryListByTime")
    List<Goods> queryListByTime(@RequestParam("date")Date date);
}
