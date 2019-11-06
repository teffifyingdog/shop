package com.wjc.controller;

import com.wjc.entity.Orders;
import com.wjc.feign.OrderFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/back")
public class IndexController {

    @Autowired
    private OrderFeign orderFeign;

    @RequestMapping("/tohome")
    public String toHome(){
        return "home";
    }

    @RequestMapping("/toReturnMoney")
    public String toReturnMoney(Model model) {
        List<Orders> orderByReturnMoney = orderFeign.getOrderByReturnMoney();
        model.addAttribute("order",orderByReturnMoney);
        return "returnMoney";
    }

    @RequestMapping("/returnMoney")
    @ResponseBody
    public String returnMoney(String oid) {
        Orders order=orderFeign.getOrderByOid(oid);
        System.out.println("order = " + order.getTotalPrice());
        System.out.println("order = " + order);
        System.out.println("?????");
        if (order!=null){
            if (order.getOid()!=null&&order.getTotalPrice()!=null){
                String res = orderFeign.returnMoney(order.getOid(), order.getTotalPrice());
                if (res.equals("1"))
                    return "1";
                else
                    return "0";
            }
        }

        return "0";
    }
}
