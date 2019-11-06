package com.wjc.controller;

import com.wjc.aop.IsLogin;
import com.wjc.entity.ShopCar;
import com.wjc.entity.User;
import com.wjc.service.ICarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("/car")
public class CarController {

    @Autowired
    private ICarService carService;

    @IsLogin(mustLogin = false)
    @RequestMapping("/addCar")
    public String addCar(@CookieValue(value = "carToken",required = false) String carToken, User user, Integer gnum, Integer gid, HttpServletResponse response){
        //添加至购物车的信息添加的数据库或redis中
        String token = carService.insertCar(gid, gnum, user, carToken);
        //如果添加至redis，增加或刷新cookie
        if (token!=null||token!=""){
            System.out.println("!!!!!!!token = " + token);
            Cookie cookie = new Cookie("carToken", token);
            cookie.setMaxAge(31536000);
            cookie.setPath("/");
            response.addCookie(cookie);
        }
        return "success";
    }

    @IsLogin
    @RequestMapping("/list")
    @ResponseBody
    public List<ShopCar> getCarList(User user, @CookieValue(value = "carToken",required = false) String carToken,HttpServletResponse response){
         response.setHeader("Access-Control-Allow-Origin", "*");
         List<ShopCar> clist= carService.getCarList(user,carToken);
;        return clist;
    }


    @IsLogin(mustLogin = true)
    @RequestMapping("/merge")
    public String mergeCar(User user,String returnUrl,@CookieValue(value = "carToken",required = false) String carToken,HttpServletResponse response){

        //如果购物车的cookies存在，则合并购物车
        if (carToken!=null||carToken!=""){
            int res=carService.mergerCar(user,carToken);
            //如果token为空则说明存入数据库
            if (res>0){
                //合并完成,删除临时购物车的cookie
                Cookie cookie = new Cookie("carToken", null);
                cookie.setMaxAge(0);
                cookie.setPath("/");
                response.addCookie(cookie);
            }
        }

        return "redirect:" + returnUrl;
    }

    @IsLogin(mustLogin = false)
    @RequestMapping("/detailList")
    public String detailList(Model model,User user, @CookieValue(value = "carToken",required = false) String carToken, HttpServletResponse response){
        //通过之前的查询的方法获得购物车信息
        List<ShopCar> carList = carService.getCarList(user, carToken);
        //返回结果
        model.addAttribute("carList",carList);

        return "cartlist";
    }
}
