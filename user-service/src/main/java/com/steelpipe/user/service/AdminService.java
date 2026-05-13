package com.steelpipe.user.service;

import com.github.pagehelper.PageInfo;
import com.steelpipe.user.entity.Account;
import com.steelpipe.user.entity.Admin;

import java.util.List;

public interface AdminService {
    void add(Admin admin);
    void update(Admin admin);
    void deleteById(Integer id);
    void deleteBatch(List<Admin> list);
    Admin selectById(String id);
    List<Admin> selectAll();
    PageInfo<Admin> selectPage(Integer pageNum, Integer pageSize, Admin admin);
    Admin login(Account account);
    void updatePassword(Account account);
}
