package com.sysdo.service;

import com.sysdo.model.User;

public interface UserService {

    public String registration(User user);

    public User findByEmail(String email);

    public User findByUsername(String username);

    public String userActivation(String code);
}
