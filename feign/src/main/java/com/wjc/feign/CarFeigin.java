package com.wjc.feign;

import com.wjc.entity.ShopCar;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("car")
public interface CarFeigin {

    @RequestMapping("/car/api/getShopCar")
    List<ShopCar> getShopCar(@RequestParam("car_id") int[] gid, @RequestParam("uid") int uid);

    @RequestMapping("/car/api/getShopCarByCarId")
    List<ShopCar> getShopCar(@RequestParam("car_id") int[] car_id);

    @RequestMapping("/car/api/deleteCarById")
    int deleteCarById(@RequestParam("id") Integer id);
}
