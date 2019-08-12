package com.easybug.service.login;

import com.easybug.model.User;

public interface ILogin {
    /**
     * 登陆
     * @param user
     * @return
     */
    public User login(User user);

    /**
     * 注册
     */
    public boolean register(User user);
}
