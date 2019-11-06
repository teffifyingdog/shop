package com.wjc.feign;

import com.wjc.entity.Orders;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.List;

@FeignClient("order")
public interface OrderFeign {

    @RequestMapping("/order/api/getOrderByReturnMoney")
    List<Orders> getOrderByReturnMoney();

    @RequestMapping("/order/api/getOrderByOid")
    Orders getOrderByOid(@RequestParam("oid") String oid);

    @RequestMapping("/pay/return")
    String returnMoney(@RequestParam("oid") String oid, @RequestParam("totalPrice")BigDecimal totalPrice);
}
