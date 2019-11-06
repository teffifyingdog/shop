package com.wjc.util;

import com.wjc.entity.ShopCar;

import java.math.BigDecimal;
import java.util.List;

public class PriceUtil {

    public static double getAllPrice(List<ShopCar> shopCars){
        BigDecimal allprice=BigDecimal.valueOf(0);

        //循环购物车集合，价格相加，得到总价
        if (shopCars!=null){
            for (ShopCar shopCar : shopCars) {
                BigDecimal price = shopCar.getGoods().getPrice();
                BigDecimal gnum = BigDecimal.valueOf(shopCar.getGnum());
                BigDecimal multiply = price.multiply(gnum);
                allprice=allprice.add(multiply);
            }
        }
        return allprice.doubleValue();

    }
}
