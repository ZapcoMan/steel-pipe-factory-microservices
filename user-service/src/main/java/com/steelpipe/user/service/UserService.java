package com.steelpipe.user.service;

import com.github.pagehelper.PageInfo;
import com.steelpipe.user.entity.Account;
import com.steelpipe.user.entity.User;

import java.util.List;

public interface UserService {
    void add(User user);
    void update(User user);
    void deleteById(Integer id);
    void deleteBatch(List<User> list);
    User selectById(String id);
    List<User> selectAll();
    PageInfo<User> selectPage(Integer pageNum, Integer pageSize, User user);
    User login(Account account);
    void updatePassword(Account account);
}
