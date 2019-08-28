package com.tianzhen.service;

import com.github.pagehelper.PageInfo;
import com.tianzhen.pojo.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    List<User> findAll();

    void save(User user);

    void update(User user);

    PageInfo<User> findByPage(int pageNum, int pageSize);

    void delete(Integer id);

    List<Map<String,Object>> findAllByIdAndName();

    User findById(Integer id);

    User findByUsername(String username);
}
