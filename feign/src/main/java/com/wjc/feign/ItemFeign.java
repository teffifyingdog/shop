package com.wjc.feign;

import com.wjc.entity.Goods;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("item")
public interface ItemFeign {

    @RequestMapping("/item/createHtml")
    boolean createHtml(@RequestBody Goods goods);
}
