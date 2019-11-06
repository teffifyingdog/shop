package com.wjc.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wjc.entity.Goods;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface GoodsApiMapper extends BaseMapper<Goods> {
    List<Goods> selectGoodsList();

    List<Goods> selectListByTime(@Param(value = "date") Date date);
}
