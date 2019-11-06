package com.wjc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wjc.dao.AddressMapper;
import com.wjc.entity.Address;
import com.wjc.service.IAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressServiceImpl implements IAddressService {

    @Autowired
    private AddressMapper addressMapper;

    @Override
    public List<Address> getAddressList(Integer id) {
        QueryWrapper<Address> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("uid",id);
        return addressMapper.selectList(queryWrapper);
    }

    @Override
    public int addAddress(Address address) {
        System.out.println("address = " + address);
        //先判断是否存在地址
        QueryWrapper<Address> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("uid",address.getUid());
        List<Address> addresses = addressMapper.selectList(queryWrapper);
        //如果存在修改默认地址
        if (addresses!=null) {
            int res = addressMapper.updateAddress(address.getUid());
            if (res != 1) {
                System.out.println("wtf"+res);
                return 0;
            }
        }
        //返回添加结果
        return addressMapper.insert(address);
    }
}
