package com.wjc.feign;


import com.wjc.entity.Goods;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("search")
public interface SearchFeign {
    @RequestMapping("/search/insert")
    boolean insert(@RequestBody Goods goods);
}
