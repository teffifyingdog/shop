package com.wjc.service.api;

import com.wjc.entity.Goods;

import java.util.Date;
import java.util.List;

public interface IGoodsApiService {
    List<Goods> getGoodsList();

    int insertAGood(Goods goods);

    Goods queryGoodsById(Integer id);

    List<Goods> queryListByTime(Date date);
}
