package com.wjc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wjc.dao.AddressMapper;
import com.wjc.dao.OrderDetailMapper;
import com.wjc.dao.OrderMapper;
import com.wjc.entity.*;
import com.wjc.feign.CarFeigin;
import com.wjc.feign.GoodsFegin;
import com.wjc.service.IOrderService;
import com.wjc.util.PriceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class OrderServiceImpl implements IOrderService {
    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private CarFeigin carFeigin;

    @Autowired
    private AddressMapper addressMapper;

    @Autowired
    private OrderDetailMapper orderDetailMapper;

    @Autowired
    private GoodsFegin goodsFegin;

    @Override
    public Orders  addOrder(Integer id, int[] car_id, int aid) {
        //用购物车接口，通过购物车id数组获得购物车信息
        List<ShopCar> shopCars=carFeigin.getShopCar(car_id);
        //通过公共方法获得订单总价
        double allPrice = PriceUtil.getAllPrice(shopCars);
        //获得地址信息
        Address address = addressMapper.selectById(aid);
        //添加订单信息至数据库
        Orders orders = new Orders().setAddress(
                address.getAddress())
                .setCode(address.getCode())
                .setOid(UUID.randomUUID().toString())
                .setPhone(address.getPhone())
                .setReceiver(address.getReceiver())
                .setTotalPrice(BigDecimal.valueOf(allPrice))
                .setUid(id);
        int ores = orderMapper.insert(orders);
        //如果订单添加成功，则添加详细订单详情
        if (ores==1){
            List<OrderDetail> orderDetails=new ArrayList<>();
            for (ShopCar shopCar : shopCars) {
                OrderDetail orderDetail = new OrderDetail(
                        shopCar.getGoods().getTitle(),
                        orders.getOid(),
                        shopCar.getGoods().getId(),
                        shopCar.getGoods().getPrice(),
                        shopCar.getGnum(),
                        BigDecimal.valueOf(shopCar.getGnum()).multiply(shopCar.getGoods().getPrice()),
                        null
                );
                System.out.println("orderDetail = " + orderDetail);
                //将订单详情放入订单中返回
                orderDetails.add(orderDetail);
                //添加订单详情至数据库
                int odres = orderDetailMapper.insert(orderDetail);
                //如果添加失败则返回0
                if (odres!=1)
                    return null;
                //删除购物车中的信息,如果删除失败返回0
                int cres=carFeigin.deleteCarById(shopCar.getId());
                if (cres!=1)
                    return null;
            }
            //订单里的订单详情信息
            orders.setDetails(orderDetails);
            //如果以上都没问题则返回订单对象
            return orders;
        }
        return null;
    }

    @Override
    public List<Orders> getOrderList(Integer id) {
        //通过用户id获得订单信息
        QueryWrapper<Orders> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("uid",id);
        List<Orders> orders = orderMapper.selectList(queryWrapper);

        //通过订单id获得订单详情对象,将订单详情的集合放入订单中
        for (Orders order : orders) {
            QueryWrapper<OrderDetail> wrapper = new QueryWrapper<>();
            wrapper.eq("oid",order.getOid());
            List<OrderDetail> orderDetails = orderDetailMapper.selectList(wrapper);
            //通过商品服务的接口将对应的商品对象放入订单详情中
            for (OrderDetail orderDetail : orderDetails) {
                Goods goods = goodsFegin.queryGoodsById(orderDetail.getGid());
                orderDetail.setGoods(goods);
            }
            order.setDetails(orderDetails);
        }
        return orders;
    }

    @Override
    public int modifyStatus(String oid) {

        return orderMapper.modifyStatus(oid);

    }

    @Override
    public int modifyStatusByOid(String oid, int id) {
        return orderMapper.modifyStatusByOid(oid,id);
    }

    @Override
    public List<Orders> getOrderByReturnMoney() {
        QueryWrapper<Orders> wrapper = new QueryWrapper<>();
        wrapper.eq("status",7);
        return orderMapper.selectList(wrapper);
    }

    @Override
    public Orders getOrderByOid(String oid) {
        QueryWrapper<Orders> wrapper = new QueryWrapper<>();
        wrapper.eq("oid",oid);
        return orderMapper.selectOne(wrapper);
    }
}
