package com.wjc.controller;

import com.wjc.entity.Orders;
import com.wjc.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/order/api")
@RestController
public class OrderApiController {

    @Autowired
    private IOrderService orderService;

    @RequestMapping("/getOrderByReturnMoney")
    public List<Orders> getOrderByReturnMoney() {
        List<Orders> orders=orderService.getOrderByReturnMoney();
        return orders;
    }

    @RequestMapping("/getOrderByOid")
    public Orders getOrderByOid(@RequestParam("oid") String oid) {
        Orders orders=orderService.getOrderByOid(oid);
        return orders;
    }
}
