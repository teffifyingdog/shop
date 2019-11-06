package com.wjc.service;

import com.wjc.entity.Address;

import java.util.List;

public interface IAddressService {
    List<Address> getAddressList(Integer id);

    int addAddress(Address address);
}
