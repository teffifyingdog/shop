package com.wjc.controller;

import com.wjc.aop.IsLogin;
import com.wjc.entity.*;
import com.wjc.feign.CarFeigin;
import com.wjc.service.IAddressService;
import com.wjc.service.IOrderService;
import com.wjc.util.PriceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private CarFeigin carFeigin;

    @Autowired
    private IAddressService addressService;

    @Autowired
    private IOrderService orderService;


    @RequestMapping("/addOrder")
    @IsLogin(mustLogin = true)
    public String addOrder(User user,int[] car_id,int aid){
        Orders order=orderService.addOrder(user.getId(),car_id,aid);
        if (order!=null)
            System.out.println("ok");
        else
            System.out.println("not ok");
        StringBuilder title=null;
        for (int i=0;i<order.getDetails().size();i++) {
            System.out.println("order = " + order.getDetails());
            String tt = order.getDetails().get(i).getTitle();
            title.append(tt);
            if (i<order.getDetails().size()-1){
                title.append(",");
            }
        }
        return "redirect:http://192.168.252.1:60000/pay/alipay?" +
                "oid="+order.getOid()+
                "&total_price="+order.getTotalPrice()+
                "&title="+title;
    }

    @IsLogin(mustLogin = true)
    @RequestMapping("/list")
    public String getOrderList(User user,Model model){
        List<Orders> orders=orderService.getOrderList(user.getId());
        model.addAttribute("orders",orders);
        return "orderlist";
    }

    @IsLogin(mustLogin = true)
    @RequestMapping("/edit")
    public String order_edit(int[] car_id, User user, Model model){
        //通过购物车接口获得用户要购买商品的信息
        List<ShopCar> shopCar = carFeigin.getShopCar(car_id, user.getId());
        model.addAttribute("shopcar",shopCar);
        //获得用户地址信息
        List<Address> addresses=addressService.getAddressList(user.getId());
        model.addAttribute("addresses",addresses);
        //通过公共方法获得订单的总价
        double allPrice = PriceUtil.getAllPrice(shopCar);
        model.addAttribute("allPrice",allPrice);
        return "orderedit";
    }

}
