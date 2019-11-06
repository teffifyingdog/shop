package com.wjc.service;

import com.wjc.entity.ShopCar;
import com.wjc.entity.User;

import java.util.List;

public interface ICarService {

    String insertCar(Integer gid, Integer gnum, User user,String carToken);

    List<ShopCar> getCarList(User user, String carToken);


    int mergerCar(User user, String carToken);

    List<ShopCar> getCarList(int[] gid, int uid);

    List<ShopCar> getShopCarByCarId(int[] cid);

    int deleteCarById(Integer id);
}
