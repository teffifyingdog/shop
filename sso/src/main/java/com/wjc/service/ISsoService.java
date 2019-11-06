package com.wjc.service;

import com.wjc.entity.User;

public interface ISsoService {
    boolean checkUsername(String username);

    boolean register(User user);

    String getEmailByUsername(String username);

    Integer updatePassword(String username, String newpassword);

    User login(String username, String password);
}
