package com.wjc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wjc.dao.CarMapper;
import com.wjc.entity.Goods;
import com.wjc.entity.ShopCar;
import com.wjc.entity.User;
import com.wjc.feign.GoodsFegin;
import com.wjc.service.ICarService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class CarServiceImpl implements ICarService {

    @Autowired
    private CarMapper carMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private GoodsFegin goodsFegin;

    @Override
    public String insertCar(Integer gid, Integer gnum, User user, String carToken) {

        //如果用户登陆
        if (user!=null){
            //将添加购物车信息至数据库
            ShopCar shopCar=new ShopCar(user.getId(),gid,gnum,null,null);
            carMapper.insert(shopCar);
        }else {//如果不登陆，将购物车存入redis，返回redis的key：carToken
            carToken=carToken==null||carToken=="" ?UUID.randomUUID().toString():carToken;
            redisTemplate.opsForList().leftPush(carToken,new ShopCar(null,gid,gnum,null,null));
            redisTemplate.expire(carToken,365,TimeUnit.DAYS);
            return carToken;
        }
        return null;
    }

    @Override
    public List<ShopCar> getCarList(User user, String carToken) {

        //根据用户对象是否为空,判断是否登陆
        List<ShopCar> shopCars=null;
        if (user!=null){
            //从数据库中取出用户的购物车信息
            QueryWrapper<ShopCar> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("uid",user.getId());
            //按照添加时间降序排序
            queryWrapper.orderByDesc("create_time");
            shopCars = carMapper.selectList(queryWrapper);
        }else {
            //如果carToken不为空，则从redis中查找数据
            if (carToken!=null){
                //从redis获得购物车长度
                Long size = redisTemplate.opsForList().size(carToken);
                //获得所有购物车对象
                shopCars=redisTemplate.opsForList().range(carToken,0,size);
            }
        }

        //如果shopCar不为空，则通过商品的api接口根据商品id查找商品信息
        if (shopCars!=null){
            for (ShopCar shopCar : shopCars) {
                Goods goods = goodsFegin.queryGoodsById(shopCar.getGid());
                shopCar.setGoods(goods);
            }
        }
        return shopCars;

    }

    @Override
    public int mergerCar(User user,String carToken) {
        if (carToken!=null&&carToken!="") {
            //获得redis中的购物车信息
            Long size = redisTemplate.opsForList().size(carToken);
            List<ShopCar> shopCars = redisTemplate.opsForList().range(carToken, 0, size);
            //如果redis中有购物车信息，则存入数据库
            if (shopCars != null) {
                System.out.println("!!!!!!!!!!carToken = " + carToken);
                for (ShopCar shopCar : shopCars) {
                    shopCar.setUid(user.getId());
                    carMapper.insert(shopCar);
                }
                //删除redis中用户的购物车信息
                Boolean delete = redisTemplate.delete(carToken);
                System.out.println("delete = " + delete);

                return 1;
            }
        }
        //如果redis中没有用户的购物车信息则睁一只眼闭一只眼
        return 0;
    }

    @Override
    public List<ShopCar> getCarList(int[] gid, int uid) {
        List<ShopCar> carList = carMapper.getCarList(gid, uid);
        if (carList!=null){
            for (ShopCar shopCar : carList) {
                Goods goods = goodsFegin.queryGoodsById(shopCar.getGid());
                shopCar.setGoods(goods);
            }
        }
        return carList;
    }

    @Override
    public List<ShopCar> getShopCarByCarId(int[] cid) {
        System.out.println("cid = " + Arrays.toString(cid));
        //用购物车id数组获得购物车信息
        List<ShopCar> shopCars = carMapper.getShopCarByCarId(cid);
        //通过购物车的商品id获得商品对象
        if (shopCars!=null){
            for (ShopCar shopCar : shopCars) {
                Goods goods = goodsFegin.queryGoodsById(shopCar.getGid());
                shopCar.setGoods(goods);
            }
        }
        return shopCars;
    }

    @Override
    public int deleteCarById(Integer id) {
        return carMapper.deleteById(id);
    }


}
