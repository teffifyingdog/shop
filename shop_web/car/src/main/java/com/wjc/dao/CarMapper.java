package com.wjc.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wjc.entity.ShopCar;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CarMapper extends BaseMapper<ShopCar> {

    List<ShopCar> getCarList(int[] gid, int uid);

    List<ShopCar> getShopCarByCarId(@Param("cid") int[] cid);
}
