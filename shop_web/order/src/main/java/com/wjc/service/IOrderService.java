package com.wjc.service;

import com.wjc.entity.Orders;

import java.util.List;

public interface IOrderService {
    Orders addOrder(Integer id, int[] car_id, int aid);

    List<Orders> getOrderList(Integer id);

    int modifyStatus(String oid);

    int modifyStatusByOid(String oid, int i);

    List<Orders> getOrderByReturnMoney();

    Orders getOrderByOid(String oid);
}
