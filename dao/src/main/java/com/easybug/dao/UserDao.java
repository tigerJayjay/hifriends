package com.easybug.dao;

import com.easybug.model.User;

import java.util.List;

public interface UserDao {
    public List<User> queryUser();
    public int insertUser(User user);
    public User selectUserById(Integer uid);
    public void updateUser(User user);
    public User selectUserbyUidAndPass(User user);
}
