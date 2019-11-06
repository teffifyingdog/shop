package com.wjc.controller;

import com.wjc.entity.ShopCar;
import com.wjc.entity.User;
import com.wjc.service.ICarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/car/api")
public class CarApiController {

    @Autowired
    private ICarService carService;

    @RequestMapping("/getShopCar")
    public List<ShopCar> getShopCar(@RequestParam("car_id") int[] gid,@RequestParam("uid") int uid){
       return carService.getCarList(gid,uid);
    }

    @RequestMapping("/getShopCarByCarId")
    public List<ShopCar> getShopCarByCarId(@RequestParam("car_id") int[] cid){
        return carService.getShopCarByCarId(cid);
    }


    @RequestMapping("/deleteCarById")
    public int deleteCarById(@RequestParam("id") Integer id){
        return carService.deleteCarById(id);
    }
}
