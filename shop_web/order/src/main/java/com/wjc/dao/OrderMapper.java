package com.wjc.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wjc.entity.Orders;
import org.apache.ibatis.annotations.Param;

public interface OrderMapper extends BaseMapper<Orders> {
    int modifyStatus(String oid);

    int modifyStatusByOid(@Param("oid") String oid,@Param("id") int id);
}
