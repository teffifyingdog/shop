package com.wjc.controller;

import com.wjc.aop.IsLogin;
import com.wjc.entity.Address;
import com.wjc.entity.User;
import com.wjc.service.IAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/address")
public class AddressController {

    @Autowired
    private IAddressService addressService;

    @IsLogin(mustLogin = true)
    @RequestMapping("/insert")
    @ResponseBody
    public String addAddress(User user,Address address){
        address.setUid(user.getId());
        int res=addressService.addAddress(address);
        if (res!=1){
            return "0";
        }
        return "succ";
    }
}
