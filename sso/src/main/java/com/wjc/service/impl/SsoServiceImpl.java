package com.wjc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wjc.dao.SsoMapper;
import com.wjc.entity.User;
import com.wjc.service.ISsoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class SsoServiceImpl implements ISsoService {
    @Autowired
    private SsoMapper ssoMapper;


    @Override
    public boolean checkUsername(String username) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",username);
        Integer count = ssoMapper.selectCount(queryWrapper);
        if (count>=1){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public boolean register(User user) {
        int insert = ssoMapper.insert(user);
        if (insert==1){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public String getEmailByUsername(String username) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        User user = ssoMapper.selectOne(queryWrapper);
        if (user==null) {
            return null;
        }else{
            return user.getEmail();
        }
    }

    @Override
    public Integer updatePassword(String username, String newpassword) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        User user = ssoMapper.selectOne(queryWrapper);
        user.setPassword(newpassword);
        int i = ssoMapper.updateById(user);
        return i;
    }

    @Override
    public User login(String username, String password) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",username).eq("password",password);
        User user = ssoMapper.selectOne(queryWrapper);
        return user;
    }
}
