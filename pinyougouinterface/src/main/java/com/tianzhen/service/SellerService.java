package com.tianzhen.service;

import com.github.pagehelper.PageInfo;
import com.tianzhen.pojo.Seller;

import java.util.List;

public interface SellerService {

    void save(Seller seller);

    void update(Seller seller);

    void delete(String id);

    Seller findById(String id);

    List<Seller> findAll();

    PageInfo<Seller> findByPage(int pageNum,int pageSize);

    Seller findByUsername(String username);
}
