package com.wjc.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wjc.entity.Address;

public interface AddressMapper extends BaseMapper<Address> {
    int updateAddress(Integer uid);
}
